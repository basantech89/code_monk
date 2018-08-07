package examples;

import processing.core.PApplet;
import processing.core.PImage;

public class MyPApplet2 extends PApplet {

    private PImage backgroundImg;
    private final static String source = "data/";

    public void setup () {
        size(400, 400);
        //background(255, 255, 0, 0);
        stroke(100); // set pen color
        backgroundImg = loadImage("palmTrees.jpg", "jpg");
    }

    public void draw () {
        image(backgroundImg, 0, 0);
        backgroundImg.resize(0, height);
        // Add drawing code for MyApplet2
        int[] color = sunColorsec(second()); //calculate color code for sun
        fill(color[0], color[1], color[2]); // set sun color
        ellipse(width / 4, height / 5, width / 4, height / 5);
    }

    public int[] sunColorsec(float second) {
        int[] rgb = new int[3];
        // scale the brightness of yellow based on the second
        // 30 sec is black, 0 sec is bright yellow
        float ratio = Math.abs(30 - second) / 30;
        rgb[0] = (int) (255 * ratio);
        rgb[1] = (int) (255 * ratio);
        rgb[2] = 0;
        return rgb;
    }
}
