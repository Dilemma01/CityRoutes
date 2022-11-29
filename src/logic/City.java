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

    /*
     * 		Enrique nov 24
     *
     * Se inserta en la City un nuevo Bus con su Ruta
     *
     * Se verifica q:
     *  - el Bus no exista ya con su ID
     *  - de esa lista no haya paradas repetidas en si misma   [funcion: repeatedBusStop]
     *
     * Cuando tod0 esta correcto:
     * . insertar los BusStop en el grafo [funcion: ]
     * . annadir a la lista del bus
     * . annadir el bus a la lista de City
     */
    public boolean insertBus(Bus bus, LinkedList<BusStop> route){
        boolean done=false;
        if(!existsBusID(bus.getId()) && !repeatedBusStop(route) ) {
            Iterator<BusStop> iter = route.iterator();
            while(iter.hasNext()) {
                insertBusStop(iter.next());
            }
            bus.setRoute(route);
            busList.add(bus);	// ya se verifico q no existe
            done = true;
        }
        return done;
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
    /*
     * 			Enrique nov24
     *
     * Busca en la ruta dada (lista de paradas) que no se repita ninguna parada
     */
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
    /*
     * 		Enrique nov24
     *
     * Para insertar una nueva BusStop (Vertice) utiliza la funcion existsBusStopID
     * para garantizar q no exista otra con ese ID
     */
    public boolean insertBusStop(BusStop bs) {
        boolean result=false;

        if(!(existsBusStopID(bs.getName()))) {
            busStopGraph.insertVertex(bs);
            result=true;
        }
        return result;
    }
    /*
     * 		Enrique nov24
     *
     * garantiza q no exista otra con ese ID
     * return true si ya existe
     */
    public boolean existsBusStopID(String bs_id) {
        boolean result=false;
        Iterator<Vertex> iter = busStopGraph.getVerticesList().iterator();

        while(iter.hasNext() && !result) {
            String aux_id = ((BusStop) iter.next().getInfo()).getName();

            if(bs_id.equalsIgnoreCase(aux_id))
                result=true;
        }
        return result;
    }
    /*
     * 		Enrique nov24
     *  esto seria como un modificar q fue lo q ya te mande
     *  solo tendria q ponerlo en la clase City y hacerle pequennos cambios
     *  y ver como vamos a tratar el tema de la distancia !!!
     */



    public boolean deleteBus(Bus bus){
        return true;
    }

    public boolean addBusStop_to_Bus(Bus bus,BusStop bs, String predecesor, int distance ){
        return true;
    }

    public boolean addRoute_to_Bus(Bus bus,BusStop bs, String predecesor, int distance1, int distance2){
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

    //Metodo para dado una parada devolver la referencia al vertex que corresponde
    public Vertex busStopToVertex(BusStop busStop) {
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
    public int indexVertex(Vertex vertex) {
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

    public boolean atLeastBusToBusStop(LinkedList<Bus> busList, BusStop busStop) {
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
    public LinkedList<Bus> busListOfBusStop(String id){
        LinkedList<Bus> result = new LinkedList<Bus>();
        BusStop busStop = buscarBusStopId(id);
        Iterator<Bus> iterBus = this.busList.iterator();

        while(iterBus.hasNext()) {
            Bus actual = iterBus.next();
            if(actual.haveBusStop(busStop)) {
                busList.add(actual);
            }

        }

        return result;

    }


    // Metodo para buscar dado el id de una parada devolver la parada
    public BusStop buscarBusStopId(String id) {
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
    public Bus buscarBusId(String id) {
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
