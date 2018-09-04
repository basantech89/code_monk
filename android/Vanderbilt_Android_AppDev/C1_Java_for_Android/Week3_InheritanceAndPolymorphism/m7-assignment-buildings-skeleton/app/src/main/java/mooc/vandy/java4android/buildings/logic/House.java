package mooc.vandy.java4android.buildings.logic;

import java.util.Objects;

/**
 * This is the House class file that extends Building.
 */
public class House 
       extends Building {

    // TODO - Put your code here.

    private String mOwner;
    private boolean mPool;

    House(int length, int width, int lotLength, int lotWidth) {
        super(length, width, lotLength, lotWidth);
    }

    House(int length, int width, int lotLength, int lotWidth, String owner) {
        super(length, width, lotLength, lotWidth);
        this.mOwner = owner;
    }

    House(int length, int width, int lotLength, int lotWidth, String owner, boolean pool) {
        this(length, width, lotLength, lotWidth, owner);
        this.mPool = pool;
    }

    public String getOwner() { return mOwner; }

    public void setOwner(String owner) { mOwner = owner; }

    public void setPool(boolean pool) { mPool = pool; }

    public boolean hasPool() { return mPool; }

    public String toString() {
        String status = "Owner: " + getOwner();
        if (this.hasPool()) status += "; has a pool";
        if (calcLotArea() > calcBuildingArea()) status += "; has a big open space";
        return status;
    }

    public boolean equals(Object other) {
        if (other instanceof House) {
            House otherHouse = (House)other;
            return this.calcBuildingArea() == otherHouse.calcBuildingArea() &&
                    this.hasPool() == otherHouse.hasPool();
        }
        return false;
    }
}
