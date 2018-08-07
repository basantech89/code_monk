package examples;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

import java.awt.*;
import java.util.*;
import java.util.List;

public class EarthquakeCityMap extends PApplet {

    private UnfoldingMap map;

    public void setup() {
        size(900, 600, OPENGL);
        // v and v1 are x, y coordinates
        // v2 and v3 are width and height
        map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
        map.zoomToLevel(2);
        MapUtils.createDefaultEventDispatcher(this, map);

        PointFeature valEq = new PointFeature(new Location(-38.14f, -73.03f));
        valEq.addProperty("title", "Valdivia, Chile");
        valEq.addProperty("magnitude", "9.5");
        valEq.addProperty("date", "May 22, 1960");
        valEq.addProperty("year", "1960");

        PointFeature alaskaEq = new PointFeature(new Location(61.02f,-147.65f));
        alaskaEq.addProperty("title", "1964 Great Alaska Earthquake");
        alaskaEq.addProperty("magnitude", "9.2");
        alaskaEq.addProperty("date", "March 28, 1964");
        alaskaEq.addProperty("year", 1964);

        PointFeature sumatraEq = new PointFeature(new Location(3.30f,95.78f));
        sumatraEq.addProperty("title", "Off the West Coast of Northern Sumatra");
        sumatraEq.addProperty("magnitude", "9.1");
        sumatraEq.addProperty("date", "February 26, 2004");
        sumatraEq.addProperty("year", 2004);


        PointFeature japanEq = new PointFeature(new Location(38.322f,142.369f));
        japanEq.addProperty("title", "Near the East Coast of Honshu, Japan");
        japanEq.addProperty("magnitude", "9.0");
        japanEq.addProperty("date", "March 11, 2011");
        japanEq.addProperty("year", 2011);


        PointFeature kamchatkaEq = new PointFeature(new Location(52.76f,160.06f));
        kamchatkaEq.addProperty("title", "Kamchatka");
        kamchatkaEq.addProperty("magnitude", "9.0");
        kamchatkaEq.addProperty("date", "November 4, 1952");
        kamchatkaEq.addProperty("year", 1952);

        List<PointFeature> bigEqs = new ArrayList<>();
        bigEqs.add(valEq);
        bigEqs.add(alaskaEq);
        bigEqs.add(sumatraEq);
        bigEqs.add(japanEq);
        bigEqs.add(kamchatkaEq);

        List<Marker> markers = new ArrayList<>();
        for (PointFeature eq : bigEqs)
            markers.add(new SimplePointMarker(eq.getLocation(), eq.getProperties()));
        map.addMarkers(markers);
    }

    public void draw() {
        background(10);
        map.draw();
        //addKey();
    }
}
