package edu.hw3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.*;
public class Task4Test {

    @ParameterizedTest
    @CsvSource({
        "2, 'II'",
        "12, 'XII'",
        "16, 'XVI'",
        "99, 'XCIX'",
        "349, 'CCCXLIX'",
        "1984, 'MCMLXXXIV'"
    })
    public void testConvertToRoman(int arabic, String expectedRoman) {
        String actualRoman = Task4.convertToRoman(arabic);
        assertThat(actualRoman).isEqualTo(expectedRoman);
    }
}
