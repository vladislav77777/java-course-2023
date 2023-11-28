package edu.hw8;

import org.junit.jupiter.api.Test;
import static edu.hw8.MultiThreadedPasswordCracker.multiThreadedCrackPasswords;
import static edu.hw8.PasswordCracker.singleThreadedCrackPasswords;

public class Task3Test {

    @Test
    void testSingleThreadedCrack() {
        long startTime = System.currentTimeMillis();
        singleThreadedCrackPasswords();
        long endTime = System.currentTimeMillis();
        System.out.println("Single-threaded execution time: " + (endTime - startTime) + " ms");

    }

    @Test
    void testMultiThreadedCrack() {
        long startTime = System.currentTimeMillis();
        multiThreadedCrackPasswords();
        long endTime = System.currentTimeMillis();
        System.out.println("Multi-threaded execution time: " + (endTime - startTime) + " ms");
    }
}
