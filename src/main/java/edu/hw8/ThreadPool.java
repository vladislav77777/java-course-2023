package edu.hw8;

interface ThreadPool extends AutoCloseable {
    void start();

    void execute(Runnable runnable);
}

