package edu.hw9;

import java.util.logging.Logger;

class Graph {
    private final int vertices;
    private final boolean[][] adjacencyMatrix;
    private static final Logger LOGGER = Logger.getLogger(Graph.class.getName());

    boolean[] visited;

    Graph(int vertices) {
        this.vertices = vertices;
        this.adjacencyMatrix = new boolean[vertices][vertices];
        this.visited = new boolean[vertices];

    }

    public void addEdge(int source, int destination) {
        adjacencyMatrix[source][destination] = true;
        adjacencyMatrix[destination][source] = true;
    }

    public void depthFirstSearch(int startVertex) {
        depthFirstSearchRecursive(startVertex);
    }

    private synchronized void depthFirstSearchRecursive(int currentVertex) {
        if (visited[currentVertex]) {
            return;
        }
        LOGGER.info("Visited vertex: " + currentVertex);
        visited[currentVertex] = true;

        for (int neighbor = 0; neighbor < vertices; neighbor++) {
            if (adjacencyMatrix[currentVertex][neighbor] && !visited[neighbor]) {
                depthFirstSearchRecursive(neighbor);
            }
        }
    }
}

