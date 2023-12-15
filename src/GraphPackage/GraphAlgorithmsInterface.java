package GraphPackage;
import ADTPackage.*;

public interface GraphAlgorithmsInterface<T> {

    public QueueInterface<T> geBreadthFirstTreval(T origin);
    public QueueInterface<T> getDepthFirstTraversal(T origin);
    public StackInterface<T> getTopologicalOrder();
    public int getShortestPath(T begin,T end, StackInterface<T> path);
    public double getCheaptestPath(T begin,T end, StackInterface<T> path);
}
