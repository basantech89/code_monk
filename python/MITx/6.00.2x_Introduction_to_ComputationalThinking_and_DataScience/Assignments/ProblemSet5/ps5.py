# 6.00.2x Problem Set 5
# Graph optimization
# Finding shortest paths through MIT buildings
#

import string
# This imports everything from `graph.py` as if it was defined in this file!
from graph import *

#
# Problem 2: Building up the Campus Map
#
# Before you write any code, write a couple of sentences here
# describing how you will model this problem as a graph.

# This is a helpful exercise to help you organize your
# thoughts before you tackle a big design problem!
#
#Buildings of MIT will be represented by Nodes and the edges will represent
#paths through buildings.Two paths could be there, traversed and outdoor for
#which distance would be given.

def load_map(mapFilename):
    """
    Parses the map file and constructs a directed graph

    Parameters:
        mapFilename : name of the map file

    Assumes:
        Each entry in the map file consists of the following four positive
        integers, separated by a blank space:
            From To TotalDistance DistanceOutdoors
        e.g.
            32 76 54 23
        This entry would become an edge from 32 to 76.

    Returns:
        a directed graph representing the map
    """
    g = WeightedDigraph()
    fh = open(mapFilename)
    for line in fh:
        line = line.split()
        weightedEdge = WeightedEdge(Node(line[0]), Node(line[1]), \
                                    float(line[2]), float(line[3]))
        for i in xrange(2):
            try:
                g.addNode(Node(line[i]))
            except ValueError:
                pass
        g.addEdge(weightedEdge)
    return g


mitMap = load_map("mit_map.txt")
#print isinstance(mitMap, Digraph)
#print isinstance(mitMap, WeightedDigraph)
#print mitMap.nodes
#print mitMap.edges
g = WeightedDigraph()
for i in range(1, 6):
    g.addNode(Node(i))
g.addEdge(WeightedEdge(Node(1), Node(2), 5.0, 2.0))
g.addEdge(WeightedEdge(Node(3), Node(5), 5.0, 1.0))
g.addEdge(WeightedEdge(Node(2), Node(3), 20.0, 10.0))
g.addEdge(WeightedEdge(Node(2), Node(4), 10.0, 5.0))
g.addEdge(WeightedEdge(Node(4), Node(3), 5.0, 1.0))
g.addEdge(WeightedEdge(Node(4), Node(5), 20.0, 1.0))
print g
#
# Problem 3: Finding the Shortest Path using Brute Force Search
#
# State the optimization problem as a function to minimize
# and what the constraints are
#

def bruteForceSearch(digraph, start, end, maxTotalDist, maxDistOutdoors):
    """
    Finds the shortest path from start to end using brute-force approach.
    The total distance travelled on the path must not exceed maxTotalDist, and
    the distance spent outdoor on this path must not exceed maxDistOutdoors.

    Parameters:
        digraph: instance of class Digraph or its subclass
        start, end: start & end building numbers (strings)
        maxTotalDist : maximum total distance on a path (integer)
        maxDistOutdoors: maximum distance spent outdoors on a path (integer)

    Assumes:
        start and end are numbers for existing buildings in graph

    Returns:
        The shortest-path from start to end, represented by
        a list of building numbers (in strings), [n_1, n_2, ..., n_k],
        where there exists an edge from n_i to n_(i+1) in digraph,
        for all 1 <= i < k.

        If there exists no path that satisfies maxTotalDist and
        maxDistOutdoors constraints, then raises a ValueError.
    """
    start = Node(start)
    end = Node(end)
    paths = helper_fcn(digraph, start, end)
    dists = []
    pathsCopy = paths[:]
    for path in pathsCopy:
        totalDist, distOutdoors = 0, 0
        for i in range(len(path)):
            if i == len(path) - 1:
                break
            Edges = digraph.edges[path[i]]
            for edge in Edges:
                if edge[0] == path[i+1]:
                    totalDist += int(edge[1][0])
                    distOutdoors += int(edge[1][1])
        if totalDist <= maxTotalDist and distOutdoors <= maxDistOutdoors:
            dists.append((totalDist, distOutdoors))
        else:
            paths.remove(path)
    if len(dists) == 0 : raise ValueError
    lst = [dist[0] for dist in dists]
    min1 = min(lst)
    minDistPath = paths[lst.index(min1)]
    return [str(node) for node in minDistPath]



