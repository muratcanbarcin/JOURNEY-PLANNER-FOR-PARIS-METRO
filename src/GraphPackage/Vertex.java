package GraphPackage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import ADTPackage.*;

class Vertex<T> implements VertexInterface<T> {
    private T label;
    private ArrayList<Edge> edgeList;
    private boolean visited;
    private VertexInterface<T> previousVertex;
    private double cost;

    public Vertex(T vertexLabel) {
        this.label = vertexLabel;
        this.edgeList = new ArrayList<>();
        this.visited = false;
        this.previousVertex = null;
        this.cost = 0;
    }
    @Override
    public T getLabel() {
        return label;
    }

    @Override
    public void visit() {visited=true;}

    @Override
    public void unvisit() {visited=false;}

    @Override
    public boolean isVisited() {
        return visited;
    }

    public Iterator<VertexInterface<T>> getNeighborIterator() {
        ArrayList<VertexInterface<T>> neighbors = new ArrayList<>();
        for (Edge edge : edgeList) {
            neighbors.add(edge.getEndVertex());
        }
        return neighbors.iterator();
    }

    @Override
    public Iterator<Double> getWeightIterator() {
        ArrayList<Double> weights = new ArrayList<>();
        for (Edge edge : edgeList) {
            weights.add(edge.getWeight());
        }
        return weights.iterator();
    }

    @Override
    public boolean hasNeighbor() {
        return !edgeList.isEmpty();
    }
    @Override
    public VertexInterface<T> getUnvisitedNeighbor() {
        for (Edge edge : edgeList) {
            if (!edge.getEndVertex().isVisited()) {
                return edge.getEndVertex();
            }
        }
        return null;
    }
    @Override
    public void setPredecessor(VertexInterface<T> predecessor) {
        this.previousVertex = predecessor;
    }

    @Override
    public VertexInterface<T> getPredecessor() {
        return previousVertex;
    }

    @Override
    public boolean hasPredecessor() {
        return previousVertex != null;
    }

    @Override
    public void setCost(double newCost) {this.cost=cost;}

    @Override
    public double getCost() {return cost;}
    @Override
    public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {
        boolean result = false;
        if (!this.equals(endVertex)) {
            Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
            boolean duplicateEdge = false;
            while (!duplicateEdge && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor)) {
                    duplicateEdge = true;
                }
            }
            if (!duplicateEdge) {
                edgeList.add(new Edge(endVertex, edgeWeight));
                result = true;
            }
        }
        return result;
    }
    @Override
    public boolean connect(VertexInterface<T> endVertex){return  connect(endVertex,0);}

    protected class Edge{
        private VertexInterface<T> vertex;
        private double weight;

        protected  Edge(VertexInterface<T> endVertex, double edgeWeight){
            vertex = endVertex;
            weight = edgeWeight;
        }
        protected VertexInterface<T> getEndVertex(){return vertex;}
        protected double getWeight(){return weight;}
    }

}
