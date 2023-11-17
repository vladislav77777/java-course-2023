package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Test {

    @ParameterizedTest
    @DisplayName("Проверка корректной кластеризации строки")
    @CsvSource(value = {
        ")(()()| [null]",
        "((()))| [\"((()))\"]",
        "((()))(())()()(()())| [\"((()))\", \"(())\", \"()\", \"()\", \"(()())\"]",
        "((())())(()(()()))| [\"((())())\", \"(()(()()))\"]",
    }, delimiter = '|')
    public void testClusterize(String input, String expectedClusters) {
        List<String> actualClusters = Task2.clusterize(input);
        List<String> expected = parseExpected(expectedClusters);
        assertEquals(expected, actualClusters);
    }

    private List<String> parseExpected(String expected) {
        // Преобразование строки в список строк
        expected = expected.substring(1, expected.length() - 1);
        if (expected.equals("null")) {
            return null;
        }
        String[] parts = expected.split(", ");
        return List.of(parts);
    }
}