def helper_fcn(digraph, start, end):
    stack = [start]
    lst2, lst3, lst4 = [], [], []
    while len(stack) > 0:
        if stack[0] == end:
            lst2.append(end)
            lst3.append(lst2)
            if len(lst4) != 0:
                delete = lst4.pop()
                lst2 = lst2[:delete]
            else:
                lst2 = [start]
            stack.pop(0)
        else:
            temp = stack.pop(0)
            lst2.append(temp)
            lst = digraph.childrenOf(temp)
            if len(lst) != 1 : lst4.append(len(lst2))
            for i in range(len(lst)):
                stack.insert(0, lst[-(i+1)])
    print lst3
    return lst3


#print bruteForceSearch(g, "1", "5", 35, 8)
# Problem 4: Finding the Shorest Path using Optimized Search Method
#
def directedDFS(digraph, start, end, maxTotalDist, maxDistOutdoors):
    """
    Finds the shortest path from start to end using directed depth-first.
    search approach. The total distance travelled on the path must not
    exceed maxTotalDist, and the distance spent outdoor on this path must
	not exceed maxDistOutdoors.

    Parameters:
        digraph: instance of class Digraph or its subclass
        start, end: start & end building numbers (strings)
        maxTotalDist : maximum total distance on a path (integer)
        maxDistOutdoors: maximum distance spent outdoors on a path (integer)

    Assumes:
        start and end are numbers for existing buildings in graph

    Returns:
        The shortest-path from start to end, represented by
        a list of building numbers (in strings), [n_1, n_2, ..., n_k],
        where there exists an edge from n_i to n_(i+1) in digraph,
        for all 1 <= i < k.

        If there exists no path that satisfies maxTotalDist and
        maxDistOutdoors constraints, then raises a ValueError.
    """
    start, end = Node(start), Node(end)
    path = findDirectDFS_Path(digraph, start, end, maxTotalDist, maxDistOutdoors)
    if len(path) == 0 : raise ValueError
    return path

def findDirectDFS_Path(digraph, start, end, maxTotalDist, maxDistOutdoors, bestPathSoFar=[]):
    totalDist, totalDistSoFar, distOutdoors, distOutdoorsSoFar = 0, 0, 0, 0
    stack = [start]
    lst2, lst4 = [], []
    while len(stack) > 0:
        if stack[0] == end:
            lst2.append(end)
            for node in lst2:
                if node == lst2[-1] : break
                Edges = digraph.edges[node]
                for edge in Edges:
                    if edge[0] == lst2[lst2.index(node) + 1]:
                        totalDist += int(edge[1][0])
                        distOutdoors += int(edge[1][1])
            if totalDist <= maxTotalDist and distOutdoors <= maxDistOutdoors:
                if len(bestPathSoFar) == 0 or (totalDistSoFar > totalDist and distOutdoorsSoFar > distOutdoors):
                    totalDistSoFar, distOutdoorsSoFar = totalDist, distOutdoors
                    path = [str(node) for node in lst2]
                    bestPathSoFar = path
            totalDist, distOutdoors = 0, 0
            if len(lst4) != 0:
                delete = lst4.pop()
                lst2 = lst2[:delete]
            else:
                lst2 = [start]
            stack.pop(0)
        else:
            temp = stack.pop(0)
            lst2.append(temp)
            lst = digraph.childrenOf(temp)
            if len(lst) != 1 : lst4.append(len(lst2))
            for i in range(len(lst)):
                stack.insert(0, lst[-(i+1)])
    return bestPathSoFar


print directedDFS(g, "1", "5", 35, 9)
# Uncomment below when ready to test
#### NOTE! These tests may take a few minutes to run!! ####
#if __name__ == '__main__':
#     Test cases
#     mitMap = load_map("mit_map.txt")
#     print isinstance(mitMap, Digraph)
#     print isinstance(mitMap, WeightedDigraph)
#     print 'nodes', mitMap.nodes
#     print 'edges', mitMap.edges


 #   LARGE_DIST = 1000000

#     Test case 1
#    print "---------------"
#    print "Test case 1:"
#    print "Find the shortest-path from Building 32 to 56"
#    expectedPath1 = ['32', '56']
#    brutePath1 = bruteForceSearch(mitMap, '32', '56', LARGE_DIST, LARGE_DIST)
#     dfsPath1 = directedDFS(mitMap, '32', '56', LARGE_DIST, LARGE_DIST)
#    print "Expected: ", expectedPath1
#    print "Brute-force: ", brutePath1
#     print "DFS: ", dfsPath1
#     print "Correct? BFS: {0}; DFS: {1}".format(expectedPath1 == brutePath1, expectedPath1 == dfsPath1)

#     Test case 2
#     print "---------------"
#     print "Test case 2:"
#     print "Find the shortest-path from Building 32 to 56 without going outdoors"
#     expectedPath2 = ['32', '36', '26', '16', '56']
#     brutePath2 = bruteForceSearch(mitMap, '32', '56', LARGE_DIST, 0)
#     dfsPath2 = directedDFS(mitMap, '32', '56', LARGE_DIST, 0)
#     print "Expected: ", expectedPath2
#     print "Brute-force: ", brutePath2
#     print "DFS: ", dfsPath2
#     print "Correct? BFS: {0}; DFS: {1}".format(expectedPath2 == brutePath2, expectedPath2 == dfsPath2)

