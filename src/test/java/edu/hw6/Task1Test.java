package edu.hw6;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {

    @Test
    public void saveAndLoadTest() {
        Task1 diskMap = new Task1("data/diskmap.txt");

        // Add multiple entries
        diskMap.put("key1", "value1");
        diskMap.put("key2", "value2");
        diskMap.put("key3", "value3");

        // Save to file
        diskMap.saveToFile();
        diskMap.clear();
        // Create a new instance to test loading
        Task1 loadedDiskMap = new Task1("data/diskmap.txt");

        // Verify that all entries are loaded
        assertThat(loadedDiskMap.size()).isEqualTo(3);
        assertThat(loadedDiskMap.get("key1")).isEqualTo("value1");
        assertThat(loadedDiskMap.get("key2")).isEqualTo("value2");
        assertThat(loadedDiskMap.get("key3")).isEqualTo("value3");
    }
}
