package basicgraph;

import util.GraphLoader;

import java.util.*;

/** A class that implements a directed graph. 
 * The graph may have self-loops, parallel edges. 
 * Vertices are labeled by integers 0 .. n-1
 * and may also have String labels.
 * The edges of the graph are not labeled.
 * Representation of edges via an adjacency matrix.
 * 
 * @author UCSD MOOC development team and YOU
 *
 */
public class GraphAdjMatrix extends Graph {

	private final int defaultNumVertices = 5;
	private int[][] adjMatrix;
	
	/** Create a new empty Graph */
	public GraphAdjMatrix () {
		adjMatrix = new int[defaultNumVertices][defaultNumVertices];
	}
	
	/** 
	 * Implement the abstract method for adding a vertex.
	 * If need to increase dimensions of matrix, double them
	 * to amortize cost. 
	 */
	public void implementAddVertex() {
		int v = getNumVertices();
		if (v >= adjMatrix.length) {
			int[][] newAdjMatrix = new int[v*2][v*2];
			for (int i = 0; i < adjMatrix.length; i ++) {
				for (int j = 0; j < adjMatrix.length; j ++) {
					newAdjMatrix[i][j] = adjMatrix[i][j];
				}
			}
			adjMatrix = newAdjMatrix;
		}
	}
	
	/** 
	 * Implement the abstract method for adding an edge.
	 * Allows for multiple edges between two points:
	 * the entry at row v, column w stores the number of such edges.
	 * @param v the index of the start point for the edge.
	 * @param w the index of the end point for the edge.  
	 */	
	public void implementAddEdge(int v, int w) {
		adjMatrix[v][w] += 1;
	}
	
	/** 
	 * Implement the abstract method for finding all 
	 * out-neighbors of a vertex.
	 * If there are multiple edges between the vertex
	 * and one of its out-neighbors, this neighbor
	 * appears once in the list for each of these edges.
	 * 
	 * @param v the index of vertex.
	 * @return List<Integer> a list of indices of vertices.  
	 */	
	public List<Integer> getNeighbors(int v) {
		List<Integer> neighbors = new ArrayList<Integer>();
		for (int i = 0; i < getNumVertices(); i ++) {
			for (int j=0; j< adjMatrix[v][i]; j ++) {
				neighbors.add(i);
			}
		}
		return neighbors;
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
	public List<Integer> getInNeighbors(int v) {
		List<Integer> inNeighbors = new ArrayList<Integer>();
		for (int i = 0; i < getNumVertices(); i ++) {
			for (int j=0; j< adjMatrix[i][v]; j++) {
				inNeighbors.add(i);
			}
		}
		return inNeighbors;
	}
	
	/** 
	 * Implement the abstract method for finding all 
	 * vertices reachable by two hops from v.
	 * Use matrix multiplication to record length 2 paths.
	 * 
	 * @param v the index of vertex.
	 * @return List<Integer> a list of indices of vertices.  
	 */	
	public List<Integer> getDistance2(int v) {
		// TODO Implement this method in week 2
		// square of adjMatrix
		int[][] newAdjMatrix = multiplyMatrix(adjMatrix, adjMatrix);

		// same algorithm of getNeighbour
		List<Integer> hop2Neighbours = new ArrayList<Integer>();
		for (int i = 0; i < getNumVertices(); i ++)
			for (int j=0; j < newAdjMatrix[v][i]; j++)
				hop2Neighbours.add(i);

		return hop2Neighbours;
	}

	/**
	 * Helper method to multiply two matrices
	 * @param matrix1 the matrix to be multiplied
	 * @param matrix2 the matrix to be multiplied
	 * @return the resultant matrix
	 * */
	private int[][] multiplyMatrix (int[][] matrix1, int[][] matrix2) {
		int[][] mulMatrix = new int[matrix1.length][matrix2.length];

		// i refers to rows of matrix 1
		for (int i = 0; i < matrix1.length; i++)
			// col refers to columns of matrix 2
			for (int col = 0; col < matrix2.length; col++)
				// j refers to rows of matrix 2
				for (int  j = 0; j < matrix2.length; j++)
					mulMatrix[i][col] += matrix1[i][j] * matrix2[j][col];

		return mulMatrix;
	}
	
	/**
	 * Generate string representation of adjacency matrix
	 * @return the String
	 */
	public String adjacencyString() {
		int dim = getNumVertices();
		String s = "Adjacency matrix";
		s += " (size " + dim + "x" + dim + " = " + dim* dim + " integers):";
		for (int i = 0; i < dim; i ++) {
			s += "\n\t"+i+": ";
			for (int j = 0; j < dim; j++) {
			s += adjMatrix[i][j] + ", ";
			}
		}
		return s;
	}


	public static void main(String[] args) {
		GraphAdjMatrix graph = new GraphAdjMatrix();
		GraphLoader.loadRoadMap("data/testdata/simpletest2.map", graph);
		//GraphLoader.createIntersectionsFile("data/testdata/simpletest2.map", "data/intersections/simpletest2.intersections");
		System.out.println(graph);
		int[][] mulMatrix =  graph.multiplyMatrix(graph.adjMatrix, graph.adjMatrix);
		for (int i = 0; i < mulMatrix.length; i++) {
			for (int j = 0; j < mulMatrix.length; j++)
				System.out.print(mulMatrix[i][j] + " ");
			System.out.println();
		}

		System.out.println(graph.getDistance2(0));

	}

}
