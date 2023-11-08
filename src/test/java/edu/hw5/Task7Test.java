package edu.hw5;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task7Test {

    @ParameterizedTest
    @MethodSource("testCases")
    public void testValidateString(String input, boolean expected) {
        boolean result = Task7.validateString(input);
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> testCases() {
        return Stream.of(
            Arguments.of("010", true),
            Arguments.of("1000", true),
            Arguments.of("10101", true),
            Arguments.of("000", true),
            Arguments.of("111", true),
            Arguments.of("10", true),
            Arguments.of("1100", true),
            Arguments.of("1010", false),
            Arguments.of("11100", false),
            Arguments.of("", false)
        );
    }
}
