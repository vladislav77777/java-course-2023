package edu.hw6;

import org.junit.jupiter.api.Test;

public class Task6Test {

    @Test
    void testScanPorts() {
        System.out.printf("%-9s%-7s%-30s\n", "Протокол", "Порт", "Сервис");
        System.out.println("-----------------------------------------------");
        System.out.println(Task6.scanPorts());
    }
}
