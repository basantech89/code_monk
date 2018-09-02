package roadgraph;


/**
 * @author Basant Soni
 *
 * A class which represents a street(edge) node in the map
 * This edge joins two MapNode objects in the map
 *
 * */
public class MapEdge {

    private String roadName;
    private String roadType;
    private double length;

    /** Create a new MapEdge
     * */
    public MapEdge(String roadName, String roadType, double length) {
        this.roadName = roadName;
        this.roadType = roadType;
        this.length = length;
    }

    /** Getters */

    public String getRoadName() {
        return roadName;
    }

    public String getRoadType() {
        return roadType;
    }

    public double getLength() {
        return length;
    }

}
