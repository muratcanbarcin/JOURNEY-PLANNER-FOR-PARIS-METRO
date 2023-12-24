import java.util.*;
import GraphPackage.*;

public class FewerStopAlgorithm {
    private DirectedGraph graph;

    public FewerStopAlgorithm(DirectedGraph graph) {
        this.graph = graph;
    }

    public void findFewerStopsPath(String startVertex, String endVertex) {
        Queue<Vertex> queue = new LinkedList<>();
        Map<Vertex, Vertex> parentMap = new HashMap<>();

        Vertex start = getVertex(startVertex);
        Vertex end = getVertex(endVertex);

        if (start == null || end == null) {
            System.out.println("Invalid start or end vertex");
            return;
        }

        queue.add(start);
        parentMap.put(start, null);

        while (!queue.isEmpty()) {
            Vertex currentVertex = queue.poll();

            for (Edge edge : currentVertex.getEdges()) {
                Vertex neighbor = edge.getDestination();

                if (!parentMap.containsKey(neighbor)) {
                    queue.add(neighbor);
                    parentMap.put(neighbor, currentVertex);
                }
            }
        }

        printPath(start, end, parentMap);
    }

    private void printPath(Vertex start, Vertex end, Map<Vertex, Vertex> parentMap) {
        List<String> path = new ArrayList<>();
        Vertex current = end;

        while (current != null) {
            path.add(current.getName());
            current = parentMap.get(current);
        }

        System.out.println("Origin station: " + start.getName());
        System.out.println("Destination station: " + end.getName());
        System.out.println("Preference: Fewer Stops\n");

        System.out.println("Suggestion:\n");
        String tempEdgeName = getEdgeBetweenVertices(path.get(path.size()-1),path.get(path.size()-2)).getID();
        String startStation = path.get(path.size()-1);
        String endStation = " ";
        System.out.println("Line " + tempEdgeName + ":");
        int stationCounter =0;
        for (int i = path.size() - 1; i > 0; i--) {
            stationCounter++;
            String currentVertexName = path.get(i);
            String nextVertexName = path.get(i - 1);

            Edge edge = getEdgeBetweenVertices(currentVertexName, nextVertexName);
            if(!edge.getID().equalsIgnoreCase(tempEdgeName) || nextVertexName.equalsIgnoreCase(end.getName())){
                endStation = nextVertexName;
                System.out.println(startStation+" - "+endStation+" ("+stationCounter +" stations)");
                startStation = nextVertexName;
                tempEdgeName = edge.getID();
                stationCounter =0;
                if (edge != null&& !nextVertexName.equalsIgnoreCase(end.getName())) {
                    System.out.println("Line " + edge.getID() + ":");
                }

            }


        }
    }


    private Vertex getVertex(String vertexName) {
        for (Vertex vertex : graph.getVertices()) {
            if (vertex.getName().equalsIgnoreCase(vertexName)) {
                return vertex;
            }
        }
        return null;
    }

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
