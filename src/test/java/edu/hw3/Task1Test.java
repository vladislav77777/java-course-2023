package edu.hw3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.*;

public class Task1Test {

    @ParameterizedTest
    @CsvSource({
        "Hello world!, Svool dliow!",
        "Any fool can write code that a computer can understand. Good programmers write code that humans can understand. ― Martin Fowler, Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi",
        "123!@Hello, 123!@Svool"
    })
    public void testAtbash(String input, String expectedOutput) {
        String result = Task1.atbash(input);
        assertThat(result).isEqualTo(expectedOutput);
    }
}
