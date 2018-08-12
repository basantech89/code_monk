package mooc.vandy.java4android.calculator.logic;

/**
 * Perform the Multiply operation.
 */
public class Multiply
        extends Divide
            implements Computation {
    // DONE -- start your code here

    public Multiply(int x, int y) {
        super(x, y);
    }

    public String compute(int operation) {
        if (operation == MULTIPLICATION)
            return Integer.toString(getX() * getY());
        return super.compute(operation); // otherwise call the compute method
                                        // from parent class(Divide)
    }
}
