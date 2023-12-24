package edu.hw9;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

class FindFilesTask extends RecursiveTask<List<File>> {
    private final File directory;
    private final long targetSize;
    private final String targetExtension;

    FindFilesTask(File directory, long targetSize, String targetExtension) {
        this.directory = directory;
        this.targetSize = targetSize;
        this.targetExtension = targetExtension;
    }

    @Override
    protected List<File> compute() {
        List<File> matchingFiles = new ArrayList<>();
        List<FindFilesTask> subtasks = new ArrayList<>();

        File[] subDirectories = directory.listFiles(File::isDirectory);
        if (subDirectories != null) {
            for (File subDirectory : subDirectories) {
                FindFilesTask subtask = new FindFilesTask(subDirectory, targetSize, targetExtension);
                subtasks.add(subtask);
                subtask.fork();
            }
        }

        for (FindFilesTask subtask : subtasks) {
            matchingFiles.addAll(subtask.join());
        }

        File[] files = directory.listFiles(file ->
            file.isFile() && file.length() == targetSize && file.getName().endsWith(targetExtension)
        );

        if (files != null) {
            matchingFiles.addAll(List.of(files));
        }

        return matchingFiles;
    }
}