#     Test case 3
#     print "---------------"
#     print "Test case 3:"
#     print "Find the shortest-path from Building 2 to 9"
#     expectedPath3 = ['2', '3', '7', '9']
#     brutePath3 = bruteForceSearch(mitMap, '2', '9', LARGE_DIST, LARGE_DIST)
#     dfsPath3 = directedDFS(mitMap, '2', '9', LARGE_DIST, LARGE_DIST)
#     print "Expected: ", expectedPath3
#     print "Brute-force: ", brutePath3
#     print "DFS: ", dfsPath3
#     print "Correct? BFS: {0}; DFS: {1}".format(expectedPath3 == brutePath3, expectedPath3 == dfsPath3)

#     Test case 4
#     print "---------------"
#     print "Test case 4:"
#     print "Find the shortest-path from Building 2 to 9 without going outdoors"
#     expectedPath4 = ['2', '4', '10', '13', '9']
#     brutePath4 = bruteForceSearch(mitMap, '2', '9', LARGE_DIST, 0)
#     dfsPath4 = directedDFS(mitMap, '2', '9', LARGE_DIST, 0)
#     print "Expected: ", expectedPath4
#     print "Brute-force: ", brutePath4
#     print "DFS: ", dfsPath4
#     print "Correct? BFS: {0}; DFS: {1}".format(expectedPath4 == brutePath4, expectedPath4 == dfsPath4)

#     Test case 5
#     print "---------------"
#     print "Test case 5:"
#     print "Find the shortest-path from Building 1 to 32"
#     expectedPath5 = ['1', '4', '12', '32']
#     brutePath5 = bruteForceSearch(mitMap, '1', '32', LARGE_DIST, LARGE_DIST)
#     dfsPath5 = directedDFS(mitMap, '1', '32', LARGE_DIST, LARGE_DIST)
#     print "Expected: ", expectedPath5
#     print "Brute-force: ", brutePath5
#     print "DFS: ", dfsPath5
#     print "Correct? BFS: {0}; DFS: {1}".format(expectedPath5 == brutePath5, expectedPath5 == dfsPath5)

#     Test case 6
#     print "---------------"
#     print "Test case 6:"
#     print "Find the shortest-path from Building 1 to 32 without going outdoors"
#     expectedPath6 = ['1', '3', '10', '4', '12', '24', '34', '36', '32']
#     brutePath6 = bruteForceSearch(mitMap, '1', '32', LARGE_DIST, 0)
#     dfsPath6 = directedDFS(mitMap, '1', '32', LARGE_DIST, 0)
#     print "Expected: ", expectedPath6
#     print "Brute-force: ", brutePath6
#     print "DFS: ", dfsPath6
#     print "Correct? BFS: {0}; DFS: {1}".format(expectedPath6 == brutePath6, expectedPath6 == dfsPath6)

#     Test case 7
#     print "---------------"
#     print "Test case 7:"
#     print "Find the shortest-path from Building 8 to 50 without going outdoors"
#     bruteRaisedErr = 'No'
#     dfsRaisedErr = 'No'
#     try:
#         bruteForceSearch(mitMap, '8', '50', LARGE_DIST, 0)
#     except ValueError:
#         bruteRaisedErr = 'Yes'

#     try:
#         directedDFS(mitMap, '8', '50', LARGE_DIST, 0)
#     except ValueError:
#         dfsRaisedErr = 'Yes'

#     print "Expected: No such path! Should throw a value error."
#     print "Did brute force search raise an error?", bruteRaisedErr
#     print "Did DFS search raise an error?", dfsRaisedErr

#     Test case 8
#     print "---------------"
#     print "Test case 8:"
#     print "Find the shortest-path from Building 10 to 32 without walking"
#     print "more than 100 meters in total"
#     bruteRaisedErr = 'No'
#     dfsRaisedErr = 'No'
#     try:
#         bruteForceSearch(mitMap, '10', '32', 100, LARGE_DIST)
#     except ValueError:
#         bruteRaisedErr = 'Yes'

#     try:
#         directedDFS(mitMap, '10', '32', 100, LARGE_DIST)
#     except ValueError:
#         dfsRaisedErr = 'Yes'

#     print "Expected: No such path! Should throw a value error."
#     print "Did brute force search raise an error?", bruteRaisedErr
#     print "Did DFS search raise an error?", dfsRaisedErr
