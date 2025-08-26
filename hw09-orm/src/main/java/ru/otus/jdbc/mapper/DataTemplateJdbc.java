package ru.otus.jdbc.mapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

/** Сохраняет объект в базу, читает объект из базы */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<?> entityClassMetaData;

    public DataTemplateJdbc(
            DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<?> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(
                connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), this::getFirstInstance);
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
                    List<T> resultList = new ArrayList<>();
                    try {
                        while (rs.next()) {
                            resultList.add(createInstance(rs));
                        }
                    } catch (Exception e) {
                        throw new DataTemplateException(e);
                    }
                    return resultList;
                })
                .orElse(new ArrayList<>());
    }

    @Override
    public long insert(Connection connection, T client) {
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), getParamsWithoutId(client));
    }

    @Override
    public void update(Connection connection, T client) {
        List<Object> params = new ArrayList<>(getParamsWithoutId(client));
        params.add(getId(client));
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);
    }

    private T getFirstInstance(ResultSet rs) {
        try {
            if (rs.next()) {
                return createInstance(rs);
            }
            return null;
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private T createInstance(ResultSet rs) throws Exception {
        var obj = entityClassMetaData.getConstructor().newInstance();
        for (var field : entityClassMetaData.getAllFields()) {
            field.setAccessible(true);
            var value = rs.getObject(field.getName());
            field.set(obj, value);
        }
        return (T) obj;
    }

    private List<T> getAllInstances(ResultSet rs) {
        try {
            var instancesList = new ArrayList<T>();
            while (rs.next()) {
                instancesList.add(createInstance(rs));
            }
            return instancesList;
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getParamsWithoutId(T client) {
        try {
            var params = new ArrayList<Object>();
            for (var field : entityClassMetaData.getFieldsWithoutId()) {
                field.setAccessible(true);
                params.add(field.get(client));
            }
            return params;
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private Object getId(T client) {
        try {
            var idField = entityClassMetaData.getIdField();
            idField.setAccessible(true);
            return idField.get(client);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
