package mooc.vandy.java4android.gate.logic;

import java.util.Random;

import mooc.vandy.java4android.gate.ui.OutputInterface;

/**
 * This class uses your Gate class to fill the corral with snails.  We
 * have supplied you will the code necessary to execute as an app.
 * You must fill in the missing logic below.
 */
public class FillTheCorral {
    /**
     * Reference to the OutputInterface.
     */
    private OutputInterface mOut;

    /**
     * Constructor initializes the field.
     */
    FillTheCorral(OutputInterface out) {
        mOut = out;
    }

    // DONE -- Fill your code in here

    public void setCorralGates(Gate[] Gates, Random selectDirection) {
        for (Gate gate : Gates) { gate.setSwing(selectDirection.nextInt(3) - 1); }
    }

    // This method will return true if any of the gate's direction is IN, else return false
    public boolean anyCorralAvailable(Gate[] corral) {
        for (Gate gate : corral) {
            if (gate.getSwingDirection() == gate.IN) return true;
        }
        return false; // All the gates are closed or OUT, so return false
    }

    public int corralSnails(Gate[] corral, Random rand) {
        int pasture = 5;
        int attempts = 0;
        gateStatus(corral);
        while (pasture > 0) {
            int gateNo = rand.nextInt(corral.length);   // Randomly selecting a gate number
            int corralled = rand.nextInt(pasture + 1);  // No of corralled snails
            pasture -= corral[gateNo].thru(corralled);
            mOut.println(corralled + " are trying to move through corral " + gateNo);
            attempts += 1;
        }
        mOut.println("It took " + attempts + " attempts to corral all of the snails");
        return attempts;
    }

    // Helper method to print the Initial Gate Setup
    private void gateStatus(Gate[] corral) {
        mOut.println("Initial gate setup");
        for (int i = 0; i < corral.length; i++)
            mOut.println("Gate " + i + ":" + corral[i].toString());
    }
}
