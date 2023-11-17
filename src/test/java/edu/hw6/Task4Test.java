package edu.hw6;

import org.junit.jupiter.api.Test;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

public class Task4Test {

    @Test
    public void writers() {
        Path filePath = Path.of("data\\output.txt");

        try (OutputStream fileOutputStream = Files.newOutputStream(filePath, StandardOpenOption.CREATE);
             CheckedOutputStream checkedOutputStream = new CheckedOutputStream(fileOutputStream, new CRC32());
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                 bufferedOutputStream,
                 StandardCharsets.UTF_8
             );
             PrintWriter printWriter = new PrintWriter(outputStreamWriter)) {

            printWriter.println("Programming is learned by writing programs. ― Brian Kernighan");

            // ensure that data is flushed and checksum is updated
            printWriter.flush();
            checkedOutputStream.flush();

            System.out.println("Checksum: " + checkedOutputStream.getChecksum().getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
