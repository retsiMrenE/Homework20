package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private static final String FILE_NAME = "data.txt";

    public static void save(HashMap<Long, Integer> statistic) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<Long, Integer> entry : statistic.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении данных", e);
        }
    }

    public static HashMap<Long, Integer> load() {
        HashMap<Long, Integer> statistic = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    try {
                        Long chatId = Long.parseLong(parts[0]);
                        Integer wins = Integer.parseInt(parts[1]);
                        statistic.put(chatId, wins);
                    } catch (NumberFormatException e) {
                        System.err.println("Ошибка при разборе данных: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Файл не найден или ошибка при загрузке данных");
        }
        return statistic;
    }
}

