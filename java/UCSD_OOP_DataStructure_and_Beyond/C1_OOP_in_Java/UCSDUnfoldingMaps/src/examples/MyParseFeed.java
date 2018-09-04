package examples;

import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PApplet;
import processing.data.XML;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyParseFeed extends PApplet {

    private PApplet p;

    public List<PointFeature> parseEarthQuake (PApplet p, String fileName) {
        List<PointFeature> features = new ArrayList<>();
        XML rss = p.loadXML(fileName);
        XML[] itemXML = rss.getChildren("entry");
        XML[] catXML = itemXML[0].getChildren("category");


        System.out.println(itemXML[0].getChild("georss:point"));
        System.out.println(Arrays.toString(catXML));

        return features;
    }

    public void test () {
        parseEarthQuake(this, "2.5_week.atom");
    }

    public static void main(String[] args) {
        MyParseFeed feed = new MyParseFeed();
        feed.test();
    }
}
