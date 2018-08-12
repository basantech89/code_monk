package examples;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LifeExpectancy extends PApplet {

    private UnfoldingMap map;
    private Map<String, Float> lifeExp;
    private final static String source = "data/";
    private static String fileName = "LifeExpectancyWorldBankModule3.csv";
    private List<Feature> countries;
    private List<Marker> countryMarkers;

    public void setup() {
        size(1000, 600, OPENGL);
        map = new UnfoldingMap(this, 50, 50, 900, 500, new
                Google.GoogleMapProvider());
        MapUtils.createDefaultEventDispatcher(this, map);
        // processing the csv file
        lifeExp = loadLifeExp("LifeExpectancyWorldBankModule3.csv");
        countries = GeoJSONReader.loadData(this, "countries.geo.json");
        countryMarkers = MapUtils.createSimpleMarkers(countries);
        map.addMarkers(countryMarkers);
        // coloring the markers
        shadeCountries();
    }

    private Map<String, Float> loadLifeExp(String fName) {
        // String is the country and Float is the lifeExpectancy
        Map<String, Float> lifeExp = new HashMap<>();
        String[] rows = loadStrings(fName);
        for (String row : rows) {
            String[] cols = row.split(",");
            if (cols.length == 6 && !cols[5].equals(".."))
                lifeExp.put(cols[4], Float.parseFloat(cols[5]));
        }
        return lifeExp;
    }

    private void shadeCountries() {
        for (Marker marker : countryMarkers) {
            String countryID = marker.getId();
            if (lifeExp.containsKey(countryID)) {
                float lifeexp = lifeExp.get(countryID);
                int colorLevel = (int) map(lifeexp, 40, 90, 10, 255);
                marker.setColor(color(255 - colorLevel, 100, colorLevel));
            }
            else
                // if the country is not in the map, set a shade of gray
                marker.setColor(color(150, 150, 150));
        }
    }

    public void draw() {
        map.draw();
    }
}
