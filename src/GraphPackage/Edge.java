package GraphPackage;

public class Edge {
    private Vertex source;
    private Vertex destination;
    private int weight;
    private String id;

    public Edge(Vertex source, Vertex destination, int weight, String id) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.id = id;
    }

    public Vertex getSource() {
        return source;
    }

    public void setSource(Vertex source) {
        this.source = source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getID(){
        return id;
    }

}
