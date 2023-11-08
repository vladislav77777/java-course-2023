package edu.hw5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task8Test {

    @ParameterizedTest
    @CsvSource({
        // input, conditionIndex, expectedResult
        "000, 1, true",
        "01, 1, false",
        "1, 1, true",
        "011, 2, true",
        "1110, 2, true",
        "11011, 2, false",
        "0111, 2, false",
        "000, 3, true",
        "00100010, 3, true",
        "0011, 3, false",
        "0101001, 3, false",
        "010101, 4, true",
        "1, 4, true",
        "11, 4, false",
        "111, 4, false",
        "101, 5, true",
        "1011101, 5, true",
        "1101, 5, false",
        "0100, 6, true",
        "10001, 6, false",
        "1010, 7, true",
        "00101, 7, true",
        "1101, 7, false",
    })
    public void testConditions(String input, int conditionIndex, boolean expectedResult) {
        boolean result = Task8.validateInput(input, conditionIndex);
        assertThat(result).isEqualTo(expectedResult);
    }
}
