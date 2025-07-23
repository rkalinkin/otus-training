package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {

    private final String path;
    private final ObjectMapper mapper = new ObjectMapper(); // Jackson ObjectMapper

    public ResourcesFileLoader(String fileName) {
        this.path = fileName;
    }

    @Override
    public List<Measurement> load() {
        // читает файл, парсит и возвращает результат
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(path);
        if (inputStream == null) {
            throw new FileProcessException("Ресурс не найден: " + path);
        }

        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return mapper.readValue(reader, new TypeReference<List<Measurement>>() {});
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
