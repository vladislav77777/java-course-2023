package edu.hw5;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {

    @ParameterizedTest
    @MethodSource("provideTestCases")
    public void testCalculateAverageTime(List<String> timeIntervals, String expected) {
        String result = Task1.calculateAverageTime(timeIntervals);
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideTestCases() {
        return Stream.of(
            Arguments.of(
                List.of(
                    "2022-03-12, 20:20 - 2022-03-12, 23:50",
                    "2022-04-01, 21:30 - 2022-04-02, 01:20"
                ),
                "3ч 40м"
            ),
            Arguments.of(
                List.of("2022-05-10, 12:00 - 2022-05-10, 12:30"),
                "0ч 30м"
            ),
            Arguments.of(
                List.of(
                    "2022-08-15, 23:45 - 2022-08-16, 00:15",
                    "2022-08-16, 00:00 - 2022-08-16, 01:00"
                ),
                "0ч 45м"
            ),
            Arguments.of(
                List.of(
                    "2022-09-01, 18:00 - 2022-09-02, 18:00",
                    "2022-09-03, 10:00 - 2022-09-04, 10:00"
                ),
                "24ч 0м"
            ),
            Arguments.of(
                List.of("2022-12-31, 23:59 - 2023-01-01, 00:01"),
                "0ч 2м"
            ),
            Arguments.of(
                List.of(),
                "0ч 0м"
            )
        );
    }

}
