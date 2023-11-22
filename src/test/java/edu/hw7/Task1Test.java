package edu.hw7;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Task1Test {

    @Test
    public void testAtomicCounter() throws InterruptedException {
        Task1 counter = new Task1();

        // Создаем и запускаем несколько потоков
        Thread thread1 = new Thread(counter::increment);
        Thread thread2 = new Thread(counter::increment);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals(20000, counter.get());
    }

}
