package practice.myGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GraphAdjList extends Graph {

    private Map<Integer, ArrayList<Integer>> adjListMap;

    @Override
    public void implementAddVertex() {
        int v = getNumVertices();
        ArrayList<Integer> neighbours = new ArrayList<>();
        adjListMap.put(v, neighbours);
    }

    @Override
    public void implementAddEdges(int v, int w) { adjListMap.get(v).add(w); }

    @Override
    public List<Integer> getNeighbours(int v) { return new ArrayList<>(adjListMap.get(v)); }

    public List<Integer> getInNeighbours(int v) {
        List<Integer> inNeighbours = new ArrayList<>();
        for (Integer u : adjListMap.keySet())
            //iterate through all edges in u's adjacency list and
            //add u to the inNeighbor list of v whenever an edge
            //with startpoint u has endpoint v.
            for (int w : adjListMap.get(u))
            if (v == w)
                inNeighbours.add(u);

        return inNeighbours;
    }
}
