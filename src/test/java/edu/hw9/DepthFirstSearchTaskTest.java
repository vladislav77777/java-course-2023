package edu.hw9;

import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DepthFirstSearchTaskTest {

    @Test
    void testDFS() {
        int numVertices = 6;
        Graph graph = new Graph(numVertices);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 5);

        ExecutorService executorService = Executors.newFixedThreadPool(numVertices);

        // запускаем задачи поиска в глубину для каждой вершины
        for (int startVertex = 0; startVertex < numVertices; startVertex++) {
            executorService.execute(new DepthFirstSearchTask(graph, startVertex));
        }

        executorService.shutdown();

        // ожидаем завершения выполнения всех задач
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
    }
}
