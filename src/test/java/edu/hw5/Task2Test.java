package edu.hw5;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

class Task2Test {

    @ParameterizedTest
    @MethodSource("yearsProvider")
    void testFindFridaysThe13th(int year, List<LocalDate> expectedFridays) {
        List<LocalDate> actualFridays = Task2.findFridaysThe13th(year);
        assertThat(actualFridays).containsExactlyElementsOf(expectedFridays);
    }

    @ParameterizedTest
    @MethodSource("datesProvider")
    void testFindNextFridayThe13th(LocalDate inputDate, LocalDate expectedNextFriday13th) {
        LocalDate actualNextFriday13th = Task2.findNextFridayThe13th(inputDate);
        assertThat(actualNextFriday13th).isEqualTo(expectedNextFriday13th);
    }

    private static Stream<Arguments> yearsProvider() {
        return Stream.of(
            Arguments.of(2022, List.of(LocalDate.of(2022, 5, 13))),
            Arguments.of(2023, List.of(LocalDate.of(2023, 1, 13), LocalDate.of(2023, 10, 13))),
            Arguments.of(1925, List.of(LocalDate.of(1925, 2, 13), LocalDate.of(1925, 3, 13), LocalDate.of(1925, 11, 13)))

        );
    }

    private static Stream<Arguments> datesProvider() {
        return Stream.of(
            Arguments.of(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1, 13)),
            Arguments.of(LocalDate.of(2022, 12, 1), LocalDate.of(2022, 12, 13)),
            Arguments.of(LocalDate.of(2023, 1, 14), LocalDate.of(2023, 1, 13))
            // Add more test cases as needed
        );
    }
}
