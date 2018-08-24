package practice.myMaze;

import java.util.*;

public class Maze<E extends Comparable<? super E>> {

    private MazeNode<E>[][] cells;
    private final int DEFAULT_SIZE = 5;

    public Maze() {
        this.cells = new MazeNode[DEFAULT_SIZE][DEFAULT_SIZE];
    }

    public MazeNode<E> addNode(int row, int col, E data) {
        cells[row][col] = new MazeNode<>(row, col, data);
        return cells[row][col];
    }


    /**
     * Depth First Search for the Maze
     * @param startRow
     * @param startCol
     * @param endRow
     * @param endCol
     * @return The path from start node to end node
     *
     * */
    public List<E> dfs(int startRow, int startCol, int endRow, int endCol) {
        // initialize the variables
        Stack<MazeNode<E>> stack = new Stack<>(); // nodes to explore
        HashSet<E> visited = new HashSet<>(); // avoid visited nodes
        Map<E, E> parent = new HashMap<>(); // keep track of parent nodes

        // push start onto the stack and add to visited
        MazeNode<E> start = cells[startRow][startCol];
        stack.push(start);
        visited.add(start.getData());

        // while stack is not empty
        while (!stack.isEmpty()) {
            // pop node curr from top of stack
            MazeNode<E> curr = stack.pop();

            // if curr == end, return parent map
            if (curr.getRow() == endRow && curr.getCol() == endCol)
                return parentMap(curr.getData(), parent);

            // for each of curr's neighbour n
            for (MazeNode<E> neighbour : curr.getNeighbours()) {
                E n = neighbour.getData();

                // if n is not visited
                if (!visited.contains(n)) {
                    // visit it
                    visited.add(n);
                    // add curr as neighbour's parent in parent map
                    parent.put(n, curr.getData());
                    // push the neighbour onto the stack
                    stack.push(neighbour);
                }
            }

        }

        return null;
    }

    private List<E> parentMap(E data, Map<E, E> parent) {
        List<E> path = new LinkedList<>();
        E p = parent.get(data);


        while (p != null) {
            path.add(0, p);
            p = parent.get(p);
        }

        return path;
    }

    public List<E> dfsRecursive (int startRow, int startCol, int endRow, int endCol) {

        HashSet<E> visited = new HashSet<>();
        Map<E, E> parent = new HashMap<>();
        List<E> path = new LinkedList<>();
        path.add(0, dfsrecursive(startRow, startCol, endRow, endCol, visited, parent));
        return path;
    }

    public E dfsrecursive (int startRow, int startCol, int endRow, int endCol,
                                 HashSet<E> visited, Map<E, E> parents) {

        MazeNode<E> node = cells[startRow][startCol];

        if (startRow == endRow && startCol == endCol)
            return node.getData();

        for (MazeNode<E> n : node.getNeighbours()) {
            E data = n.getData();

            if (!visited.contains(data)) {
                visited.add(data);
                parents.put(node.getData(), data);
                return dfsrecursive(n.getRow(), n.getCol(), endRow, endCol, visited, parents);
            }

        }

        return null;
    }

    public List<E> bfs (int startRow, int startCol, int endRow, int endCol) {
        // initialize the variables
        Queue<MazeNode<E>> queue = new LinkedList<>(); // nodes to explore
        HashSet<E> visited = new HashSet<>(); // avoid visited nodes
        Map<E, E> parent = new HashMap<>(); // keep track of parent nodes

        // add start into the queue and add to visited
        MazeNode<E> start = cells[startRow][startCol];
        queue.add(start);
        visited.add(start.getData());

        // while queue is not empty
        while (!queue.isEmpty()) {
            // remove node curr from front of queue
            MazeNode<E> curr = queue.remove();

            // if curr == end, return parent map
            if (curr.getRow() == endRow && curr.getCol() == endCol)
                return parentMap(curr.getData(), parent);

            // for each of curr's neighbour n
            for (MazeNode<E> neighbour : curr.getNeighbours()) {
                E n = neighbour.getData();

                // if n is not visited
                if (!visited.contains(n)) {
                    // visit it
                    visited.add(n);
                    // add curr as neighbour's parent in parent map
                    parent.put(n, curr.getData());
                    // add the neighbour into the queue
                    queue.add(neighbour);
                }
            }

        }

        return null;
    }

    public static void main(String[] args) {

        Maze<Character> maze = new Maze<>();
        MazeNode<Character> nodeA =  maze.addNode(0, 0, 'A');
        MazeNode<Character> nodeB =  maze.addNode(0, 1, 'B');
        MazeNode<Character> nodeF =  maze.addNode(1, 1, 'F');
        MazeNode<Character> nodeC =  maze.addNode(0, 2, 'C');
        MazeNode<Character> nodeD =  maze.addNode(0, 3, 'D');
        MazeNode<Character> nodeH =  maze.addNode(1, 3, 'H');
        MazeNode<Character> nodeI =  maze.addNode(2, 3, 'I');
        MazeNode<Character> nodeS =  maze.addNode(3, 3, 'S');
        MazeNode<Character> nodeL =  maze.addNode(3, 2, 'L');
        MazeNode<Character> nodeK =  maze.addNode(3, 1, 'K');
        MazeNode<Character> nodeJ =  maze.addNode(3, 0, 'J');
        MazeNode<Character> nodeG =  maze.addNode(2, 0, 'G');
        MazeNode<Character> nodeM =  maze.addNode(1, 0, 'M');
        nodeA.addNeighbour(nodeB);
        nodeB.addNeighbour(nodeA);
        nodeB.addNeighbour(nodeC);
        nodeC.addNeighbour(nodeB);
        nodeB.addNeighbour(nodeF);
        nodeF.addNeighbour(nodeB);
        nodeC.addNeighbour(nodeD);
        nodeD.addNeighbour(nodeC);
        nodeD.addNeighbour(nodeH);
        nodeH.addNeighbour(nodeD);
        nodeH.addNeighbour(nodeI);
        nodeI.addNeighbour(nodeH);
        nodeI.addNeighbour(nodeS);
        nodeS.addNeighbour(nodeI);
        nodeS.addNeighbour(nodeL);
        nodeL.addNeighbour(nodeS);
        nodeL.addNeighbour(nodeK);
        nodeK.addNeighbour(nodeL);
        nodeK.addNeighbour(nodeJ);
        nodeJ.addNeighbour(nodeK);
        nodeJ.addNeighbour(nodeG);
        nodeG.addNeighbour(nodeJ);
        nodeG.addNeighbour(nodeM);
        nodeM.addNeighbour(nodeG);
        nodeM.addNeighbour(nodeA);
        nodeM.addNeighbour(nodeF);
        nodeA.addNeighbour(nodeM);
        nodeF.addNeighbour(nodeM);

        List<Character> path1 = maze.dfs(3, 3, 2, 0);
        System.out.println(path1);
        List<Character> path3 = maze.dfs(3, 3, 0, 3);
        System.out.println(path3);
        List<Character> path4 = maze.bfs(3, 3, 0, 3);
        System.out.println(path4);
        List<Character> path2 = maze.dfsRecursive(3, 3, 2, 0);
        System.out.println(path2);
    }


}
