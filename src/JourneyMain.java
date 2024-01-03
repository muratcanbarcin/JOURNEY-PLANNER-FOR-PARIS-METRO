import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import AlgorithmPackage.FewerStopAlgorithm;
import AlgorithmPackage.MinimumTimeAlgorithm;
import GraphPackage.*;

public class JourneyMain {
    //attributes
    private DirectedGraph metroGraph;
    private ArrayList<Route> routes;
    public JourneyMain() throws IOException{ //constructor
        this.routes = new ArrayList<>();
        this.metroGraph = new DirectedGraph();

        //variables
        String csvFile = "Paris_RER_Metro.csv";
        String walk_edge_file = "walk_edges.txt";
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
                boolean new_route = true;
                for(Route route : routes){
                    if(routeLong.equalsIgnoreCase(route.getRouteName())){
                        new_route = false;
                    }
                }
                //Route add to routes arraylist if has not been ever
                if(new_route){
                    Route routeNew = new Route(directionID,routeShort,routeLong,routeType);
                    routeNew.setStations(new ArrayList<>());
                    routes.add(routeNew);
                    countRoute+=1;
                }
                //Station add to stations arraylist
                Route tempRoute = routes.get(countRoute);
                Route.Station s = tempRoute.new Station(arrivalTime,stopID,stopName,stopSequence);
                tempRoute.addStation(s);
                countStation+=1;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        buildGraph(); //create a DirectedGraph
        addWalkEdges(walk_edge_file); //add walk edges

        Scanner scan = new Scanner(System.in);

        System.out.print("\nTest (Y/N):");
        String choice = scan.nextLine();

        if (choice.equalsIgnoreCase("Y")){
            //test area

            String testFile = "Test100.csv";
            int testCounter = 0;
            long total_time = 0;

            try (BufferedReader br = new BufferedReader(new FileReader(testFile))) {
                String line2;

                while ((line2 = br.readLine()) != null) {


                    if (testCounter == 0){
                        testCounter++;
                        continue;
                    }

                    // CSV'den verileri çek
                    String[] parts = line2.split(",");

                    if (parts.length >= 3) {
                        String originStation = parts[0];
                        String destination = parts[1];
                        String preferetion = parts[2];

                        // Elde edilen verileri kullan
                        System.out.println("\n\nTest:"+testCounter+ " choice: " + preferetion +"\n----------------------------------------");

                        if(preferetion.equalsIgnoreCase("1")){
                            FewerStopAlgorithm algorithm = new FewerStopAlgorithm(metroGraph);
                            algorithm.findFewerStopsPath(originStation,destination,true);
                            total_time+=algorithm.getTime();
                        }
                        else if(preferetion.equalsIgnoreCase("0")){
                            MinimumTimeAlgorithm minimumTimeAlgorithm = new MinimumTimeAlgorithm(metroGraph);
                            minimumTimeAlgorithm.findShortestPaths(originStation,destination,true); //finds and prints the shortest path
                            total_time+=minimumTimeAlgorithm.getTime();
                        }

                        metroGraph.unvisit_all();

                        // Burada istediğiniz işlemleri gerçekleştirebilirsiniz.
                        // Örneğin, originStation, destination ve preference değişkenlerini kullanarak bir şeyler yapabilirsiniz.
                    }
                    testCounter++;
                }
                br.close();
                System.out.println("\nAverage Time: " + (total_time/testCounter));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{

            boolean Exit = false;

            while (!Exit){
                System.out.print("\nDo you want to continue? (Y/N): ");
                String exit_choice = scan.nextLine();

                if (exit_choice.equalsIgnoreCase("N"))
                    Exit = true;
                //Simple UI
                System.out.print("\nOrigin Station: ");
                String originStation = scan.nextLine();
                System.out.print("\nDestination: ");
                String destination = scan.nextLine();
                System.out.print("\nPreferetion (0=minimum time, 1=fewer stops): ");
                String preferetion  = scan.nextLine();

                if(preferetion.equalsIgnoreCase("1")){
                    FewerStopAlgorithm algorithm = new FewerStopAlgorithm(metroGraph);
                    algorithm.findFewerStopsPath(originStation,destination,false);
                }
                else if(preferetion.equalsIgnoreCase("0")){
                    MinimumTimeAlgorithm minimumTimeAlgorithm = new MinimumTimeAlgorithm(metroGraph);
                    minimumTimeAlgorithm.findShortestPaths(originStation,destination,false); //finds and prints the shortest path
                }

                metroGraph.unvisit_all();
            }
        }
    }

    public int buildGraph() {
        int edge_counter = 0;

        for (Route route : routes) { //foreach for routes
            for (int i = 0; i < route.getStations().size() - 1; i++) { //loop for stations
                String sourceStation = route.getStations().get(i).getStopName(); //assign source
                String destinationStation = route.getStations().get(i + 1).getStopName();//assign destination
                int weight = Math.abs(route.getStations().get(i).getArrivalTime() - route.getStations().get(i+1).getArrivalTime()); //calculate weight of edge
                String route_id = route.getRouteID();  //assign routeID

                metroGraph.addEdge(sourceStation, destinationStation, weight, route_id); //addEdge to graph
                edge_counter++;
            }
        }
        return edge_counter;
    }

    public void addWalkEdges(String edge_file) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(edge_file));
        String edge;
        
        while ((edge = br.readLine()) != null) {
            String[] edge_arr = edge.split(",");
            String sourceStation = edge_arr[0];
            String destinationStation = edge_arr[1];
            metroGraph.addEdge(sourceStation, destinationStation, 300, "walk");
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
