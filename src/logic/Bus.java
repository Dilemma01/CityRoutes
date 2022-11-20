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

    public boolean deleteBusStop(BusStop bs){
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
}
