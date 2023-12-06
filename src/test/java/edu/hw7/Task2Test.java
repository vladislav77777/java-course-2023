package edu.hw7;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task2Test {

    @Test
    public void testCalculateFactorial() {
        assertEquals(1, Task2.calculateFactorial(0));
        assertEquals(1, Task2.calculateFactorial(1));
        assertEquals(120, Task2.calculateFactorial(5));
        assertEquals(3628800, Task2.calculateFactorial(10));
    }

    @Test
    public void testCalculateFactorialWithNegativeInput() {
        try {
            Task2.calculateFactorial(-1);
        } catch (IllegalArgumentException e) {
//            e.printStackTrace(); // Success
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException but no exception was thrown");
    }
}

