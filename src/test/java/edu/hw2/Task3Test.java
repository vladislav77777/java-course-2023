//package edu.hw2;

//
//public class Task3Test {
//
//    private ConnectionManager manager;
//    private PopularCommandExecutor executor;
//

////    @BeforeEach
////    void setUp() {
////        manager = new FaultyConnectionManager();
////        int maxAttempts = 3;
////        executor = new PopularCommandExecutor(manager, maxAttempts);
////    }
////
////    @Test
////    void testUpdatePackages_MaxAttemptsReached() {
////        // имитация ошибки в соединении, но количество попыток выполнения команды ограничено, ошибка должна быть перехвачена и выброшено ConnectionException
////        manager = new FaultyConnectionManager();
////        executor = new PopularCommandExecutor(manager, 2);
////        assertThatExceptionOfType(ConnectionException.class)
////            .isThrownBy(() -> executor.updatePackages())
////            .withMessageContaining("Failed to execute command after 2 attempts");
////    }
//}
