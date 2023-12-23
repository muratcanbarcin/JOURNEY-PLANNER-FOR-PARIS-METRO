import GraphPackage.*;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class ShortestPathAlgorithm {

    private DirectedGraph graph;

    public ShortestPathAlgorithm(DirectedGraph graph) {
        this.graph = graph;
    }

    public HashMap<String, Integer> findShortestPaths(String startVertex) {
        HashMap<String, Integer> shortestPaths = new HashMap<>();
        PriorityQueue<VertexDistancePair> minHeap = new PriorityQueue<>();

        minHeap.add(new VertexDistancePair(getVertex(startVertex), 0));

        while (!minHeap.isEmpty()) {
            VertexDistancePair currentPair = minHeap.poll();
            Vertex currentVertex = currentPair.getVertex();

            if (!currentVertex.isVisited()) {
                currentVertex.visit();
                shortestPaths.put(currentVertex.getName(), currentPair.getDistance());

                for (Edge edge : currentVertex.getEdges()) {
                    Vertex neighbor = edge.getDestination();
                    if (!neighbor.isVisited()) {
                        int newDistance = currentPair.getDistance() + edge.getWeight();
                        minHeap.add(new VertexDistancePair(neighbor, newDistance));
                    }
                }
            }
        }
        return shortestPaths;
    }

    private Vertex getVertex(String vertexName) {
        for (Vertex vertex : graph.getVertices()) {
            if (vertex.getName().equalsIgnoreCase(vertexName)) {
                return vertex;
            }
        }
        return null;
    }

    private class VertexDistancePair implements Comparable<VertexDistancePair> {
        private Vertex vertex;
        private int distance;

        public VertexDistancePair(Vertex vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public Vertex getVertex() {
            return vertex;
        }

        public int getDistance() {
            return distance;
        }

        @Override
        public int compareTo(VertexDistancePair other) {
            return Integer.compare(this.distance, other.distance);
        }
    }
}
