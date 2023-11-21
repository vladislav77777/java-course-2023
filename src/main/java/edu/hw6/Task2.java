package edu.hw6;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public final class Task2 {

    private Task2() {
    }

    private static final Logger LOGGER = Logger.getLogger(Task2.class.getName());

    public static void cloneFile(Path path) {
        try {
            String fileName = path.getFileName().toString();
            String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
            String extension = fileName.substring(fileName.lastIndexOf('.'));

            Path destinationPath = path.getParent();
            int copyNumber = 1;

            String copy = " — копия";
            while (Files.exists(destinationPath.resolve(
                baseName + copy + (copyNumber > 1 ? " (" + copyNumber + ")" : "") + extension))) {
                copyNumber++;
            }

            Path copiedFilePath = destinationPath.resolve(
                baseName + copy + (copyNumber > 1 ? " (" + copyNumber + ")" : "") + extension);

            Files.copy(path, copiedFilePath);

            LOGGER.info("Файл скопирован: " + copiedFilePath);

        } catch (Exception e) {
            LOGGER.severe("Error during file cloning");

        }
    }
}
