import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import AlgorithmPackage.FewerStopAlgorithm;
import AlgorithmPackage.MinimumTimeAlgorithm;
import GraphPackage.*;

public class JourneyMain {
    //attributes
    private static DirectedGraph metroGraph;
    private ArrayList<Route> routes;
    public JourneyMain() { //constructor
        this.routes = new ArrayList<>();
        this.metroGraph = new DirectedGraph();

        //variables
        String csvFile = "Paris_RER_Metro.csv";
        String walk_edge_file = "walk_edges.txt";
        String line;
        String cvsSplitBy = ",";
        int countRoute = -2;
        int countStation = -2;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) { //Loop skipping the first line
                if (countStation == -2) {
                    countStation += 1;
                    countRoute += 1;
                    continue;
                }

                //Separate each line in the CSV file with a comma
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

                //Has been route added to routes ArrayList control
                boolean new_route = true;
                for (Route route : routes) {
                    if (routeLong.equalsIgnoreCase(route.getRouteName())) {
                        new_route = false;
                    }
                }

                //Route add to routes ArrayList if it has not been added ever
                if (new_route) {
                    Route routeNew = new Route(directionID, routeShort, routeLong, routeType);
                    routeNew.setStations(new ArrayList<>());
                    routes.add(routeNew);
                    countRoute += 1;
                }

                //Station add to stations ArrayList
                Route tempRoute = routes.get(countRoute);
                Route.Station s = tempRoute.new Station(arrivalTime, stopID, stopName, stopSequence);
                tempRoute.addStation(s);
                countStation += 1;
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        buildGraph(); //create a DirectedGraph
        try {
            addWalkEdges(walk_edge_file); //add walk edges
        } catch (IOException e) {
            e.printStackTrace();
        }
        menu();
    }

    public void menu(){
        Scanner scan = new Scanner(System.in);

        while (true) {
            displayMenu();

            System.out.print("Enter your choice: ");
            String choice = scan.nextLine();

            switch (choice.toLowerCase()) {
                case "1":
                    planYourJourney(scan);
                    break;
                case "2":
                    testFile();
                    break;
                case "3":
                    stopSearch();
                    break;
                case "4":
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("------------------------------------------------------");
        System.out.println("\nJourney Planner for Paris Metro:\n");
        System.out.println("------------------------------------------------------");
        System.out.println("1. Plan Your Journey");
        System.out.println("2. Test File");
        System.out.println("3. Stop Search");
        System.out.println("4. Exit");
    }

    private static void planYourJourney(Scanner scan) {
        System.out.print("\nOrigin Station: ");
        String originStation = scan.nextLine();
        System.out.print("Destination: ");
        String destination = scan.nextLine();
        System.out.print("Preference (0=fewer stops , 1=minimum time): ");
        String preference = scan.nextLine();

        if (preference.equals("0") || preference.equals("1")) {
            if (preference.equals("1")) {
                MinimumTimeAlgorithm minimumTimeAlgorithm = new MinimumTimeAlgorithm(metroGraph);
                minimumTimeAlgorithm.findShortestPaths(originStation, destination);
            } else {
                FewerStopAlgorithm algorithm = new FewerStopAlgorithm(metroGraph);
                algorithm.findFewerStopsPath(originStation, destination);
            }
        } else {
            System.out.println("Invalid preference. Please enter 0 or 1.");
        }
        metroGraph.unvisit_all();

    }

    private static void testFile() {
        String testFile = "Test100.csv";
        int testCounter = 0;
        long totalTime = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(testFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (testCounter == 0) {
                    testCounter++;
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length >= 3) {
                    String originStation = parts[0];
                    String destination = parts[1];
                    String preference = parts[2];

                    System.out.println("\n\nTest: " + testCounter + "  " + originStation + " - " + destination + "  Choice: " + preference + "\n----------------------------------------");

                    if (preference.equals("0")) {
                        FewerStopAlgorithm algorithm = new FewerStopAlgorithm(metroGraph);
                        algorithm.findFewerStopsPath(originStation, destination);
                        totalTime += algorithm.getTime();
                        System.out.println("Search time: " + algorithm.getTime());
        
                    } else if (preference.equals("1")) {
                        MinimumTimeAlgorithm minimumTimeAlgorithm = new MinimumTimeAlgorithm(metroGraph);
                        minimumTimeAlgorithm.findShortestPaths(originStation, destination);
                        totalTime += minimumTimeAlgorithm.getTime();
                        System.out.println("Search time: " + minimumTimeAlgorithm.getTime());
                    }
                    

                    metroGraph.unvisit_all();
                    testCounter++;
                }
            }
            br.close();
            System.out.println("\nAverage Time: " + (totalTime / testCounter) + "ns");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopSearch() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the station name to search: ");
        String stationName = scanner.nextLine();

        System.out.println("Lines Passing through the '" + stationName + "' Station:");
        System.out.println("------------------------------------------------------");
        for (Route route : routes) {
            for (Route.Station station : route.getStations()) {
                if (station.getStopName().equalsIgnoreCase(stationName)) {
                    System.out.println("Line: "+ route.getRouteName());
                    break; // Break the inner loop as we found the station in this route
                }
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

    public void addWalkEdges(String edge_file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(edge_file))) {
            String edge;

            while ((edge = br.readLine()) != null) {
                String[] edge_arr = edge.split(",");
                String sourceStation = edge_arr[0];
                String destinationStation = edge_arr[1];
                metroGraph.addEdge(sourceStation, destinationStation, 300, "walk");
            }
        } catch (IOException e) {
            e.printStackTrace();
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
