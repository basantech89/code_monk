package mooc.vandy.java4android.gate.logic;


/**
 * This file defines the Gate class.
 */
public class Gate {
    // Fill in your code here
    public static final int IN = 1;
    public static final int OUT = -1;
    public static final int CLOSED = 0;
    private int mSwing = CLOSED;

    public int getSwingDirection () { return mSwing; }

    public boolean setSwing (int mSwing) {
        if (mSwing == CLOSED || mSwing == IN || mSwing == OUT) {
            this.mSwing = mSwing;
            return true;
        }
        else // Else if direction is invalid
            return false;
    }

    public boolean open (int mSwing) {
        if (mSwing == IN || mSwing == OUT)
            return this.setSwing(mSwing);
        else // If gates are closed or invalid value
            return false;
    }

    public int thru (int count) {
        if (this.mSwing == IN) { return count; }
        else if (this.mSwing == OUT) {
            count = -count;
            return count;
        }
        else { return 0; }
    }

    public void close () { this.mSwing = CLOSED; } // Closing the Gates

    public String toString () {
        if (this.mSwing == CLOSED) { return "This gate is closed"; }
        else if (this.mSwing == IN) { return "This gate is open and swings to enter the pen only"; }
        else if (this.mSwing == OUT) {return "This gate is open and swings to exit the pen only"; }
        else {return "This gate has an invalid swing direction"; }
    }
}
