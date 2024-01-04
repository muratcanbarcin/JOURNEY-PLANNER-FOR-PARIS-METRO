package GraphPackage;

import java.util.HashMap;
import java.util.Iterator;

public class DirectedGraph implements GraphInterface {
    private HashMap<String, Vertex> vertices; //attribute

    public DirectedGraph() {
        this.vertices = new HashMap<>();
    } //Constructor

    public void addEdge(String source, String destination, int weight, String id) { //method of addEdge between source and destination

        Vertex source_v = vertices.get(source); //assign source
        Vertex destination_v = vertices.get(destination); //assign destination

        if (!(source_v != null && destination_v != null && source_v.hasEdge(destination))) { //source and destination control
            if (vertices.get(source) == null) { //if source null put source to vertices
                source_v = new Vertex(source);
                vertices.put(source, source_v);
            }

            if (vertices.get(destination) == null) {//if destination null put destination to vertices
                destination_v = new Vertex(destination);
                vertices.put(destination, destination_v);
            }
            //create new edge
            Edge edge = new Edge(source_v, destination_v, weight, id);
            source_v.addEdge(edge);
        }
    }

    public void print() { //Display method

        for (Vertex v : vertices.values()) {
            System.out.print(v.getName() + " -> ");

            Iterator<Vertex> neighbors = v.getNeighborIterator();
            while (neighbors.hasNext())
            {
                Vertex n = neighbors.next();
                System.out.print(n.getName() + " ");
            }
            System.out.println();
        }
    }

    public Iterable<Vertex> getVertices() {
        return vertices.values();
    }

    public int size() {
        return vertices.size();
    } //size of vertices

    private void resetVertices() {  //reset of all vertices
        for (Vertex v : vertices.values()) {
            v.unvisit();
            v.setCost(0);
            v.setParent(null);
        }
    }

    public void unvisit_all(){
        for (Vertex v : vertices.values()) {
            v.unvisit();
        }
    }
}
