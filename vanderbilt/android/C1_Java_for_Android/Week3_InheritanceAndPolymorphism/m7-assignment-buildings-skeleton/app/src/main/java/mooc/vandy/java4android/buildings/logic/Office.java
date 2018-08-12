package mooc.vandy.java4android.buildings.logic;

/**
 * This is the office class file, it is a subclass of Building.
 */
public class Office 
       extends Building {
       
    // TODO - Put your code here.

    private String mBusinessName;
    private int mParkingSpaces;
    private static int sTotalOffices = 0;

    public Office(int length, int width, int lotLength, int lotWidth) {
        super(length, width, lotLength, lotWidth);
        sTotalOffices += 1;
    }

    public Office(int length, int width, int lotLength, int lotWidth, String businessName) {
        super(length, width, lotLength, lotWidth);
        mBusinessName = businessName;
        sTotalOffices += 1;
    }

    public Office(int length, int width, int lotLength, int lotWidth, String businessName,
                  int parkingSpaces) {
        this(length, width, lotLength, lotWidth, businessName);
        mParkingSpaces = parkingSpaces;
    }

    public String getBusinessName() { return mBusinessName; }

    public int getParkingSpaces() { return mParkingSpaces; }

    public void setBusinessName(String businessName) { mBusinessName = businessName; }

    public void setParkingSpaces(int parkingSpaces) { mParkingSpaces = parkingSpaces; }

    public String toString() {
        String status = "Business: ";
        if (mBusinessName == null) status = status.concat("unoccupied");
        else status = status.concat(mBusinessName);
        if (mParkingSpaces > 0) status = status.concat("; has " + mParkingSpaces + " parking Spacess");
        if (sTotalOffices > 0) status = status.concat(" (total offices: " + sTotalOffices + ")");
        return status;
    }

    public boolean equals(Object other) {
        if (other instanceof Office) {
            Office otherOffice = (Office)other;
            return this.calcBuildingArea() == otherOffice.calcBuildingArea() &&
                    this.getParkingSpaces() == otherOffice.getParkingSpaces();
        }
        return false;
    }
}
