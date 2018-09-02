package practice.myGraph;

import java.util.ArrayList;
import java.util.List;

/** A class that implements a directed graph.
 * The graph may have self-loops, parallel edges.
 * Vertices are labeled by integers 0 .. n-1
 * and may also have String labels.
 * The edges of the graph are not labeled.
 * Representation of edges via an adjacency matrix.
 *
 * @author code_monk
 *
 */

public class GraphAdjMatrix extends Graph {

    private int[][] adjMatrix;
    private final int defaultNumVertices = 5;

    /** create a new empty graph*/
    public GraphAdjMatrix() { this.adjMatrix = new int[defaultNumVertices][defaultNumVertices]; }


    /**
     * Implement the abstract method for adding an edge.
     * Allows for multiple edges between two points:
     * the entry at row v, column w stores the number of such edges.
     * @param v the index of the start point for the edge.
     * @param w the index of the end point for the edge.
     */
    @Override
    public void implementAddEdges(int v, int w) { adjMatrix[v][w] += 1; }


    /**
     * Implement the abstract method for adding a vertex.
     * If need to increase dimensions of matrix, double them
     * to amortize cost.
     */
    @Override
    public void implementAddVertex() {
        int v = getNumVertices();

        if (v >= adjMatrix.length) {

            int[][] newAdjMatrix = new int[v*2][v*2];

            for (int i = 0; i < adjMatrix.length; i++)
                for (int j = 0; j < adjMatrix.length; j++)
                    newAdjMatrix[i][j] = adjMatrix[i][j];

            adjMatrix = newAdjMatrix;
        }
    }


    /**
     * Implement the abstract method for finding all
     * out-neighbors of a vertex.
     * If there are multiple edges between the vertex
     * and one of its out-neighbors, this neighbor
     * appears once in the list for each of these edges.
     *
     * @param v The index of vertex.
     * @return List<Integer> a list of indices of vertices
     */
    @Override
    public List<Integer> getNeighbours(int v) {
        List<Integer> neighbours = new ArrayList<>();

        for (int i = 0; i < getNumVertices(); i++)
            for (int j = 0; j < adjMatrix[v][i]; j++)
            // looking at columns (end points of edge) for neighbours
            // if the end point is > 0, means a neighbour is found
                neighbours.add(i);

        return neighbours;
    }

    /**
     * Implement the abstract method for finding all
     * in-neighbors of a vertex.
     * If there are multiple edges from another vertex
     * to this one, the neighbor
     * appears once in the list for each of these edges.
     *
     * @param v the index of vertex.
     * @return List<Integer> a list of indices of vertices.
     */

    public List<Integer> getInNeighbour(int v) {
        List<Integer> inNeighbours = new ArrayList<>();

        for (int i = 0; i < getNumVertices(); i++)
            for (int j = 0; j < adjMatrix[i][v]; j++)
                inNeighbours.add(i);

        return inNeighbours;
    }
}
