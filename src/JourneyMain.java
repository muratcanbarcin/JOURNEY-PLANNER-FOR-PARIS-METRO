import java.io.*;
import java.util.ArrayList;
import GraphPackage.*;

public class JourneyMain {
    private DirectedGraph metroGraph;
    private ArrayList<Route> routes;
    public JourneyMain() {
        this.routes = new ArrayList<>();
        this.metroGraph = new DirectedGraph();

        String csvFile = "Paris_RER_Metro.csv";
        String line;
        String cvsSplitBy = ",";
        int countRoute =-2;
        int countStation =-2;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) { //Loop skipping first line
                if (countStation == -2) {
                    countStation+=1;
                    countRoute+=1;
                    continue;
                }
                //Separate each line in CSV file with comma
                String[] data = line.split(cvsSplitBy);

                //Assign data to the correct variables
                int stopID = Integer.parseInt(data[0]);
                String stopName = data[1];
                int arrivalTime = Integer.parseInt(data[2]);
                int stopSequence = Integer.parseInt(data[3]);
                int directionID = Integer.parseInt(data[4]);
                String routeShort = data[5];
                String routeLong = data[6];
                int routeType = Integer.parseInt(data[7]);

                //Has been route added to routes arraylist control
                boolean flag = true;
                for(Route route : routes){
                    if(routeLong.equalsIgnoreCase(route.getRouteName())){
                        flag = false;
                    }
                }
                //Route add to routes arraylist if has not been ever
                if(flag){
                    Route routeNew = new Route(directionID,routeShort,routeLong,routeType);
                    routeNew.setStations(new ArrayList<>());
                    routes.add(routeNew);
                    countRoute+=1;
                    //System.out.println("Route Number:" + countRoute); //testPrint
                }
                //Station add to stations arraylist
                Route tempRoute = routes.get(countRoute);
                Route.Station s = tempRoute.new Station(arrivalTime,stopID,stopName,stopSequence);
                tempRoute.addStation(s);
                countStation+=1;
              //  System.out.println("Station Number:" + countStation); //testPrint

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

       buildGraph();
       metroGraph.print();
    }
    public void buildGraph() {
        for (Route route : routes) {
            for (int i = 0; i < route.getStations().size() - 1; i++) {
                String sourceStation = route.getStations().get(i).getStopName();
                String destinationStation = route.getStations().get(i + 1).getStopName();
                int weight = Math.abs(route.getStations().get(i).getArrivalTime() - route.getStations().get(i+1).getArrivalTime());

                metroGraph.addEdge(sourceStation, destinationStation, weight);
            }
        }
    }
    public void displayRoutesAndStations() { //test display method
        System.out.println("Routes:");

        for (Route route : routes) {
            System.out.println("Route: " + route.getRouteName());
            System.out.println("Stations:");

            for (Route.Station station : route.getStations()) {
                System.out.println("  Station: " + station.getStopName());
            }

            System.out.println("------");
        }
    }
}
