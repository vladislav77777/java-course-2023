package edu.hw7;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class Task4 {

    private static final double FOUR = 4.0;

    public static class MonteCarloPi {

        public static double calculatePi(int iterations) {
            int circleCount = 0;
            for (int i = 0; i < iterations; i++) {
                double x = ThreadLocalRandom.current().nextDouble(0, 1);
                double y = ThreadLocalRandom.current().nextDouble(0, 1);
                if (isInsideCircle(x, y)) {
                    circleCount++;
                }
            }

            return FOUR * circleCount / iterations;
        }

        private static boolean isInsideCircle(double x, double y) {
            return x * x + y * y <= 1;
        }

    }

    public static class MonteCarloPiMultiThreaded {

        public static double calculatePi(int iterations, int numThreads) throws Exception {
            int totalCircleCount;
            /* Executor services помогает упростить выполнение задач и автоматически
             предоставляет пул потоков и интерфейс для назначения им задач.
             Таким образом, мы не работаем с классом Thread. */
            try (
                ExecutorService executorService = Executors.newFixedThreadPool(numThreads)) {
                // thread pool с фиксированным числом потоков

                int iterationsPerThread = iterations / numThreads;
                Future<Integer>[] futures = new Future[numThreads];

                for (int i = 0; i < numThreads; i++) {
                    futures[i] =
                        executorService.submit(() -> simulatePoints(iterationsPerThread));
                    // The submit() method queues a task for execution
                }

                totalCircleCount = 0;
                for (int i = 0; i < numThreads; i++) {
                    totalCircleCount += futures[i].get();
                    // Using the Future object, you can determine whether a task
                    // is finished (using the isDone() method) or access its result (using the get() method)
                }

                executorService.shutdown();
                // The shutdown() method doesn't cause immediate destruction of the ExecutorService. It will
                // make the ExecutorService stop accepting new tasks and shut down after all
                // running threads finish their current work
            }

            return FOUR * totalCircleCount / iterations;
        }

        private static int simulatePoints(int iterations) {
            int circleCount = 0;
            for (int i = 0; i < iterations; i++) {
                double x = ThreadLocalRandom.current().nextDouble(-1, 1);
                double y = ThreadLocalRandom.current().nextDouble(-1, 1);

                if (isInsideCircle(x, y)) {
                    circleCount++;
                }
            }
            return circleCount;
        }

        private static boolean isInsideCircle(double x, double y) {
            return x * x + y * y <= 1;
        }
    }
}
