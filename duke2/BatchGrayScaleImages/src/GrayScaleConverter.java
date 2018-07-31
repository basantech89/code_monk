/*
* This class convert colorful images to gray images
*
*  @author Basant Soni
*  @version 1.0, July 27th 2018
* */

import edu.duke.*;
import java.io.*;

public class GrayScaleConverter {
    // Start with the image I wanted (inImage)
    public ImageResource makeGray (ImageResource inImage) {
        // Make a blank new image of the same size
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        // For each pixel in outImage
        for (Pixel pixel : outImage.pixels()) {
            // look at the corresponding pixel in inImage
            Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
            // compute inPixel's red + inPixel's blue + inPixel's green
            // divide that sum by 3 (call it average)
            int average = (inPixel.getRed() + inPixel.getBlue() + inPixel.getGreen()) / 3;
            // set pixel's red to average
            pixel.setRed(average);
            // set pixel's blue to average
            pixel.setBlue(average);
            // set pixel's green to average
            pixel.setGreen(average);
        }
        return outImage;
    }

    public ImageResource makrInvert (ImageResource inImage) {
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        for (Pixel pixel : outImage.pixels()) {
            Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
            // making inverted (negative) pixels
            pixel.setRed(255 - inPixel.getRed());
            pixel.setBlue(255 - inPixel.getBlue());
            pixel.setGreen(255 - inPixel.getGreen());
        }
        return outImage;
    }

    public void testGray () {
        ImageResource ir = new ImageResource();
        ImageResource gray = makeGray(ir);
        gray.draw();
    }

    public void selectAndConvert () {
        // Batch Processing of images
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            ImageResource inImage = new ImageResource(f);
            //ImageResource gray = makeGray(inImage);
            ImageResource gray = makrInvert(inImage);
            gray.draw();
            //gray.setFileName("gray-" + inImage.getFileName());
            gray.setFileName("inverted-" + inImage.getFileName());
            gray.save();
        }
    }

    public static void main (String[] args) {
        GrayScaleConverter converter = new GrayScaleConverter();
        //converter.testGray();
        converter.selectAndConvert();
    }
}
