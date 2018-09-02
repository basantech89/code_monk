package mooc.vandy.java4android.diamonds.logic;

import android.util.Log;
import mooc.vandy.java4android.diamonds.ui.OutputInterface;

/**
 * This is where the logic of this App is centralized for this assignment.
 * <p>
 * The assignments are designed this way to simplify your early
 * Android interactions.  Designing the assignments this way allows
 * you to first learn key 'Java' features without having to beforehand
 * learn the complexities of Android.
 */
public class Logic
       implements LogicInterface {
    /**
     * This is a String to be used in Logging (if/when you decide you
     * need it for debugging).
     */
    public static final String TAG = Logic.class.getName();

    /**
     * This is the variable that stores our OutputInterface instance.
     * <p>
     * This is how we will interact with the User Interface [MainActivity.java].
     * <p>
     * It is called 'out' because it is where we 'out-put' our
     * results. (It is also the 'in-put' from where we get values
     * from, but it only needs 1 name, and 'out' is good enough).
     */
    private OutputInterface mOut;

    /**
     * This is the constructor of this class.
     * <p>
     * It assigns the passed in [MainActivity] instance (which
     * implements [OutputInterface]) to 'out'.
     */
    public Logic(OutputInterface out){
        mOut = out;
    }

    /**
     * This is the method that will (eventually) get called when the
     * on-screen button labeled 'Process...' is pressed.
     */
    public void process (int size) {

        // TODO -- add your code here
        hedge(size);    // Drawing the top edge
        for (int i = 1; i <= 2 * size - 1; i++) {
            for (int j = 1; j <= 2 * size + 2; j++) { diamond(size, i, j); }
        }
        hedge(size);    // Drawing the bottom edge
    }

    // Function to draw the horizontal edges
    public void hedge (int size) {
        for (int j = 1; j <= 2 * size + 2; j++) {
            if (j == 1) { mOut.print("+"); }
            else if (j == 2 * size + 2) { mOut.println("+"); }
            else { mOut.print("-"); }
        }
    }

    // Function to draw the diamond shape
    public void diamond (int size, int i, int j) {
        // Drawing the left/right side of picture frame i.e the | characters
        if (j == 1) { mOut.print("|"); }
        else if (j == 2 * size + 2) { mOut.println("|"); }
        else { spaces(size, i, j); }
    }

    // Function to draw spaces
    public void spaces (int size, int i, int j) {
        if (j <= size - i + 1 || j > size + i + 1 || j <= i - size + 1
                || j > 3 * size - i + 1) {
            mOut.print(" ");
        }
        // Drawing the Diamond Shape
        else { shape(size, i, j); }
    }

    // Function to draw the different chars for the diamond shape
    public void shape (int size, int i, int j) {
        // Drawing the / and < chars for top half
        if (j == size + 2 - i) {
            if (i == size && j == 2) { mOut.print("<"); }
            else { mOut.print("/"); }
        }
        // Drawing the \ chars for bottom half
        else if (j == 2 + i - size) { mOut.print("\\"); }
        else if (j == size + i + 1) {
            // Drawing the > and \ chars for top half
            if (i == size && j == 2 * size + 1) { mOut.print(">"); }
            else { mOut.print("\\"); }
        }
        // Drawing / chars for bottom half
        else if (j == 3 * size - i + 1) { mOut.print("/"); }
        // Drawing - and == chars for both top and bottom halves
        else {
            if (i % 2 == 0) { mOut.print("-"); }
            else { mOut.print("="); }
        }
    }
}