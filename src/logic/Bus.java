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
    /*\
     * 		Enrique nov 27
     *
     * La usa City para al insertar una ruta verificar q un Bus ya realiza es ruta
     *
     */
    public boolean routesMatches(LinkedList<BusStop> new_route) {
        boolean result=true;

        if(new_route.size() != this.route.size())
            result=false;
        else {
            Iterator<BusStop> iter_1 = this.route.iterator();
            Iterator<BusStop> iter_2 = new_route.iterator();

            while (iter_1.hasNext() && iter_2.hasNext()) {
                String name_1 = iter_1.next().getName();
                String name_2 = iter_2.next().getName();

                if( !(name_1.equalsIgnoreCase(name_2)) )
                    result=false;
            }
        }
        return result;
    }

}
