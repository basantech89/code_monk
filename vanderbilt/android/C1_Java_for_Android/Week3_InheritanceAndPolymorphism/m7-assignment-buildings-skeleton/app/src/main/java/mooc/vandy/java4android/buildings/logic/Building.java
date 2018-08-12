package mooc.vandy.java4android.buildings.logic;

/**
 * This is the Building class file.
 */
public class Building {
    
    // TODO - Put your code here.

    private int mLength, mWidth, mLotLength, mLotWidth;

    public Building(int length, int width, int lotLength, int lotWidth) {
        mLength = length;
        mWidth = width;
        mLotLength = lotLength;
        mLotWidth = lotWidth;
    }

    public int getLength() { return mLength; }

    public int getWidth() { return mWidth; }

    public int getLotLength() { return mLotLength; }

    public int getLotWidth() { return mLotWidth; }

    public void setLength(int length) { this.mLength = length; }

    public void setWidth(int width) { this.mWidth = width; }

    public void setLotLength(int lotLength) { this.mLotLength = lotLength; }

    public void setLotWidth(int lotWidth) { this.mLotWidth = lotWidth; }

    public int calcBuildingArea() { return mLength * mWidth; }

    public int calcLotArea() { return mLotLength * mLotWidth; }

    @Override
    public String toString() {
        return "Building: " +
                "Length is=" + mLength +
                ", Width is=" + mWidth +
                ", LotLength is=" + mLotLength +
                ", LotWidth is=" + mLotWidth +
                ", Area of Building is=" + calcBuildingArea() +
                ", Area of Lot is=" + calcLotArea();
    }
}
