package edu.hw6;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class Task2Test {


    @Test
    void testCloneFile() {
        Path filePath = Paths.get("src\\test\\resources\\data\\Tinkoff Bank Biggest Secret.txt");
        Task2.cloneFile(filePath);
        Task2.cloneFile(filePath);

        Path copiedFilePath = Paths.get("src\\test\\resources\\data\\Tinkoff Bank Biggest Secret — копия.txt");
        Path copiedFilePath2 = Paths.get("src\\test\\resources\\data\\Tinkoff Bank Biggest Secret — копия (2).txt");

        assertThat(Files.exists(copiedFilePath))
            .as("File is copied with the expected name")
            .isTrue();
        assertThat(Files.exists(copiedFilePath2))
            .as("File is copied with the expected name")
            .isTrue();
    }

}
