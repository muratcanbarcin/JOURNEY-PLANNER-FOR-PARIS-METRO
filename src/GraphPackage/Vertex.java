package GraphPackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Vertex {
    //Attributes
    private String name;
    private ArrayList<Edge> edges;
    private Vertex parent;
    private boolean visited;
    private double cost;

    public Vertex(String name) {
        this.name = name;
        edges = new ArrayList<Edge>();
        parent = null;
        visited = false;
    }

    public void addEdge(Edge e) {
        edges.add(e);
    } //add Edge to Vertex

    //getters and setters
    public ArrayList<Edge> getEdges() {
        return this.edges;
    }
    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vertex getParent() {
        return parent;
    }

    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void visit() {
        this.visited = true;
    }

    public void unvisit() {
        this.visited = false;
    }

    public boolean isVisited() {
        return this.visited;
    }
    /* No usage
    public ArrayList<Vertex> getNeighbors() {
        ArrayList<Vertex> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            neighbors.add(edge.getDestination());
        }
        return neighbors;
    }

    public Vertex getUnvisitedNeighbor() {
        Vertex result = null;

        Iterator<Vertex> neighbors = getNeighborIterator();
        while (neighbors.hasNext() && (result == null))
        {
            Vertex nextNeighbor = neighbors.next();
            if (!nextNeighbor.isVisited())
                result = nextNeighbor;
        } // end while

        return result;
    }


     */
    public boolean hasEdge(String neighbor) { //If between of vertex and neighbor has edge return true, if has not edge return false
        boolean found = false; //flag
        Iterator<Vertex> neighbors = getNeighborIterator();
        while (neighbors.hasNext()) //neigbors loop
        {
            Vertex nextNeighbor = neighbors.next();
            if (nextNeighbor.getName().equalsIgnoreCase(neighbor)) //neighbor control
            {
                found = true;
                break;
            }
        } // end while

        return found;
    }

    public Iterator<Vertex> getNeighborIterator() //Method of finding neighbors of vertex
    {
        return new NeighborIterator();
    } // end getNeighborIterator

    private class NeighborIterator implements Iterator<Vertex> //Class of finding neighbors of vertex
    {
        int edgeIndex = 0; //attribute
        private NeighborIterator() //constructor
        {
            edgeIndex = 0;
        }

        public boolean hasNext()
        {
            return edgeIndex < edges.size();
        }

        public Vertex next()
        {
            Vertex nextNeighbor = null;

            if (hasNext())
            {
                nextNeighbor = edges.get(edgeIndex).getDestination();
                edgeIndex++;
            }
            else
                throw new NoSuchElementException();

            return nextNeighbor;
        } // end next

        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
