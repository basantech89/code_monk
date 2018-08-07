package examples;

import processing.core.*;

public class MyPApplet extends PApplet {

    private PImage backgroundImg;
    private final static String source = "data/";

    public void setup () {
        size(200, 200);
        // load the image into memory
        backgroundImg = loadImage("palmTrees.jpg", "jpg");
        //background(200, 200, 200); fill in the background with yellow
    }

    // draw method that will be called whenever the canvas is resized
    public void draw () {
        // position the image on top left corner of canvas
        image(backgroundImg, 0 ,0);
        // resize the image in the memory
        backgroundImg.resize(0, height);
        fill(255, 209, 0); // yellow
        // a and b are center of ellipse, c and d are width and height of ellipse
        ellipse(width / 4, height / 5, width / 5, height / 5);
    }
}
