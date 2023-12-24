import GraphPackage.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class ShortestPathAlgorithm {

    private DirectedGraph graph;

    public ShortestPathAlgorithm(DirectedGraph graph) {
        this.graph = graph;
    }

    public HashMap<String, Integer> findShortestPaths(String startVertex, String stopVertex) {
        HashMap<String, Integer> shortestPaths = new HashMap<>();
        HashMap<String, Vertex> previousVertices = new HashMap<>();
        HashMap<String, String> edgeIds = new HashMap<>(); // Map to store edge IDs in the shortest path
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
                    int newDistance = currentPair.getDistance() + edge.getWeight();
    
                    if (!neighbor.isVisited()) {
                        if (!shortestPaths.containsKey(neighbor.getName()) || newDistance < shortestPaths.get(neighbor.getName())) {
                            minHeap.add(new VertexDistancePair(neighbor, newDistance));
                            previousVertices.put(neighbor.getName(), currentVertex);
                            edgeIds.put(neighbor.getName(), edge.getID()); // Store the edge ID
                            shortestPaths.put(neighbor.getName(), newDistance);
                        }
                    }
    
                    if (neighbor.getName().equalsIgnoreCase(stopVertex)) {
                        // Reached the stopVertex, reconstruct and print the path
                        printShortestPath(previousVertices, startVertex, stopVertex, newDistance, edgeIds);
                        return shortestPaths;
                    }
                }
            }
        }
    
        // No path found to the stopVertex
        System.out.println("No path found from " + startVertex + " to " + stopVertex + "\n");
        return shortestPaths;
    }
    
    private void printShortestPath(HashMap<String, Vertex> previousVertices, String startVertex, String stopVertex, int weight, Map<String, String> edgeIds) {
        System.out.println("Shortest path from " + startVertex + " to " + stopVertex + ": " + weight);
        String currentVertexName = stopVertex;
        while (currentVertexName != null) {
            System.out.print(currentVertexName);
            if (!currentVertexName.equals(startVertex)) {
                System.out.print(" <" + edgeIds.get(currentVertexName) + "- "); // Print the edge ID
            }
            currentVertexName = previousVertices.get(currentVertexName) != null ?
                                previousVertices.get(currentVertexName).getName() : null;
        }
        System.out.println("\n");
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
