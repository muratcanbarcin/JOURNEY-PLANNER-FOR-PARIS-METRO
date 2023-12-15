package GraphPackage;
import java.util.Dictionary;
import java.util.Iterator;
import ADTPackage.*;
public class DirectedGraph<T> implements  GraphInterface<T> {
    private DictionaryInterface<T,VertexInterface<T>> vertices;
    private int edgeCount;

    public DirectedGraph(){
       // vertices = new LinkedDictionary<>();
        edgeCount =0;
    }


    @Override
    public boolean addVertex(T vertexLabel) {
        VertexInterface<T> addOutcome = vertices.add(vertexLabel, new Vertex<>(vertexLabel));
        return addOutcome==null;
    }

    @Override
    public boolean addEdge(T begin, T end, double edgeWeight) {
        boolean result = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);

        if((beginVertex != null)&&(endVertex!=null))
            result = beginVertex.connect(endVertex,edgeWeight);
        if(result)
            edgeCount++;

        return result;
    }

    @Override
    public boolean hasEdge(T begin, T end) {
        boolean found = false;
        VertexInterface<T> beginVertex = vertices.getValue(begin);
        VertexInterface<T> endVertex = vertices.getValue(end);

        if((beginVertex!=null)&&(endVertex!=null)){
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while (!found && neighbors.hasNext()){
                VertexInterface<T> nextNeighbor = neighbors.next();
                if(endVertex.equals(nextNeighbor))
                    found = true;
            }
        }
        return found;
    }

    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    @Override
    public int getNumberofVertices() {
        return vertices.getSize();
    }

    @Override
    public int getNumberofEdges() {
        return edgeCount;
    }

    @Override
    public void clear() {
        vertices.clear();
        edgeCount =0;
    }

    @Override
    public QueueInterface<T> geBreadthFirstTreval(T origin) {//not implemented
        return null;
    }

    @Override
    public QueueInterface<T> getDepthFirstTraversal(T origin) {//not implemented
        return null;
    }

    @Override
    public StackInterface<T> getTopologicalOrder() {//not implemented
        return null;
    }

    @Override
    public int getShortestPath(T begin, T end, StackInterface<T> path) {//not implemented
        return 0;
    }

    @Override
    public double getCheaptestPath(T begin, T end, StackInterface<T> path) {//not implemented
        return 0;
    }
}
