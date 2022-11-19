package logic;

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




}
