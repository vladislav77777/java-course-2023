package edu.hw8;

import org.junit.jupiter.api.Test;

public class Task2Test {

    @Test
    public void testThreadPoolWithFibonacciExample() {
        int numbers = 40;
        try (ThreadPool threadPool = CustomThreadPool.create(numbers)) {
            threadPool.start();
            for (int i = 0; i < numbers; i++) {
                final int n = i;
                threadPool.execute(() -> {
                    long result = calculateFibonacci(n);
                    System.out.println("Fibonacci(" + n + ") = " + result);
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testWithoutPoolFibonacciExample() {
        int numbers = 40;

        for (int i = 0; i < numbers; i++) {
            long result = calculateFibonacci(i);
            System.out.println("Fibonacci(" + i + ") = " + result);
        }

    }

    private long calculateFibonacci(int n) {
        if (n <= 1) {
            return n;
        } else {
            return calculateFibonacci(n - 1) + calculateFibonacci(n - 2);
        }
    }
}
