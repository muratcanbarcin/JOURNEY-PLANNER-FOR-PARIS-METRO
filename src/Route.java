import java.util.ArrayList;

public class Route {
 private int arrivalTime;
 private int directionID;
 private String routeID;
 private String routeName;
 private int routeType;
 private ArrayList<Station> stations;

    public Route(int arrivalTime, int directionID, String routeID, String routeName, int routeType) {
        this.arrivalTime = arrivalTime;
        this.directionID = directionID;
        this.routeID = routeID;
        this.routeName = routeName;
        this.routeType = routeType;
    }

    public void addStation(Station s){
        stations.add(s);
    }

    protected class Station {
        private int stopID;
        private String stopName;
        private int stopSequence;

        public Station(int stopID, String stopName, int stopSequence) {
            this.stopID = stopID;
            this.stopName = stopName;
            this.stopSequence = stopSequence;
        }
    }
}


