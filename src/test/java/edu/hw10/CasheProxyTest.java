package edu.hw10;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CasheProxyTest {

    @Test
    void testProxy() {
        FibCalculator calculator = new FibCalculatorImpl();
        FibCalculator proxy = CacheProxy.create(calculator, FibCalculator.class, "src/test/resources/disk");

        // Call method fib through proxy
        System.out.println(proxy.fib(5));

        // call the fib method again, now the result should be taken from the cache
        System.out.println(proxy.fib(5));

        // Call the fib method with a different value to recalculate the result and save it to cache
        System.out.println(proxy.fib(10));

        // Check for cache file on disk
        File cacheFile = new File("src/test/resources/disk/FibCalculator_fib_5.cache");
        System.out.println("Cache file exists: " + cacheFile.exists());
    }

    @Test
    void deserializeCache() { // check cached value
        String cacheFileName = "src/test/resources/disk/fib_10.cache";

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(cacheFileName))) {
            // deserialize the object from the file
            long cachedResult = (long) inputStream.readObject();

            System.out.println("Cached Result: " + cachedResult); // 55 - 10th cashed Fibonacci number
            assertEquals(cachedResult, 55);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
