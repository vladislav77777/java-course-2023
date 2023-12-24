package edu.hw9;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

class FindDirectoriesTask extends RecursiveTask<List<File>> {
    private final File directory;
    private final int threshold;

    FindDirectoriesTask(File directory, int threshold) {
        this.directory = directory;
        this.threshold = threshold;
    }

    @Override
    protected List<File> compute() {
        List<File> largeDirectories = new ArrayList<>();
        List<FindDirectoriesTask> subtasks = new ArrayList<>();

        File[] subDirectories = directory.listFiles(File::isDirectory);
        if (subDirectories != null) {
            for (File subDirectory : subDirectories) {
                FindDirectoriesTask subtask = new FindDirectoriesTask(subDirectory, threshold);
                subtasks.add(subtask);
                subtask.fork();
            }
        }

        for (FindDirectoriesTask subtask : subtasks) {
            largeDirectories.addAll(subtask.join());
        }

        File[] files = directory.listFiles(File::isFile);
        if (files != null && files.length > threshold) {
            largeDirectories.add(directory);
        }

        return largeDirectories;
    }
}
