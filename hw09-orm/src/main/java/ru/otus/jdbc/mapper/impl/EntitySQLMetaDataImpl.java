package ru.otus.jdbc.mapper.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private static final String SELECT_ALL = "select * from %s";
    private static final String SELECT_ALL_BY_ID = "select * from %s where %s = ?";
    private static final String INSERT = "insert into %s (%s) values (%s)";
    private static final String UPDATE = "update %s set %s where %s = ?";

    private final String selectAllSql;
    private final String selectByIdSql;
    private final String insertSql;
    private final String updateSql;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        String tableName = entityClassMetaData.getName();
        String idFieldName = entityClassMetaData.getIdField().getName();
        List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();

        this.selectAllSql = SELECT_ALL.formatted(tableName);
        this.selectByIdSql = SELECT_ALL_BY_ID.formatted(tableName, idFieldName);
        this.insertSql =
                INSERT.formatted(tableName, getInsertFields(fieldsWithoutId), getInsertValues(fieldsWithoutId.size()));
        this.updateSql = UPDATE.formatted(tableName, getUpdateFields(fieldsWithoutId), idFieldName);
    }

    @Override
    public String getSelectAllSql() {
        return selectAllSql;
    }

    @Override
    public String getSelectByIdSql() {
        return selectByIdSql;
    }

    @Override
    public String getInsertSql() {
        return insertSql;
    }

    @Override
    public String getUpdateSql() {
        return updateSql;
    }

    private String getInsertFields(List<Field> fields) {
        return fields.stream().map(Field::getName).collect(Collectors.joining(","));
    }

    private String getInsertValues(int count) {
        return String.join(",", java.util.Collections.nCopies(count, "?"));
    }

    private String getUpdateFields(List<Field> fields) {
        return fields.stream().map(field -> field.getName() + " = ?").collect(Collectors.joining(","));
    }
}
