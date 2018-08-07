package examples;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import module4.EarthquakeMarker;
import module4.OceanQuakeMarker;
import parsing.ParseFeed;
import processing.core.PApplet;

import java.util.List;

public class Tester extends PApplet {

    private String earthquakesURL = "test1.atom";

    public void testing() {
        List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
        Marker m = new OceanQuakeMarker(earthquakes.get(0));
        System.out.println(m);
        SimplePointMarker pm = new OceanQuakeMarker(earthquakes.get(0));
        EarthquakeMarker em = new OceanQuakeMarker(earthquakes.get(0));
        Object o = new SimplePointMarker();
        //System.out.println(em);
    }

    public static void main(String[] args) {
        Tester test = new Tester();
        test.testing();
    }
}
