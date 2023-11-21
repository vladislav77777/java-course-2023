package edu.hw5;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task5Test {

    @ParameterizedTest
    @MethodSource("carNumbersProvider")
    void testIsValidCarNumber(String carNumber, boolean expectedResult) {
        boolean isValid = Task5.isValidCarNumber(carNumber);
        assertThat(isValid).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> carNumbersProvider() {
        return Stream.of(
            Arguments.of("А123ВЕ777", true),
            Arguments.of("О777ОО177", true),
            Arguments.of("123АВЕ777", false),
            Arguments.of("А123ВГ77", false),
            Arguments.of("А123ВЕ7777", false)
        );
    }
}
