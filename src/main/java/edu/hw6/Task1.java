package edu.hw6;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;

public class Task1 implements Map<String, String> {
    private final String filePath;

    private static final Logger LOGGER = Logger.getLogger(Task2.class.getName());

    public Task1(String filePath) {
        this.filePath = filePath;
        loadFromFile();
    }

    private final Map<String, String> data = new HashMap<>();

    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    data.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            LOGGER.severe("Error during file loading");

        }
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            LOGGER.severe("Error during file saving");
        }
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return data.get(key);
    }

    @Override
    public String put(String key, String value) {
        String previousValue = data.put(key, value);
        saveToFile();
        return previousValue;
    }

    @Override
    public String remove(Object key) {
        String removedValue = data.remove(key);
        saveToFile();
        return removedValue;
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        data.putAll(m);
        saveToFile();
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public java.util.Set<String> keySet() {
        return data.keySet();
    }

    @Override
    public java.util.Collection<String> values() {
        return data.values();
    }

    @Override
    public java.util.Set<Map.Entry<String, String>> entrySet() {
        return data.entrySet();
    }
}

