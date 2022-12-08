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
                        if(aux==null){
                            throw new IllegalArgumentException("CAMINANTE NO HAY CAMINO, SE HACE CAMINO AL ANDAR");
                        }else{
                            pushed = (BusStop) aux.getInfo();
                        }

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

    public boolean insertBus_withRoute (String bus_string, LinkedList<String> route_string){
        boolean done = true;
        Bus bus = buscarBusId(bus_string);
        LinkedList<BusStop> route = new LinkedList<>();

        Iterator<String> iterRoute = route_string.iterator();
        while(iterRoute.hasNext()){
            BusStop bs_aux = buscarBusStopId(iterRoute.next());
            if(bs_aux != null){
                route.add(bs_aux);
            }
        }

        if( bus==null || route == null ) {// primero si no son null
            done = false;
        }
        // para despues verificar con funciones q lo usan
        else if(existsBusID(bus.getId())  || route.size()<2
                || repeatedBusStop(route) || alreadyExists_Route(route)
                || !exists_thisPath_inTheGraph(route)) {
            done = false;
        }
        else {
            bus.setRoute(route);
            this.busList.add(bus);
        }
        return done;
    }
    private int indexOf_BusStop_Descending(BusStop bs) {
        int index=-1;
        Iterator<Vertex> iter = busStopGraph.getVerticesList().descendingIterator();

        int i = busStopGraph.getVerticesList().size()-1; // ultima posicion

        while (iter.hasNext() && index==-1) {
            String aux_id = ((BusStop)iter.next().getInfo()).getName();

            if(aux_id.equalsIgnoreCase(bs.getName()))
                index=i;
            i--;
        }
        return index;
    }

    private boolean exists_thisPath_inTheGraph(LinkedList<BusStop> route) {
        boolean result = true;
        if(route.size() < 2){
            result = false;
        }
        else{
            Iterator<BusStop> iter = route.iterator();
            int index1 = indexOf_BusStop_Descending(iter.next());
            while(iter.hasNext() && result) {
                BusStop bs = iter.next();
                int index2 = indexOf_BusStop_Descending(bs);
                if(index1 < 0 || index2 < 0){
                    result = false;
                }
                else{
                    result = this.busStopGraph.areAdjacents(index1, index2);
                    index1 = index2;
                }
            }
        }
        return result;
    }

    private boolean existsBusID(String id) {
        boolean result=false;
        Iterator<Bus> iter = busList.iterator();

        while (iter.hasNext() && !result) {
            String aux_id = iter.next().getId();

            if(id.equalsIgnoreCase(aux_id))
                result=true;
        }
        return result;
    }

    private boolean repeatedBusStop(LinkedList<BusStop> route) {
        boolean result=false;
        String id_aux;

        LinkedList<String> id_list = new LinkedList<String>();
        Iterator<BusStop> iter = route.iterator();

        while(iter.hasNext() && !result) {  // sacar el ID para la lista para comparar por el
            id_aux = iter.next().getName();
            if(id_list.contains(id_aux)){
                result=true;
            }else {
                id_list.add(id_aux);
            }
        }

        /*while( !(id_list.isEmpty()) && !result) {
            id_aux = route.removeFirst().getName(); // obtiene y elimina el 1ro de la lista
            if(id_list.contains(id_aux))    // y busca si hay otro igual en el resto de la lista
                result=true;
        }*/
        return result;
    }

    public boolean insertBusStop(String bs_string) {
        boolean result=false;

        if(!existsBusStopID(bs_string)) {
            busStopGraph.insertVertex(new BusStop(bs_string));
            result=true;
        }
        return result;
    }

    public boolean insertBusStopWithPath(String bsExistent_string, String bsNew_string, int distance){
        boolean result = false;
        BusStop bsExistent = buscarBusStopId(bsExistent_string);

        if((bsExistent != null) && !(existsBusStopID(bsNew_string)) && (distance > 0)) {
            int index_existent = busStopIndex(bsExistent);
            insertBusStop(bsNew_string);
            BusStop bsNew = buscarBusStopId(bsNew_string);
            int index_new = indexOf_BusStop_Descending(bsNew);
            this.busStopGraph.insertWEdgeNDG(index_existent, index_new, distance);
            result = true;
        }

        return result;
    }

    public boolean insertPathBetweenBusStop (String bs1_string, String bs2_string, int distance){
        boolean result = false;
        BusStop bs1 = buscarBusStopId(bs1_string);
        BusStop bs2 = buscarBusStopId(bs2_string);
        if((bs1 != null) && (bs2 != null) && (distance > 0)) {
            LinkedList<BusStop> bsList = new LinkedList<>();
            bsList.add(bs1);
            bsList.add(bs2);
            if(!exists_thisPath_inTheGraph(bsList)){
                this.busStopGraph.insertWEdgeNDG(busStopIndex(bs1), busStopIndex(bs2), distance);
            }
            else{
                result = false;
            }
        }
        return result;
    }
    public boolean insertBus(String bus_string) {
        boolean result = false;
        if(!existsBusID(bus_string)) {
            result=true;
            this.busList.add(new Bus(bus_string));
        }
        return result;
    }
    private boolean alreadyExists_Route(LinkedList<BusStop> route) {
        boolean result=false;
        Iterator<Bus> iter = this.busList.iterator();

        while (iter.hasNext() && !result) {
            result = iter.next().routesMatches(route);
        }
        return result;
    }
    private boolean existsBusStopID(String bs_id) {
        boolean result=false;
        Iterator<Vertex> iter = busStopGraph.getVerticesList().iterator();

        while(iter.hasNext() && !result) {
            String aux_id = ((BusStop) iter.next().getInfo()).getName();

            if(bs_id.equalsIgnoreCase(aux_id))
                result=true;
        }
        return result;
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

    //Metodo para dado una parada devolver la referencia al vertex que corresponde
    private Vertex busStopToVertex(BusStop busStop) {
        Vertex vertexBusStop = null;
        Vertex aux = null;

        Iterator<Vertex> iterVertex = busStopGraph.getVerticesList().iterator();

        while(iterVertex.hasNext() && vertexBusStop == null) {
            aux = iterVertex.next();
            if(aux.getInfo().equals(busStop)) {
                vertexBusStop = aux;
            }
        }

        return vertexBusStop;
    }
    //Metodo para eliminar una ruta completa

    public boolean deleteRoute(String busString) {
        boolean result = false;

        Bus bus = buscarBusId(busString);

        Iterator<BusStop> iter = bus.getRoute().iterator();
        BusStop aux = null;

        while(iter.hasNext()){
            aux = iter.next();
            deleteBusStopToBus(busString, aux.getName());
        }

        this.busList.remove(bus);

        return result;

    }
    //Metodo para eliminar una parada de la guagua, si es la unica guagua, elimina tambien del grafo

    public boolean deleteBusStopToBus(String busString, String busStopString) {
        boolean result = false;

        BusStop busStopDelete = buscarBusStopId(busStopString);
        Bus bus = buscarBusId(busString);

        if(bus.haveBusStop(busStopDelete)) {

            result = bus.deleteBusStop(busStopDelete);

            if(busListOfBusStop(busStopString).isEmpty()) {

                busStopGraph.deleteVertex(busStopIndex(busStopDelete));
            }

        }

        return result;
    }
    //Metodo para eliminar una parada del grafo
    public boolean deleteBusStop(String id) {
        boolean result = false;
        BusStop busStopDelete = buscarBusStopId(id);

        // Este while elimina la paradas de las rutas
        if(busStopDelete != null) {

            Iterator<Bus> iter = this.busList.iterator();

            while(iter.hasNext()){

                Bus aux = iter.next();

                if(aux.haveBusStop(busStopDelete)){
                    aux.deleteBusStop(busStopDelete);
                }
            }

            Vertex vBusStopDelete = busStopToVertex(busStopDelete);

            //Aqui elimino la parada del grafo
            LinkedList<Vertex> adjList = vBusStopDelete.getAdjacents();
            Iterator<Vertex> adjListIter = adjList.iterator();


            while(adjListIter.hasNext()) {
                Vertex actual = adjListIter.next();
                Iterator<Vertex> adjListIter2 = adjList.iterator();
                LinkedList<Bus> busListActual = busListOfBusStop(((BusStop) actual.getInfo()).getName());

                while(adjListIter2.hasNext()) {
                    Vertex actual2 = adjListIter2.next();

                    if(atLeastBusToBusStop(busListActual, ((BusStop) actual2.getInfo()))) {
                        if (!this.busStopGraph.areAdjacents(indexVertex(actual), indexVertex(actual2))) {
                            this.busStopGraph.insertWEdgeNDG(indexVertex(actual), indexVertex(actual2),totalWeight(actual, vBusStopDelete, actual2));
                        }
                    }
                }
            }

            busStopGraph.deleteVertex(indexVertex(vBusStopDelete));
            result = true;

        }

        return result;

    }
    //Metodo para dado un vertex devolver el indice
    private int indexVertex(Vertex vertex) {
        int index = -1;
        boolean flag = false;

        Iterator<Vertex> graphIter = busStopGraph.getVerticesList().iterator();

        while(graphIter.hasNext() && !flag) {
            index++;
            if(vertex.equals(graphIter.next())){
                flag = true;
            }
        }

        if(!flag) {
            index = -1;
        }

        return index;
    }


    //Metodo para dada una lista de guaguas y una parada, devuelve boleano si al menos una guagua de la lista para ahi

    private boolean atLeastBusToBusStop(LinkedList<Bus> busList, BusStop busStop) {
        boolean result = false;
        LinkedList<Bus> busListBusStop = busListOfBusStop(busStop.getName());

        Iterator<Bus> busListIter = busList.iterator();
        Bus actual = null;

        while(busListIter.hasNext() && !result) {

            actual = busListIter.next();
            Iterator<Bus> busListBusStopIter = busListBusStop.iterator();

            while(busListBusStopIter.hasNext() && !result) {
                if(actual.equals(busListBusStopIter.next())) {
                    result = true;
                }
            }
        }

        return result;
    }


    //Metodo para saber el peso total cuando elimine una parada intermedia
    //Te devuelve el peso total de un vertex a otro pasando por un vertex comun
    private int totalWeight(Vertex first, Vertex medium, Vertex last) {
        int weightResult = -1;
        int weightFirst = 0;
        int weightLast = 0;

        LinkedList<Vertex> vList = medium.getAdjacents();

        Edge actual = null;

        if(vList.contains(first) && vList.contains(last)) {

            LinkedList<Edge> eList = first.getEdgeList();
            Iterator<Edge> eListIter = eList.iterator();

            boolean flag = false;


            while(eListIter.hasNext() && !flag) {
                actual = eListIter.next();
                if(actual.getVertex().equals(medium)) {
                    weightFirst = (int) ((WeightedEdge)(actual)).getWeight();
                    flag = true;
                }

            }

            eList = last.getEdgeList();
            eListIter = eList.iterator();
            flag = false;

            while(eListIter.hasNext() && !flag) {
                actual = eListIter.next();
                if(actual.getVertex().equals(medium)) {
                    weightFirst = (int) ((WeightedEdge)(actual)).getWeight();
                    flag = true;
                }


            }

            weightResult = weightFirst + weightLast;
        }

        return weightResult;
    }


    //Metodo para dado una parada devolver todas las rutas que pasan por ahi
    private LinkedList<Bus> busListOfBusStop(String id){
        LinkedList<Bus> result = new LinkedList<Bus>();
        BusStop busStop = buscarBusStopId(id);
        Iterator<Bus> iterBus = this.busList.iterator();

        while(iterBus.hasNext()) {
            Bus actual = iterBus.next();
            if(actual.haveBusStop(busStop)) {
                result.add(actual);
            }

        }

        return result;

    }


    // Metodo para buscar dado el id de una parada devolver la parada
    private BusStop buscarBusStopId(String id) {
        BusStop busStopResult = null;
        Iterator<Vertex> iter = busStopGraph.getVerticesList().iterator();
        Vertex actual;

        while(iter.hasNext() && busStopResult == null) {
            actual = iter.next();
            if(((BusStop)actual.getInfo()).getName().equalsIgnoreCase(id)) {
                busStopResult = (BusStop)actual.getInfo();
            }

        }

        return busStopResult;
    }

    //Metodo para buscar dado el id de un bus y devolver el bus
    private Bus buscarBusId(String id) {
        Bus busResult = null;
        boolean flag = false;
        Iterator<Bus> iter = busList.iterator();

        while(iter.hasNext() && flag == false) {
            busResult = iter.next();

            if(busResult.getId().equalsIgnoreCase(id)) {
                flag = true;
            }

        }

        if(!flag) {
            busResult = null;
        }

        return busResult;
    }

    //Metodo para devolver el indice de una parada
    private int busStopIndex(BusStop bs){
        int i = -1;
        boolean found = false;
        Iterator<Vertex>iter = this.busStopGraph.getVerticesList().iterator();
        while(iter.hasNext() && !found){
            if(iter.next().getInfo().equals(bs)){
                found = true;
            }
            i++;
        }
        if(!found){
            i = -1;
        }
        return i;
    }

}
