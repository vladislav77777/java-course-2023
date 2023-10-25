package edu.hw3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class Task3 {
    private Task3() {
    }

    public static <T> Map<T, Integer> freqDict(List<T> list) {
        Map<T, Integer> frequencyMap = new HashMap<>();

        for (T item : list) {
            frequencyMap.put(item, frequencyMap.getOrDefault(item, 0) + 1);
        }

        return frequencyMap;
    }

}
