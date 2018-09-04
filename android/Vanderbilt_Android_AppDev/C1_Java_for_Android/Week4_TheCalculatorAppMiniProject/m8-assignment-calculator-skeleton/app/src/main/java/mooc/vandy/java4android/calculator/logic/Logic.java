package mooc.vandy.java4android.calculator.logic;

import mooc.vandy.java4android.calculator.ui.ActivityInterface;

/**
 * Performs an operation selected by the user.
 */
public class Logic
       implements LogicInterface {
    /**
     * Reference to the Activity output.
     */
    protected ActivityInterface mOut;

    /**
     * Constructor initializes the field.
     */
    public Logic(ActivityInterface out){
        mOut = out;
    }

    /**
     * Perform the @a operation on @a argumentOne and @a argumentTwo.
     */
    public void process(int argumentOne,
                        int argumentTwo,
                        int operation) {
        // DONE -- start your code here

        try {

            Add obj = new Add(argumentOne, argumentTwo);
            /** Calling the compute method on add object
            *  Add class extends Subtract which extends Multiply
            *  which extends Divide which extends the Base class
            *  All the classes override compute method.
            *  Compute method in the Add class will perform addition if operation is 1
            *  otherwise will call the compute method from it's parent class(Subtract)
            *  and will continue to call compute method recursively
            *  until a matched value for operation is found*/
            mOut.print(obj.compute(operation));
        } catch (ArithmeticException e) {
            mOut.print("Can't divide by Zero\n");
        }
    }
}
