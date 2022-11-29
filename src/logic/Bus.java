package logic;

import java.util.Iterator;
import java.util.LinkedList;

public class Bus {
    private String id;
    private LinkedList<BusStop> route;

    public Bus(String id) {
        setId(id);
        route = new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkedList<BusStop> getRoute() {
        return route;
    }

    public boolean addBusStop(LinkedList<BusStop> route, BusStop predecesor) {
       return true;
    }
    public String busPath(BusStop tail, BusStop head) {
        String result = "no";
        Iterator<BusStop> iter = route.iterator();
        boolean founded = false;
        while (!founded && iter.hasNext()) {
            BusStop bs1 = iter.next();
            if((bs1.equals(tail) || bs1.equals(head)) && iter.hasNext()){
                BusStop bs2 = iter.next();
                if ((bs1.equals(tail) && bs2.equals(head)) || (bs1.equals(head) && bs2.equals(tail))){
                    founded = true;
                    result = this.id;
                }
            }
        }
        return result;
    }

    public void setRoute(LinkedList<BusStop> route) {
        this.route = route;
    }

    //Metodo para dado una parada devolver true o false si la guagua pasa por esa parada
    public boolean haveBusStop(BusStop busStop) {
        boolean result = false;
        Iterator<BusStop> iter = route.iterator();

        while(iter.hasNext() && !result) {
            if(iter.next().equals(busStop)) {
                result = true;
            }
        }

        return result;
    }

    //Metodo para eliminar una parada de la lista de paradas
    public boolean deleteBusStop(BusStop delete) {
        boolean result = route.remove(delete);
        return result;


    }
}
