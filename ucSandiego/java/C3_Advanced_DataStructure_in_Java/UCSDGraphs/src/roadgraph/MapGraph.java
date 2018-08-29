/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 3
	private int numVertices;
	private int numEdges;
	// Key of hashmap is a geographic point
	// value of hashmap is the intersection point(a map node object)
	private Map<GeographicPoint, MapNode> graph;

	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 3
		numVertices = 0;
		numEdges = 0;
		graph = new HashMap<>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 3
		return numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 3
		return new HashSet<>(graph.keySet()); // never expose your private data
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 3
		return numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 3
		if (location == null || graph.containsKey(location)) return false;

		MapNode node = new MapNode(location);
		graph.put(location, node);
		++numVertices;
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 3
		if (!graph.containsKey(from) || !graph.containsKey(to) || length <= 0.0 ||
				roadName == null || roadType == null)
			throw new IllegalArgumentException("Pl check arguments");

		MapEdge street = new MapEdge(roadName, roadType, length);

		// adding the neighbour MapNode which correspond to "to location"
		// to the MapNode which correspond to from location
		graph.get(from).addNeighbour(graph.get(to), street);
		++numEdges;
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		
		// Hook for visualization. See writeup.
		//nodeSearched.accept(next.getLocation());

		if (start == null || goal == null) {
			System.out.println("Start or Goal is null! No path exists.");
			return new LinkedList<>();
		}

		MapNode startNode = graph.get(start);
		MapNode goalNode = graph.get(goal);

		Map<GeographicPoint, GeographicPoint> parent = new HashMap<>();
		boolean found = BFSearch(startNode, goalNode, parent, nodeSearched);

		if (!found) {
			System.out.println("No path exists");
			return null;
		}

		// reconstruct the path
		return constructPath(start, goal, parent);

	}

	/** Helper method for bfs to find the path from startNode to goalNode
	 * @param startNode start map node object
	 * @param goalNode goal map node object
	 * @param parent a map that links a map node to it's neighbour
	 * @param nodeSearched a hook for visualization
	 * @return True if found the goal node or false otherwise
	 * */
	private boolean BFSearch(MapNode startNode, MapNode goalNode, Map<GeographicPoint, GeographicPoint> parent,
							 Consumer<GeographicPoint> nodeSearched) {

		Queue<MapNode> toExplore = new LinkedList<>();
		HashSet<MapNode> visited = new HashSet<>();
		toExplore.add(startNode);
		visited.add(startNode);

		while (!toExplore.isEmpty()) {

			MapNode currNode = toExplore.remove();
			nodeSearched.accept(currNode.getLocation());

			if (currNode == goalNode) return true;

				for (MapNode child : currNode.getNeighbours().keySet())

					if (!visited.contains(child)) {
						visited.add(child);
						parent.put(child.getLocation(), currNode.getLocation());
						toExplore.add(child);
					}

			}

		return false; // goal isn't found in the graph
	}

	/** Helper method for bfs to construct the path from goal node to start node
	 * @param start The starting location
	 * @param goal The goal location
	 * @param parent a map that links a map node object to it's neighbour map node object
	 * @return list of the path that bfs took to reach to goal
	 * */

	private List<GeographicPoint> constructPath(GeographicPoint start, GeographicPoint goal,
												Map<GeographicPoint, GeographicPoint> parent) {
		GeographicPoint p = goal;
		List<GeographicPoint> path = new LinkedList<>();
		path.add(p);

		while (p.getX() != start.getX() || p.getY() != start.getY()) {
			p = parent.get(p);
			path.add(0, p);
		}

		return path;
	}

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4

		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());

		if (start == null || goal == null) {
			System.out.println("Start or Goal is null! No path exists.");
			return new LinkedList<>();
		}

		MapNode startNode = graph.get(start);
		MapNode goalNode = graph.get(goal);

		Map<GeographicPoint, GeographicPoint> parent = new HashMap<>();
		boolean found = dijkstraSearch(startNode, goalNode, parent, nodeSearched);

		if (!found) {
			System.out.println("No path exists");
			return null;
		}

		// reconstruct the path
		return constructPath(start, goal, parent);

	}

	private boolean dijkstraSearch(MapNode startNode, MapNode goalNode, Map<GeographicPoint, GeographicPoint> parent,
								   Consumer<GeographicPoint> nodeSearched) {
		// Initialization
		PriorityQueue<MapNode> queue = new PriorityQueue();
		Set<MapNode> visited = new HashSet<>();
		int count = 0;

		// Initializing all vertices to infinity
		for (MapNode node : graph.values())
			node.setPredicted(Double.POSITIVE_INFINITY);

		queue.add(startNode);
		startNode.setPredicted(0.0);

		while (!queue.isEmpty()) {

			// retrieve and remove head of the priority queue
			MapNode curr = queue.poll();
			//System.out.println("Visiting dijkstra " + curr);
			++count;

			// hook for visualization
			nodeSearched.accept(curr.getLocation());

			if (!visited.contains(curr)) visited.add(curr);
			if (curr == goalNode) {
				//System.out.println("dijkstra " + visited.size());
				//System.out.println("dijkstra Removed Items " + count);
				return true;
			}

			for (MapNode child : curr.getNeighbours().keySet())
				if (!visited.contains(child)) {

					// length of the street which correspond to child MapNode
					MapEdge street = curr.getNeighbours().get(child);
					// distance of this node from start node
					double nodeDist = street.getLength() + curr.getPredicted();

					// if this distance is shorter from it's already set distance
					if (nodeDist < child.getPredicted()) {
						child.setPredicted(nodeDist);
						queue.add(child);
						parent.put(child.getLocation(), curr.getLocation());
					}

				}
		}

		return false;
	}


	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 4
		
		// Hook for visualization.  See writeup.
		//nodeSearched.accept(next.getLocation());

		if (start == null || goal == null) {
			System.out.println("Start or Goal is null! No path exists.");
			return new LinkedList<>();
		}

		MapNode startNode = graph.get(start);
		MapNode goalNode = graph.get(goal);

		Map<GeographicPoint, GeographicPoint> parent = new HashMap<>();
		boolean found = aStarSearch(startNode, goalNode, parent, nodeSearched);

		if (!found) {
			System.out.println("No path exists");
			return null;
		}

		// reconstruct the path
		return constructPath(start, goal, parent);

	}

	private boolean aStarSearch (MapNode startNode, MapNode goalNode, Map<GeographicPoint, GeographicPoint> parent,
								 Consumer<GeographicPoint> nodeSearched) {
		// Initialization
		PriorityQueue<MapNode> queue = new PriorityQueue();
		Set<MapNode> visited = new HashSet<>();
		int count = 0; // counter for removed nodes from the queue

		// Initializing all vertices to infinity
		for (MapNode node : graph.values()) {
			node.setActual(Double.POSITIVE_INFINITY);
			node.setPredicted(Double.POSITIVE_INFINITY);
		}

		queue.add(startNode);
		startNode.setActual(0.0);
		startNode.setPredicted(0.0);

		while (!queue.isEmpty()) {
			// retrieve and remove head of the priority queue
			MapNode curr = queue.remove();
			//System.out.println("Visiting aStar " + curr);
			//System.out.println("Actual " + curr.getActual() +
			//		" Predicted " + curr.getPredicted());
			++count;
			// hook for visualization
			nodeSearched.accept(curr.getLocation());

			if (!visited.contains(curr)) visited.add(curr);

			if (curr == goalNode) {
				//System.out.println("aStar " + visited.size());
				//System.out.println("aStar Removed Items " + count);
				return true;
			}

			for (MapNode child : curr.getNeighbours().keySet())
				if (!visited.contains(child)) {

					// length of the street which correspond to child MapNode
					MapEdge street = curr.getNeighbours().get(child);
					// distance form start node to this node
					double nodeDist = street.getLength() + curr.getActual();
					// predicted distance from start to goal through this node
					// start to goal distance = start to this node + this node to goal(heuristic)
					// heuristic is a straight line in this case
					double predicted = nodeDist + child.getLocation().distance(goalNode.getLocation());


					// if this distance is shorter from it's already set distance
					if (predicted < child.getPredicted()) {
						child.setActual(nodeDist);
						child.setPredicted(predicted);
						queue.add(child);
						parent.put(child.getLocation(), curr.getLocation());
					}

				}
		}

		return false; // no path exists

	}

	/**
	 * Method to print the graph object
	 * */
	@Override
	public String toString () {
		StringBuilder toReturn = new StringBuilder();

		for (GeographicPoint point : graph.keySet()) {
			toReturn.append("( " + point.getX() + ", " + point.getY() + " ) --> ");
			MapNode node = graph.get(point);
			for (MapNode n : node.getNeighbours().keySet()) {
				GeographicPoint nLoc = n.getLocation();
				toReturn.append("( " + nLoc.getX() + ", " + nLoc.getY() + " ) ");
				toReturn.append(node.getNeighbours().get(n).getLength() + ", " +
						n.getLocation().distance(new GeographicPoint(0, 4)));
				toReturn.append("\n");
			}
			//toReturn.append("\n");
		}

		return toReturn.toString();
	}

	
	
	public static void main(String[] args)
	{
		/*System.out.print("Making a new map...");
		MapGraph firstMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", firstMap);
		System.out.println("DONE.");
		//GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		//GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
		//List<GeographicPoint> testroute = firstMap.bfs(testStart, testEnd);
		//System.out.println(testroute);

		// You can use this method for testing.  
		
		
		*//* Here are some test cases you should try before you attempt
		 * the Week 3 End of Week Quiz, EVEN IF you score 100% on the 
		 * programming assignment.
		 *//*
		MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
		
		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);

		MapGraph map3 = new MapGraph();
		GraphLoader.loadRoadMap("data/graders/mod3/map3.txt", map3);
		//System.out.println(map3);
		GeographicPoint testStart2 = new GeographicPoint(0, 0);
		GeographicPoint testEnd2 = new GeographicPoint(0, 4);
		List<GeographicPoint> testroute0 = map3.aStarSearch(testStart2,testEnd2);
		List<GeographicPoint> testroute1 = map3.dijkstra(testStart2,testEnd2);
		*//*for (GeographicPoint point : testroute1)
			System.out.print("( " + point.getX() + ", " + point.getY() + " ) ");
		System.out.println("\nyoyoy" + testroute1.size());*//*


		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);
		*//*for (GeographicPoint point : testroute2)
			System.out.print("( " + point.getX() + ", " + point.getY() + " ) ");
		System.out.println("\n" + testroute2.size());*//*


		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
		
		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		*//*for (GeographicPoint point : testroute)
			System.out.print("( " + point.getX() + ", " + point.getY() + " ) ");
		System.out.println("\n" + testroute.size());*//*


		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);
		*//*for (GeographicPoint point : testroute)
			System.out.print("( " + point.getX() + ", " + point.getY() + " ) ");
		System.out.println("\n" + testroute.size());*//*
*/

		/* Use this code in Week 3 End of Week Quiz */
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);


		// Week 4 End of week quiz
		/*MapGraph simpleTestMap = new MapGraph();
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);

		GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
		GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);

		System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
		List<GeographicPoint> testroute = simpleTestMap.dijkstra(testStart,testEnd);
		List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart,testEnd);


		MapGraph testMap = new MapGraph();
		GraphLoader.loadRoadMap("data/maps/utc.map", testMap);

		// A very simple test using real data
		testStart = new GeographicPoint(32.869423, -117.220917);
		testEnd = new GeographicPoint(32.869255, -117.216927);
		System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);


		// A slightly more complex test using real data
		testStart = new GeographicPoint(32.8674388, -117.2190213);
		testEnd = new GeographicPoint(32.8697828, -117.2244506);
		System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
		testroute = testMap.dijkstra(testStart,testEnd);
		testroute2 = testMap.aStarSearch(testStart,testEnd);*/
		
	}
	
}
