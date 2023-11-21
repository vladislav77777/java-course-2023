package edu.hw5;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task4Test {

    @ParameterizedTest
    @MethodSource("passwordProvider")
    public void testContainsSpecialCharacter(String password, boolean expected) {
        boolean result = Task4.containsSpecialCharacter(password);
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> passwordProvider() {
        return Stream.of(
            Arguments.of("password123", false),
            Arguments.of("P@ssw0rd", true),
            Arguments.of("!Hello123", true),
            Arguments.of("Hello123|", true),
            Arguments.of("onlyletters", false),
            Arguments.of("~!@#$%^&*|", true),
            Arguments.of("1234567890", false)
        );
    }
}
