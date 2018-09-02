package mooc.vandy.java4android.calculator.logic;
import java.lang.reflect.Array;

/**
 * Perform the Divide operation.
 */
// All other classes must extends Divide class
public class Divide
    extends Base
            implements Computation {
    // DONE -- start your code here


    public Divide(int x, int y) {
        super(x, y);
    }

    public String compute(int operation) {
        return getX() / getY() + " R: " + getX() % getY(); // Ran out of other options
                                            // so value of operation must be 4, no need to check
    }
}
