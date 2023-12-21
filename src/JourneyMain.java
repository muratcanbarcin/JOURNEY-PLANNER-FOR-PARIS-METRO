import java.io.*;
import java.util.ArrayList;

public class JourneyMain {
    private ArrayList<Route> routes;
    public JourneyMain() {
        this.routes = new ArrayList<>();

        String csvFile = "Paris_RER_Metro.csv";
        String line;
        String cvsSplitBy = ",";
        int countRoute =-2;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                if (countRoute == -2) {
                    countRoute+=1;
                    continue;
                }
                // CSV dosyasındaki her satırı virgül ile ayır
                String[] data = line.split(cvsSplitBy);

                // Verileri doğru değişkenlere atama
                int stopID = Integer.parseInt(data[0]);
                String stopName = data[1];
                int arrivalTime = Integer.parseInt(data[2]);
                int stopSequence = Integer.parseInt(data[3]);
                int directionID = Integer.parseInt(data[4]);
                String routeShort = data[5];
                String routeLong = data[6];
                int routeType = Integer.parseInt(data[7]);

                boolean flag = routes.contains(routeLong);

                if(!flag){
                    Route routeNew = new Route(arrivalTime,directionID,routeShort,routeLong,routeType);
                    routes.add(routeNew);
                    countRoute+=1;
                }
                Route tempRoute = routes.get(countRoute);
                Route.Station s = tempRoute.new Station(stopID,stopName,stopSequence);
                tempRoute.addStation(s);
                System.out.println(countRoute);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
