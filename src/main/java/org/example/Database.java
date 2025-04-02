package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private static final String FILE_NAME = "data.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void save(Map<Long, Integer> statistic) {
        try (Writer writer = new FileWriter(FILE_NAME)) {
            objectMapper.writeValue(writer, statistic);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении данных", e);
        }
    }

    public static Map<Long, Integer> load() {
        File file = new File(FILE_NAME);
        if (!file.exists() || file.length() == 0) {
            return new HashMap<>();
        }

        try (Reader reader = new FileReader(FILE_NAME)) {
            return objectMapper.readValue(reader, new TypeReference<Map<Long, Integer>>() {});
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке данных: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
