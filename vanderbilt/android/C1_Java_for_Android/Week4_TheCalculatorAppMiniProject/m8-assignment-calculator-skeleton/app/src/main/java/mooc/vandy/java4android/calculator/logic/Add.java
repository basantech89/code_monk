package mooc.vandy.java4android.calculator.logic;

/**
 * Perform the Add operation.
 */
public class Add
    extends Subtract
        implements Computation {
    // DONE -- start your code here

    public Add(int x, int y) {
        super(x, y);
    }

    public String compute(int operation) {
        if (operation == ADDITION)
            return Integer.toString(getX() + getY());
        return super.compute(operation); //otherwise call the compute method
                                        // from the parent class(Subtract)
    }
}
