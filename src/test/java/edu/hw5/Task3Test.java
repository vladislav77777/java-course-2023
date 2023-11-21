package edu.hw5;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {

    private static final Task3 dateParser = new Task3();

    @ParameterizedTest
    @MethodSource("dateProvider")
    public void testParseDate(String input, Optional<LocalDate> expectedDate) {
        Optional<LocalDate> result = dateParser.parseDate(input);
        assertThat(result).isEqualTo(expectedDate);
    }

    private static Stream<Arguments> dateProvider() {
        return Stream.of(
            Arguments.of("2020-10-10", Optional.of(LocalDate.of(2020, 10, 10))),
            Arguments.of("1/3/1976", Optional.of(LocalDate.of(1976, 1, 3))),
            Arguments.of("tomorrow", Optional.of(LocalDate.now().plusDays(1))),
            Arguments.of("today", Optional.of(LocalDate.now())),
            Arguments.of("yesterday", Optional.of(LocalDate.now().minusDays(1))),
            Arguments.of("1 day ago", Optional.of(LocalDate.now().minusDays(1))),
            Arguments.of("2234 days ago", Optional.of(LocalDate.now().minusDays(2234))),
            Arguments.of("invalid", Optional.empty())
        );
    }
}
