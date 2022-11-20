package logic;

import cu.edu.cujae.ceis.graph.LinkedGraph;
import cu.edu.cujae.ceis.graph.edge.Edge;
import cu.edu.cujae.ceis.graph.edge.WeightedEdge;
import cu.edu.cujae.ceis.graph.interfaces.ILinkedWeightedEdgeNotDirectedGraph;
import cu.edu.cujae.ceis.graph.vertex.Vertex;

import java.util.*;

public class City {
    private String id;
    private ILinkedWeightedEdgeNotDirectedGraph busStopGraph;

    private LinkedList<Bus> busList;

    public City(String id) {
        setId(id);
        busStopGraph = new LinkedGraph();
        busList = new LinkedList<>();
    }

    public LinkedList<Bus> getBusList() {
        return busList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ILinkedWeightedEdgeNotDirectedGraph getBusStopGraph() {
        return busStopGraph;
    }

    public Deque<BusStop> shortestPath(int pos_bs1, int pos_bs2)throws IllegalArgumentException{
        //routes.removeDisconnectVerticesND();
        if(posInRange(pos_bs1) && posInRange(pos_bs2)) {
            Deque<BusStop> result = new ArrayDeque<>();
            LinkedList<Vertex> vertexList = busStopGraph.getVerticesList();
            Map<Vertex, Vertex> dijsktra_path = pathDijsktraAlgorithm(pos_bs1, pos_bs2);
            Iterator<Vertex> iter = vertexList.iterator();
            boolean founded = false;
            while (iter.hasNext() && !founded) {
                Vertex aux = iter.next();
                if (aux.getInfo().equals(vertexList.get(pos_bs2).getInfo())) {
                    founded = true;
                    BusStop pushed = (BusStop) aux.getInfo();
                    while (!pushed.equals(vertexList.get(pos_bs1).getInfo())) {
                        result.push(pushed);
                        aux = dijsktra_path.get(aux);
                        pushed = (BusStop) aux.getInfo();
                    }
                    result.push(pushed);
                }
            }
            return result;
        }
        else{
            throw new IllegalArgumentException("Posiciones fuera de rango");
        }
    }
    private Map<Vertex, Vertex> pathDijsktraAlgorithm(int pos_bs1, int pos_bs2) throws IllegalArgumentException{
        //routes.removeDisconnectVerticesND();
        if(posInRange(pos_bs1) && posInRange(pos_bs2)){
            Map<Vertex, Vertex> result = new HashMap<>();
            ArrayList<DijsktraVertex> d_vertexList = new ArrayList<>();
            int i = 0;
            Iterator<Vertex> iter = busStopGraph.getVerticesList().iterator();
            while(iter.hasNext()){
                Vertex aux = iter.next();
                if(i != pos_bs1){
                    d_vertexList.add(new DijsktraVertex(aux, 99999, false));
                }
                else{
                    d_vertexList.add(new DijsktraVertex(aux, 0, false));
                }
                i++;
            }

            while(!d_vertexList.get(pos_bs2).isVisited()) {
                //v = nodo de menor distancia a bs que no fue visitado aun
                DijsktraVertex least_distance_vertex = searchLeastDistanceVertex(d_vertexList);
                least_distance_vertex.setVisited(true);
                LinkedList<DijsktraVertex> adjacentList = adjacentDijsktraVertex(least_distance_vertex, d_vertexList);
                Iterator<DijsktraVertex> iter1 = adjacentList.iterator();
                while (iter1.hasNext()) {
                    DijsktraVertex aux = iter1.next();
                    int distanceD = least_distance_vertex.getDistance();
                    int weight = weight(aux.getVertex(), least_distance_vertex.getVertex());
                    int new_distance = distanceD + weight;
                    if (aux.getDistance() > new_distance) {
                        aux.setDistance(new_distance);
                        result.put(aux.getVertex(), least_distance_vertex.getVertex());
                    }
                }
            }
            return result;
        }
        else{
            throw new IllegalArgumentException("Posiciones fuera de rango");
        }
    }

    private LinkedList<DijsktraVertex> adjacentDijsktraVertex(DijsktraVertex d_vertex, ArrayList<DijsktraVertex> d_vertexList) {
        LinkedList<DijsktraVertex> result = new LinkedList<>();
        LinkedList<Vertex> adjacents = d_vertex.getVertex().getAdjacents();
        for(DijsktraVertex dv: d_vertexList){
            if(adjacents.contains(dv.getVertex()) && !dv.isVisited()){
                result.add(dv);
            }
        }
        return result;
    }

    private int weight(Vertex vertex1, Vertex vertex2) {
        int result = 0;
        LinkedList<Edge> edges = vertex1.getEdgeList();
        Iterator<Edge> iter = edges.iterator();
        boolean stop = false;
        while(iter.hasNext() && !stop){
            WeightedEdge aux_edge = (WeightedEdge) iter.next();
            if(aux_edge.getVertex().equals(vertex2)){
                result = (int) aux_edge.getWeight();
                stop = true;
            }
        }

        return result;
    }

    private DijsktraVertex searchLeastDistanceVertex(ArrayList<DijsktraVertex> d_vertexList) {
        DijsktraVertex result = d_vertexList.get(0);
        int dist = 999999;
        for(DijsktraVertex dv: d_vertexList){
            if((dv.getDistance() < dist) && !dv.isVisited()){
                dist = dv.getDistance();
                result = dv;
            }
        }
        return result;
    }

    private boolean posInRange(int pos) {
        return pos > -1 && pos < busStopGraph.getVerticesList().size();
    }

    public boolean insertBus(Bus bus){
        return true;
    }

    public boolean deleteBus(Bus bus){
        return true;
    }

    public boolean addBusStop(Bus bus, LinkedList<BusStop> route, String predecesor, int distance ){
        return true;
    }

    public boolean addBusStop(Bus bus, LinkedList<BusStop> route, String predecesor, int distance1, int distance2){
        return true;
    }

    public boolean deleteBusStop(Bus bus, BusStop bs){
        return true;
    }

    public ArrayList<Object[]> shortPathResponse(int pos_bs1, int pos_bs2){
        ArrayList<Object[]> result = new ArrayList<>();
        Deque<BusStop> sh_path = shortestPath(pos_bs1, pos_bs2);
        if(sh_path.size() > 1) {
            BusStop tail = sh_path.pop();
            while (!sh_path.isEmpty()) {
                BusStop head = sh_path.pop();
                result.add(travelKind(tail, head));
                tail = head;
            }
        }
        return result;
    }

    private Object[] travelKind(BusStop tail, BusStop head) {
        Object[]result = {tail, "no", head};
        Iterator<Bus> iter = busList.iterator();
        boolean founded = false;
        while(iter.hasNext() && !founded){
            Bus bus = iter.next();
            String bp = bus.busPath(tail, head);
            if(!bp.equalsIgnoreCase("no")){
                founded = true;
                result [1] = bp;
            }
        }
        return result;
    }

}
