package roadgraph;

import geography.GeographicPoint;
import java.util.*;


/**
 * @author Basant Soni
 *
 * A class which represents a node in the map
 * This node is an intersection of two streets (edges)
 *
 * */
public class MapNode implements Comparable<MapNode> {

    GeographicPoint location;
    Map<MapNode, MapEdge> neighbours;
    Double actual;      // distance from start node to this node

    Double predicted;   // predicted distance (start node to goal node through this node)
                        // predicted distance = start node to this node + this node to goal node(straight line)
                        // MapNode objects will be compared based on this distance

    /**
     * Create a new MapNode
     */
    public MapNode(GeographicPoint location) {
        this.location = location;
        this.neighbours = new HashMap<>();
    }


    /**
     * @return the location of a MapNode object
     */
    public GeographicPoint getLocation() { return location; }


    /**
     * @return neighbours of a MapNode object
     */
    public Map<MapNode, MapEdge> getNeighbours() { return neighbours; }


    /**
     * Method to add neighbours to a MapNode object
     * @param neighbour the MapNode object to be added to this MapNode object
     * @param edge the corresponding edge that joins this and neighbour MapNode objects
     *
     * */
    public void addNeighbour(MapNode neighbour, MapEdge edge) { neighbours.put(neighbour, edge); }

    /** Getter and Setters for actual or predicted distance */

    public Double getActual() { return actual; }

    public void setActual(Double dist) { this.actual = dist; }

    public Double getPredicted() { return predicted; }

    public void setPredicted(Double predicted) { this.predicted = predicted; }

    /** Compare two MapNode objects based on their predicted distance parameter */
    public int compareTo (MapNode other) { return this.predicted.compareTo(other.predicted); }

    @Override
    public java.lang.String toString() {
        return "(" + this.getLocation().getX() + ", " + this.getLocation().getY() + ")";
    }
}
