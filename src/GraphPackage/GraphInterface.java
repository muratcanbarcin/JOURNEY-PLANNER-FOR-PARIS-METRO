package GraphPackage;

public interface GraphInterface {
    void addEdge(String source, String destination, int weight, String id);

    int size();

    void print();


}
