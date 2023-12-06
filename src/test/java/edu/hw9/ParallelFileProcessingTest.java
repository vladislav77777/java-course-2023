package edu.hw9;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class ParallelFileProcessingTest {

    @Test
    void testFileTraversing() {
        String rootPath = "./src/test/resources/fileSystem";
        File rootDirectory = new File(rootPath);

        // cоздаем ForkJoinPool с количеством доступных процессоров
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // поиск дииректорий, в которых больше 2 файлов
        FindDirectoriesTask findDirectoriesTask = new FindDirectoriesTask(rootDirectory, 2);
        List<File> largeDirectories = forkJoinPool.invoke(findDirectoriesTask);
        System.out.println("Directories with more than 2 files: ");
        for (File directory : largeDirectories) {
            System.out.println(directory);
        }

        // пооиск файлов по предикату: размер, расширение
        String targetExtension = ".txt";
        long targetSize = 10154; // в байтах
        FindFilesTask findFilesTask = new FindFilesTask(rootDirectory, targetSize, targetExtension);
        List<File> matchingFiles = forkJoinPool.invoke(findFilesTask);
        System.out.println("\nFiles matching criteria: extension := " + targetExtension + " and size := " + targetSize + " bytes" );
        for (File file : matchingFiles) {
            System.out.println(file);
        }
    }
}
