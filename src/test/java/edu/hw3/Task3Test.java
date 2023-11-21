package edu.hw3;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Task3Test {

    @ParameterizedTest
    @CsvSource(value = {
        "'a,bb,a,bb'| {bb: 2, a: 2}",
        "'this,and,that,and'| {that: 1, and: 2, this: 1}",
        "'код,код,код,bug'| {код: 3, bug: 1}",
        "'1,1,2,2'| {1: 2, 2: 2}"
    }, delimiter = '|')
    public void testFreqDict(String input, String expected) {
        List<String> inputList = List.of(input.split(","));
        Map<?, Integer> actualResult = Task3.freqDict(inputList);
        Map<?, Integer> expectedResult = parseExpectedResult(expected);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    private Map<?, Integer> parseExpectedResult(String expected) {
        expected = expected.trim().substring(1, expected.length() - 1);
        String[] pairs = expected.split(",");
        Map<Object, Integer> result = new HashMap<>();

        for (String pair : pairs) {
            String[] keyValue = pair.trim().split(":");
            Object key = keyValue[0].trim();
            Integer value = Integer.parseInt(keyValue[1].trim());
            result.put(key, value);
        }

        return result;
    }
}
