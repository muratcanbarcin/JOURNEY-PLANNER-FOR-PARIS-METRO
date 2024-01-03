package AlgorithmPackage;

import java.util.*;
import GraphPackage.*;

public class FewerStopAlgorithm {
    private DirectedGraph graph;

    private long time;

    public FewerStopAlgorithm(DirectedGraph graph) {
        this.graph = graph;
        time = 0;
    }

    // Queue for BFS traversal
    private Queue<Vertex> queue = new LinkedList<>();

    // Map to store the parent relationship during traversal
    private HashMap<Vertex, Vertex> parentMap = new HashMap<>();

    // Method to find the path with fewer stops using BFS
    public void findFewerStopsPath(String startVertex, String endVertex,boolean test) {
        long start_time = System.nanoTime();
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
        if (test){
            time = System.nanoTime()-start_time;
            System.out.println(startVertex + " - " + endVertex + " time: " + time);
        }
        else
            printPath(start, end, parentMap);
    }

    private void printPath(Vertex start, Vertex end, Map<Vertex, Vertex> parentMap) {
        ArrayList<String> path = new ArrayList<>();
        ArrayList<String> starts = new ArrayList<>();
        ArrayList<String> ends = new ArrayList<>();
        ArrayList<String> edgeID = new ArrayList<>();
        Vertex current = end;
        String tempEdgeName = "";

        // Build the path in reverse order
        while (current != null) {
            path.add(current.getName());
            current = parentMap.get(current);
        }
        
        path = getReverseList(path);
                    
        for (int i = 0; i < path.size() - 1; i++) {
            String currentVertexName = path.get(i);
            String nextVertexName = path.get(i + 1);
    
            Edge edge = getEdgeBetweenVertices(currentVertexName, nextVertexName);

            if (!edge.getID().equalsIgnoreCase(tempEdgeName)) {
                starts.add(currentVertexName);
                ends.add(currentVertexName);
                edgeID.add(edge.getID());
                tempEdgeName = edge.getID();
            }
        }
        
        String endStation = path.get(path.size() - 1);

        ends.add(endStation);
        ends.remove(0);

        for (int i = 0; i < starts.size(); i++){
            if (edgeID.get(i).equalsIgnoreCase("walk")){
                System.out.println("\nWalk :");
                System.out.println(String.format("%s - %s", starts.get(i), ends.get(i)));
            }
                
            else{
                 System.out.println(String.format("\nLine %s:", edgeID.get(i)));
                 System.out.println(String.format("%s - %s (%d stations)", starts.get(i), ends.get(i), (path.indexOf(ends.get(i))-path.indexOf(starts.get(i)))));
            }
        }

        System.out.println("\n" + getTotalWeight(path)/60 + " min\n");
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

    private ArrayList<String> getReverseList(ArrayList<String> ListToReverse){
        ArrayList<String> ReversedList = new ArrayList<>();
        ListIterator<String> listIterator = ListToReverse.listIterator(ListToReverse.size());
        while(listIterator.hasPrevious()){
           String element = listIterator.previous();
           ReversedList.add(element);
        }
        return ReversedList;
    }

    private int getTotalWeight(ArrayList<String> list){
        int total_weight = 0;
        int weight;

        for (int i = 0; i < list.size()-1; i++){
            weight = getEdgeBetweenVertices(list.get(i), list.get(i+1)).getWeight();
            total_weight+=weight;
        }

        return total_weight;
    }

    public long getTime(){
        return time;
    }
}
