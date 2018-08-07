package examples;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.core.Coordinate;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class MapWithButton extends PApplet {

    private UnfoldingMap map;
    int ch = 800, cw = 600;

    public void setup() {
        size(ch, cw, OPENGL);
        map = new UnfoldingMap(this, 25, 25, ch - 50, cw - 50, new
                Microsoft.HybridProvider());
        MapUtils.createDefaultEventDispatcher(this, map);
    }

    public void draw() {
        map.draw();
        drawButtons();
    }

    private void drawButtons() {
        fill(255, 255, 255);
        rect(75, 75, 25, 25);

        fill(100, 100, 100);
        rect(75, 125, 25, 25);
    }

    public void mouseReleased() {
        if (mouseX > 75 && mouseX < 100 && mouseY > 75 && mouseY < 100)
            background(color(255, 255, 255));
        else if (mouseX > 75 && mouseX < 100 && mouseY > 125 && mouseY < 150)
            background(color(100, 100, 100));
    }
}
