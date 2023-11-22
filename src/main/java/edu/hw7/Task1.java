package edu.hw7;

import java.util.concurrent.atomic.AtomicInteger;

public final class Task1 {

    private static final int NUMBER = 10000;

    Task1() {
    }

    private final AtomicInteger value = new AtomicInteger(0);

    public void increment() {
        for (int i = 0; i < NUMBER; i++) {
            value.incrementAndGet();
        }
    }

    public int get() {
        return value.get();
    }
}

