package practice.myGraph;

import java.util.List;

public abstract class Graph {

    private int numVertices;
    private int numEdges;

    public Graph() {
        this.numVertices = 0;
        this.numEdges = 0;
    }

    public int getNumVertices() { return numVertices; }

    public void addVertex () {
        implementAddVertex();
        numVertices++;
    }

    public abstract void implementAddVertex();

    public int getNumEdges() { return numEdges; }

    public void addEdges(int v, int w) {
        implementAddEdges(v, w);
        numEdges++;
    }

    public abstract void implementAddEdges(int v, int w);

    public abstract List<Integer> getNeighbours(int v);
}
