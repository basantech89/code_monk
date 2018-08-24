package practice.myMaze;

import java.util.LinkedList;
import java.util.List;

public class MazeNode<E> {

    private int row;
    private int col;
    private E data;
    private List<MazeNode<E>> neighbours;

    public MazeNode(int row, int col, E data) {
        this.row = row;
        this.col = col;
        this.data = data;
        this.neighbours = new LinkedList<>();
    }

    public int getRow() { return row; }

    public void setRow(int row) { this.row = row; }

    public int getCol() { return col; }

    public void setCol(int col) { this.col = col; }

    public E getData() { return data; }

    public void setData(E data) { this.data = data; }

    public List<MazeNode<E>> getNeighbours() { return neighbours; }

    public void addNeighbour(MazeNode<E> neighbour) { this.neighbours.add(neighbour); }
}
