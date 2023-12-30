package AlgorithmPackage;

import GraphPackage.*;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class MinimumTimeAlgorithm {

    private DirectedGraph graph;
    private HashMap<String, Integer> shortestPaths = new HashMap<>();
    private HashMap<String, Vertex> previousVertices = new HashMap<>();
    private HashMap<String, String> edgeIds = new HashMap<>();
    private PriorityQueue<VertexDistancePair> minHeap = new PriorityQueue<>();

    public MinimumTimeAlgorithm(DirectedGraph graph) {
        this.graph = graph;
    }

    // Method to find the shortest paths using Dijkstra's algorithm
    public void findShortestPaths(String startVertex, String stopVertex) {

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

                    if (!neighbor.isVisited() && (!shortestPaths.containsKey(neighbor.getName()) || newDistance < shortestPaths.get(neighbor.getName()))) {
                        minHeap.add(new VertexDistancePair(neighbor, newDistance));
                        previousVertices.put(neighbor.getName(), currentVertex);
                        edgeIds.put(neighbor.getName(), edge.getID()); // Store the edge ID
                        shortestPaths.put(neighbor.getName(), newDistance);
                    }
                    if (neighbor.getName().equalsIgnoreCase(stopVertex)) {
                        // Reached the stopVertex, reconstruct and print the path
                        newDistance = currentPair.getDistance() + edge.getWeight();
                        System.out.println(newDistance);
                        printShortestPath(previousVertices,startVertex , stopVertex, newDistance, edgeIds);
                        return;
                    }
                }
            }
        }

        // No path found to the stopVertex
        System.out.println("No path found from " + startVertex + " to " + stopVertex + "\n");
    }

    private void printShortestPath(HashMap<String, Vertex> previousVertices, String startVertex, String stopVertex, int weight, Map<String, String> edgeIds) {
        String currentVertexName = stopVertex;
        String tempEdge = edgeIds.get(currentVertexName);
        String startStation = currentVertexName;
        String endStation = "";
        int stationCounter = 0;
        Stack<String> pathStations = new Stack<>();
        Stack<String> lines = new Stack<>();

        lines.push(edgeIds.get(currentVertexName));
    
        while (currentVertexName != null) {
            if (!currentVertexName.equalsIgnoreCase(startStation))
                stationCounter++;
    
            if (!(tempEdge.equalsIgnoreCase(edgeIds.get(currentVertexName)))) {
                endStation = currentVertexName;
                pathStations.push(endStation + " - " + startStation + " (" + stationCounter + " stations)");
                startStation = currentVertexName;
                stationCounter = 0;
    
                if (edgeIds.get(currentVertexName) != null) {
                    lines.push(edgeIds.get(currentVertexName));
                }
    
                tempEdge = edgeIds.get(currentVertexName);
            }
    
            if (previousVertices.get(currentVertexName) != null) {
                currentVertexName = previousVertices.get(currentVertexName).getName();
            } else {
                currentVertexName = null;
            }
        }
    
        while (!pathStations.isEmpty()) {
            System.out.println("\nLine "+lines.pop() + ":");
            System.out.println(pathStations.pop());
        }

        System.out.println("\n" + weight / 60 + " min\n");
    }

    // Helper method to get a vertex by its name
    private Vertex getVertex(String vertexName) {
        for (Vertex vertex : graph.getVertices()) {
            if (vertex.getName().equalsIgnoreCase(vertexName)) {
                return vertex;
            }
        }
        return null;
    }

    // Private class to represent a pair of vertex and its distance
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
