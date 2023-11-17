package edu.hw3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Arrays;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.*;

public class Task5Test {

    @ParameterizedTest
    @MethodSource("provideData")
    public void testParseContacts(String input, String order, String expectedOutput) {
        String[] contacts = null;
        if (!(input == null || input.isEmpty())) {
            contacts = input.split(", ");
        }

        Task5.Contact[] sortedContacts = Task5.parseContacts(contacts, order); // function result
        String[] sortedContactsStrings =
            Arrays.stream(sortedContacts).map(Task5.Contact::getFullName).toArray(String[]::new);

        String[] expected =
            (expectedOutput == null || expectedOutput.isEmpty()) ? new String[] {} : expectedOutput.split(", ");
        assertThat(sortedContactsStrings).containsExactly(expected);
    }

    private static Stream<Arguments> provideData() {
        return Stream.of(
            Arguments.of(
                "John Locke, Thomas Aquinas, David Hume, Rene Descartes",
                "ASC",
                "Thomas Aquinas, Rene Descartes, David Hume, John Locke"
            ),
            Arguments.of("Paul, Leonhard Euler, Carl Gauss", "DESC", "Paul, Carl Gauss, Leonhard Euler"),
            Arguments.of("", "ASC", ""),
            Arguments.of(null, "DESC", "")
        );
    }
}
