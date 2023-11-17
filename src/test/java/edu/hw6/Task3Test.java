package edu.hw6;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {

    @Test
    public void dataFilterTest() {
        Path dir = Paths.get("data\\dataFilter");

        DirectoryStream.Filter<Path> filter = AbstractFilter.regularFile()
            .and(AbstractFilter.readable())
            .and(AbstractFilter.largerThan(9))
            .and(AbstractFilter.magicNumber())
            .and(AbstractFilter.globMatches("txt"))
            .and(AbstractFilter.regexContains(".*[k].*"));

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter)) {
            // collect entries into a list for further assertion
            List<Path> fileList = new ArrayList<>();
            entries.forEach(fileList::add);

            assertThat(fileList)
                .hasSize(2)
                .containsExactly(
                    Paths.get("data\\dataFilter", "diskmap.txt"), // in our case
                    Paths.get("data\\dataFilter", "Tinkoff Bank Biggest Secret.txt")
                );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void dataFilterPNGTest() {
        Path dir = Paths.get("data\\dataFilter");

        DirectoryStream.Filter<Path> filter = AbstractFilter.regularFile()
            .and(AbstractFilter.readable())
            .and(AbstractFilter.writable())
            .and(AbstractFilter.largerThan(2 * 1024 * 1024)) // MB <--> byte * 1024 * 1024
            .and(AbstractFilter.magicNumber(0x89, 'P', 'N', 'G'))
            .and(AbstractFilter.globMatches("png"));

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir, filter)) {
            // collect entries into a list for further assertion
            List<Path> fileList = new ArrayList<>();
            entries.forEach(fileList::add);

            assertThat(fileList)
                .hasSize(1)
                .containsExactly(
                    Paths.get("data\\dataFilter", "inno.png") // in our case
                );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
