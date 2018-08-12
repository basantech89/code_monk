package mooc.vandy.java4android.gate.logic;

import java.util.Random;

import mooc.vandy.java4android.gate.ui.OutputInterface;

/**
 * This class uses your Gate class to manage a herd of snails.  We
 * have supplied you will the code necessary to execute as an app.
 * You must fill in the missing logic below.
 */
public class HerdManager {
    /**
     * Reference to the output.
     */
    private OutputInterface mOut;

    /**
     * The input Gate object.
     */
    private Gate mEastGate;

    /**
     * The output Gate object.
     */
    private Gate mWestGate;

    /**
     * Maximum number of iterations to run the simulation.
     */
    private static final int MAX_ITERATIONS = 10;

    /**
    * Size of the HERD
    */
    public static final int HERD = 24;

    /**
     * Constructor initializes the fields.
     */
    public HerdManager(OutputInterface out,
                       Gate westGate,
                       Gate eastGate) {
        mOut = out;

        mWestGate = westGate;
        mWestGate.open(Gate.IN);

        mEastGate = eastGate;
        mEastGate.open(Gate.OUT);
    }

    // DONE -- Fill your code in here
    public void simulateHerd (Random rand) {
        int pen = this.HERD;  // No of snails in the pen
        int pasture = 0;    // No of snails in the pasture
        printStatus(pen, pasture);
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            // snailsToMove Method will return an object array of the selected gate
            // and number of snails who can move
            Object[] arr = snailsToMove(rand, pen, pasture);
            Gate gate = (Gate)arr[0];   // gate is an object type, casting it to gate
            int movableSnails = (int)arr[1];    // movableSnails is an object type, casting it to int
            pen += gate.thru(rand.nextInt(movableSnails) + 1);  // moving some snails randomly
            pasture = 24 - pen; // changes in the pasture
            printStatus(pen, pasture);  // print the left snails in pen and pasture
        }
    }

    // Helper method to print snails in pen and pasture
    private void printStatus(int pen, int pasture) {
        mOut.println("There are " + pen + " snails in the pen and " + pasture +
                " snails in the pasture");
    }

    // Helper method to return number of snails who can move from pen or pasture
    // Returns an object array of movable snails and the selected gate
    private Object[] snailsToMove(Random rand, int pen, int pasture) {
        Gate gate = null;
        int movableSnails = 0;  // no of snails who can move
        if (pasture == 0) {
            gate = mEastGate;
            movableSnails = pen;
        }
        else if (pen == 0) {
            gate = mWestGate;
            movableSnails = pasture;
        }
        else {
            gate = selectGate(rand);    // selecting a gate randomly
            if (gate == mEastGate && pen > 0 && pasture < 24) movableSnails = pen;
            else movableSnails = pasture;
        }
        Object[] arr = {gate, movableSnails};
        return arr;
    }

    // Helper method to randomly select a gate
    private Gate selectGate(Random rand) {
        if (rand.nextInt(2) == 0) { return mWestGate; }
        return mEastGate;
    }
}
