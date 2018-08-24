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
public class MapNode {

    GeographicPoint location;
    Map<MapNode, MapEdge> neighbours;

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

}
