package edu.hw5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task8Test {

    @ParameterizedTest
    @CsvSource({
        // input, conditionIndex, expectedResult
        "000, ODD_LENGTH, true",
        "01, ODD_LENGTH, false",
        "1, ODD_LENGTH, true",
        "011, SPECIFIC_LENGTH_AND_STARTS_WITH, true",
        "1110, SPECIFIC_LENGTH_AND_STARTS_WITH, true",
        "11011, SPECIFIC_LENGTH_AND_STARTS_WITH, false",
        "0111, SPECIFIC_LENGTH_AND_STARTS_WITH, false",
        "000, MULTIPLE_OF_THREE_ZEROS, true",
        "00100010, MULTIPLE_OF_THREE_ZEROS, true",
        "0011, MULTIPLE_OF_THREE_ZEROS, false",
        "0101001, MULTIPLE_OF_THREE_ZEROS, false",
        "010101, NOT_ELEVEN_OR_TRIPLE_ONE, true",
        "1, NOT_ELEVEN_OR_TRIPLE_ONE, true",
        "11, NOT_ELEVEN_OR_TRIPLE_ONE, false",
        "111, NOT_ELEVEN_OR_TRIPLE_ONE, false",
        "101, ODD_ON_ODD_INDICES, true",
        "1011101, ODD_ON_ODD_INDICES, true",
        "1101, ODD_ON_ODD_INDICES, false",
        "0100, AT_LEAST_TWO_ZEROS_AT_MOST_ONE_ONE, true",
        "10001, AT_LEAST_TWO_ZEROS_AT_MOST_ONE_ONE, false",
        "1010, NO_CONSECUTIVE_ONES, true",
        "00101, NO_CONSECUTIVE_ONES, true",
        "1101, NO_CONSECUTIVE_ONES, false",
    })
    public void testConditions(String input, Task8.Condition conditionIndex, boolean expectedResult) {
        boolean result = Boolean.TRUE.equals(Task8.validateInput(input, conditionIndex));
        assertThat(result).isEqualTo(expectedResult);
    }
}
