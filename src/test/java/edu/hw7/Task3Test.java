package edu.hw7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task3Test {

    @Test
    public void testAddAndFindByName() {
        Task3.CachingPersonDatabase database = new Task3.CachingPersonDatabase();
        Person person = new Person(1, "John Doe", "123 Main St", "555-1234");

        database.add(person);

        Person result = database.findByName("John Doe");

        assertEquals(person, result);
    }

    @Test
    public void testDeleteAndFindByPhone() {
        Task3.CachingPersonDatabase database = new Task3.CachingPersonDatabase();
        Person person = new Person(1, "Jane Doe", "456 Oak St", "555-5678");

        database.add(person);
        database.delete(1);

        Person result = database.findByPhone("555-5678");

        assertNull(result);
    }

    @Test
    public void testConcurrentAddAndFind() throws InterruptedException {
        Task3.CachingPersonDatabase database = new Task3.CachingPersonDatabase();

        // Создаем поток для добавления Person
        Thread addThread = new Thread(() -> {
            int id = 1;
            String name = "Person" + id;
            String address = "Address" + id;
            String phone = "Phone" + id;
            Person person = new Person(id, name, address, phone);
            database.add(person);
        });

        // Создаем поток для поиска Person
        Thread findThread = new Thread(() -> {
            int id = 1;
            String name = "Person" + id;
            String address = "Address" + id;
            String phone = "Phone" + id;

            // Ищем человека по всем атрибутам
            Person nameGet = database.findByName(name);
            Person phoneGet = database.findByPhone(phone);
            Person addressGet = database.findByAddress(address);
            System.out.println(nameGet + " " + phoneGet + " " + addressGet);
            boolean result = false;
            if (nameGet != null) { // eсли первый HE null
                if (addressGet != null && phoneGet != null) { // то и другие должны быть не null
                    result = true;
                }
            } else { // eсли первый null - то не важно какие другие
                result = true;
            }
            assertTrue(result);

        });

        // запускаем оба потока
        addThread.start();
//        Thread.sleep(10);
        findThread.start();

        // дожидаемся завершения обоих потоков
        addThread.join();
        findThread.join();
    }

    @ParameterizedTest
    @CsvSource({"10", "50", "10000"}) // Задаем количество повторений теста
    public void testConcurrentAddAndFind(int repeatCount) {
        Task3.CachingPersonDatabase database = new Task3.CachingPersonDatabase();

        try (ExecutorService executorService = Executors.newFixedThreadPool(repeatCount * 2)) {

            for (int i = 0; i < repeatCount; i++) {
                // Создаем поток для добавления Person
                executorService.submit(() -> {
                    int id = 1;
                    String name = "Person" + id;
                    String address = "Address" + id;
                    String phone = "Phone" + id;
                    Person person = new Person(id, name, address, phone);
                    database.add(person);
                });

                // Создаем поток для поиска Person
                executorService.submit(() -> {
                    int id = 1;
                    String name = "Person" + id;
                    String address = "Address" + id;
                    String phone = "Phone" + id;

                    // Ищем человека по всем атрибутам
                    Person nameGet = database.findByName(name);
                    Person phoneGet = database.findByPhone(phone);
                    Person addressGet = database.findByAddress(address);

                    // Проверяем результаты поиска
                    Assertions.assertTrue(nameGet == null || addressGet != null && phoneGet != null);
                });
            }

            // Завершаем выполнение потоков
            executorService.shutdown();
        }
    }

}
