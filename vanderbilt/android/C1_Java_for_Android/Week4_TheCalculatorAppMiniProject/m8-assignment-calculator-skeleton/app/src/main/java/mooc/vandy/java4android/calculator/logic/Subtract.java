package mooc.vandy.java4android.calculator.logic;

/**
 * Perform the Subtract operation.
 */
public class Subtract
        extends Multiply
            implements Computation {
    // DONE -- start your code here

    public Subtract(int x, int y) {
        super(x, y);
    }

    public String compute(int operation) {
        if (operation == SUBTRACTION)
            return Integer.toString(getX() - getY());
        return super.compute(operation); // otherwise call the compute method
                                        // from parent class(Multiply)
    }
}
