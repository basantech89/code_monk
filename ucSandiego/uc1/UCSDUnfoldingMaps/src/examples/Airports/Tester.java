package examples.Airports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tester {

    public static void main(String[] args) {
        int[] vals = {7, 16, 66, 43, 97, 51};
        Airport.selectionShort(vals);
        Airport.insertionShort(vals);
        List<Airport> airports = new ArrayList<>();
        Airport airport1 = new Airport("Montreal", "Canada", "YMX");
        Airport airport2 = new Airport("Lagos", "Nigeria", "LOS");
        Airport airport3 = new Airport("Essen", "Germany", "ESS");
        Airport airport4 = new Airport("Chicago", "USA", "ORD");
        Airport airport5 = new Airport("Beijing", "China", "PEK");
        Airport airport6 = new Airport("Sydney", "Australia", "SYD");
        Airport airport7 = new Airport("Quito", "Ecuador", "UIO");
        Airport airport8 = new Airport("India", "Agra", "AGR");
        airports.add(airport1);
        airports.add(airport2);
        airports.add(airport3);
        airports.add(airport4);
        airports.add(airport5);
        airports.add(airport6);
        airports.add(airport7);
        airports.add(airport8);
        for (Airport airport : airports)
            System.out.print(airport.getCity() + " ");
        Collections.sort(airports);
        System.out.println();
        for (Airport airport : airports)
            System.out.print(airport.getCity() + " ");

    }
}
