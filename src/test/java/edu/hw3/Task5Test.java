package edu.hw3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.*;

public class Task5Test {

    @ParameterizedTest
    @MethodSource("provideData")
    public void testParseContacts(String input, String order, String expectedOutput) {
        String[] contacts = null;
        if (input == null || input.isEmpty()) {
            input = null;
        } else {
            contacts = input.split(", ");
        }

        String[] sortedContacts = Task5.parseContacts(contacts, order); // function result
        String[] expected =
            (expectedOutput == null || expectedOutput.isEmpty()) ? new String[] {} : expectedOutput.split(", ");
        assertThat(sortedContacts).containsExactly(expected);
    }

    private static Stream<Arguments> provideData() {
        return Stream.of(
            Arguments.of("John Locke, Thomas Aquinas, David Hume, Rene Descartes", "ASC", "Thomas Aquinas, Rene Descartes, David Hume, John Locke"),
            Arguments.of("Paul Erdos, Leonhard Euler, Carl Gauss", "DESC", "Carl Gauss, Leonhard Euler, Paul Erdos"),
            Arguments.of("", "ASC", ""),
            Arguments.of(null, "DESC", "")
        );
    }
}
