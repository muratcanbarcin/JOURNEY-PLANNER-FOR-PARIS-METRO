package GraphPackage;
import java.util.Iterator;
import java.util.NoSuchElementException;
import ADTPackage.*;

class Vertex<T> implements VertexInterface<T> {
    private T label;
   // private ListWithIteratorInterface<Edge> edgeList;
    private boolean visited;
    private VertexInterface<T> previousVertex;
    private double cost;

    public Vertex(T vertexLabel) {
        this.label = vertexLabel;
      //  this.edgeList = new LinkedListWithIterator<>();
        this.visited = false;
        this.previousVertex = null;
        this.cost = 0;
    }
    @Override
    public T getLabel() {
        return label;
    }

    @Override
    public void visit() {//not implemented

    }

    @Override
    public void unvisit() {//not implemented

    }

    @Override
    public boolean isVisited() {
        return visited;
    }

    @Override
    public Iterator<VertexInterface<T>> getNeighborIterator() {//not implemented
        return null;
    }

    @Override
    public Iterator<Double> getWeightIterator() {//not implemented
        return null;
    }

    @Override
    public boolean hasNeighbor() {//not implemented
        return false;
    }

    @Override
    public VertexInterface<T> getUnvisitedNeighbor() {//not implemented
        return null;
    }

    @Override
    public void setPredecessor(VertexInterface<T> predecessor) {//not implemented

    }

    @Override
    public VertexInterface<T> getPredecessor() {//not implemented
        return null;
    }

    @Override
    public boolean hasPredecessor() {//not implemented
        return false;
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
                //edgeList.add(new Edge(endVertex, edgeWeight));
                result = true;
            }
        }
        return result;
    }
    @Override
    public boolean connect(VertexInterface<T> endVertex){return  connect(endVertex,0);}

    protected class Edge{
        private VertexInterface<T> vertex; //Vertex at end of edge
        private double weight;

        protected  Edge(VertexInterface<T> endVertex, double edgeWeight){
            vertex = endVertex;
            weight = edgeWeight;
        }
        protected VertexInterface<T> getEndVertex(){return vertex;}
        protected double getWeight(){return weight;}
    }

}
