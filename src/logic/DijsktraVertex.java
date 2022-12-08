package logic;

import cu.edu.cujae.ceis.graph.vertex.Vertex;

public class DijsktraVertex{
    private Vertex vertex;
    private int distance;
    private boolean visited;

    public DijsktraVertex(Vertex vertex, int distance, boolean visited) {
        setVertex(vertex);
        setDistance(distance);
        setVisited(visited);
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}

