package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

public class Task7Test {

    @ParameterizedTest
    @MethodSource("rotateRightArguments")
    @DisplayName("Циклический сдвиг вправо")
    void testRotateRight(int input, int shift, int expected) {
        assertThat(Task7.rotateRight(input, shift)).isEqualTo(expected);
    }

    private static Stream<Arguments> rotateRightArguments() {
        return Stream.of(
            Arguments.of(8, 4, 8),
            Arguments.of(16, 1, 8),
            Arguments.of(17, 2, 12)
        );
    }

    @ParameterizedTest
    @MethodSource("rotateLeftArguments")
    @DisplayName("Циклический сдвиг влево")
    void testRotateLeft(int input, int shift, int expected) {
        assertThat(Task7.rotateLeft(input, shift)).isEqualTo(expected);
    }

    private static Stream<Arguments> rotateLeftArguments() {
        return Stream.of(
            Arguments.of(7, 1, 7),
            Arguments.of(16, 1, 1),
            Arguments.of(17, 2, 6)
        );
    }

}
