package AlgorithmPackage;

import java.util.*;
import GraphPackage.*;

public class FewerStopAlgorithm {
    private DirectedGraph graph;

    public FewerStopAlgorithm(DirectedGraph graph) {
        this.graph = graph;
    }

    // Queue for BFS traversal
    private Queue<Vertex> queue = new LinkedList<>();

    // Map to store the parent relationship during traversal
    private HashMap<Vertex, Vertex> parentMap = new HashMap<>();

    // Method to find the path with fewer stops using BFS
    public void findFewerStopsPath(String startVertex, String endVertex) {
        Vertex start = getVertex(startVertex);
        Vertex end = getVertex(endVertex);

        if (start == null || end == null) {
            System.out.println("Invalid start or end vertex");
            return;
        }

        // Initialize BFS traversal
        queue.add(start);
        parentMap.put(start, null);

        while (!queue.isEmpty()) {
            Vertex currentVertex = queue.poll();

            // Explore neighbors
            for (Edge edge : currentVertex.getEdges()) {
                Vertex neighbor = edge.getDestination();

                // If the neighbor hasn't been visited, add to the queue and update the parent map
                if (!parentMap.containsKey(neighbor)) {
                    queue.add(neighbor);
                    parentMap.put(neighbor, currentVertex);
                }
            }
        }

        // Print the path with fewer stops
        printPath(start, end, parentMap);
    }

    // Method to print the path with fewer stops
    private void printPath(Vertex start, Vertex end, Map<Vertex, Vertex> parentMap) {
        ArrayList<String> path = new ArrayList<>();
        Vertex current = end;

        // Build the path in reverse order
        while (current != null) {
            path.add(current.getName());
            current = parentMap.get(current);
        }

        // Print origin and destination information
        System.out.println("Origin station: " + start.getName());
        System.out.println("Destination station: " + end.getName());
        System.out.println("Preference: Fewer Stops\n");

        System.out.println("Suggestion:\n");
        String tempEdgeName = getEdgeBetweenVertices(path.get(path.size() - 1), path.get(path.size() - 2)).getID();
        String startStation = path.get(path.size() - 1);
        String endStation = " ";
        System.out.println("Line " + tempEdgeName + ":");
        int stationCounter = 0;

        // Iterate through the path to print stations and lines
        for (int i = path.size() - 1; i > 0; i--) {
            stationCounter++;
            String currentVertexName = path.get(i);
            String nextVertexName = path.get(i - 1);

            Edge edge = getEdgeBetweenVertices(currentVertexName, nextVertexName);

            // Check if the line changes or it's the end station
            if (!edge.getID().equalsIgnoreCase(tempEdgeName) || nextVertexName.equalsIgnoreCase(end.getName())) {
                endStation = nextVertexName;
                System.out.println(startStation + " - " + endStation + " (" + stationCounter + " stations)");
                startStation = nextVertexName;
                tempEdgeName = edge.getID();
                stationCounter = 0;

                // Print the new line if it exists and not the end station
                if (edge != null && !nextVertexName.equalsIgnoreCase(end.getName())) {
                    System.out.println("Line " + edge.getID() + ":");
                }
            }
        }
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

    // Helper method to get the edge between two vertices
    private Edge getEdgeBetweenVertices(String source, String destination) {
        Vertex sourceVertex = getVertex(source);
        Vertex destinationVertex = getVertex(destination);

        if (sourceVertex != null && destinationVertex != null) {
            for (Edge edge : sourceVertex.getEdges()) {
                if (edge.getDestination().equals(destinationVertex)) {
                    return edge;
                }
            }
        }

        return null;
    }
}
