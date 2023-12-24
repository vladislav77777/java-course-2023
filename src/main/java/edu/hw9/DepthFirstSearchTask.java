package edu.hw9;

class DepthFirstSearchTask implements Runnable {
    private final Graph graph;
    private final int startVertex;

    DepthFirstSearchTask(Graph graph, int startVertex) {
        this.graph = graph;
        this.startVertex = startVertex;
    }

    @Override
    public void run() {
        graph.depthFirstSearch(startVertex);
    }
}
