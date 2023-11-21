package edu.hw5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Task6Test {

    @ParameterizedTest
    @MethodSource("subsequenceProvider")
    public void testIsSubsequence(String s, String t, boolean expectedResult) {
        boolean result = Task6.isSubsequence(s, t);
        assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> subsequenceProvider() {
        return Stream.of(
            Arguments.of("abc", "achfdbaabgabcaabg", true),
            Arguments.of("xyz", "achfdbaabgabcaabg", false),
            Arguments.of("ab", "achfdbaabgabcaabg", true),
            Arguments.of("cba", "achfdbaabgabcaabg", false)
        );
    }
}
