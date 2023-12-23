import java.util.ArrayList;

public class Route {
 private int directionID;
 private String routeID;
 private String routeName;
 private int routeType;
 private ArrayList<Station> stations;

    public Route(int directionID, String routeID, String routeName, int routeType) {
        this.directionID = directionID;
        this.routeID = routeID;
        this.routeName = routeName;
        this.routeType = routeType;
    }
    public int getDirectionID() {
        return directionID;
    }

    public void setDirectionID(int directionID) {
        this.directionID = directionID;
    }

    public String getRouteID() {
        return routeID;
    }

    public void setRouteID(String routeID) {
        this.routeID = routeID;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public int getRouteType() {
        return routeType;
    }

    public void setRouteType(int routeType) {
        this.routeType = routeType;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }

    public void addStation(Station s){
        stations.add(s);
    }

    protected class Station {
        private int arrivalTime;
        private int stopID;
        private String stopName;
        private int stopSequence;

        public Station(int arrivalTime,int stopID, String stopName, int stopSequence) {
            this.stopID = stopID;
            this.stopName = stopName;
            this.stopSequence = stopSequence;
            this.arrivalTime = arrivalTime;

        }
        public int getArrivalTime() {
            return arrivalTime;
        }

        public void setArrivalTime(int arrivalTime) {
            this.arrivalTime = arrivalTime;
        }

        public int getStopID() {
            return stopID;
        }

        public void setStopID(int stopID) {
            this.stopID = stopID;
        }

        public String getStopName() {
            return stopName;
        }

        public void setStopName(String stopName) {
            this.stopName = stopName;
        }

        public int getStopSequence() {
            return stopSequence;
        }

        public void setStopSequence(int stopSequence) {
            this.stopSequence = stopSequence;
        }
    }
}


