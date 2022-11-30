import cu.edu.cujae.ceis.graph.vertex.Vertex;
import logic.Bus;
import logic.BusStop;
import logic.City;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

    public class Test {  // esta en el paquete x defecto al igual q el Main

        public static void show_Buses(City city) {

            LinkedList<Bus> buses = city.getBusList();
            Iterator<Bus> iter = buses.iterator();
            System.out.println("\n. . . Buses de la Ciudad . . .\n");

            int i=1;
            while (iter.hasNext()) {
                Bus b = iter.next();
                if(i<10)
                    System.out.print(0);
                System.out.println(i+". " + b.getId());
                i++;
            }
            System.out.println("\n. . . . . . . .");
        }

        public static void show_Buses_with_route(City city) {

            LinkedList<Bus> buses = city.getBusList();
            Iterator<Bus> iter = buses.iterator();
            System.out.println("\n. . . Buses de la Ciudad con su Ruta . . .\n");

            while (iter.hasNext()) {
                Bus b = iter.next();

                System.out.println("Nombre: " + b.getId());
                System.out.println("Ruta: ");

                Iterator<BusStop> iter_2 = b.getRoute().iterator();
                int i=1;

                while (iter_2.hasNext()) {
                    if(i<10)
                        System.out.print(0);
                    System.out.println(i+". " + iter_2.next().getName());
                    i++;
                }
                System.out.println(". . .\n");
            }
            System.out.println("\n. . . . . . . .");
        }



        public static void show_BusesStop(City city) {

            LinkedList<Vertex> busesStop = city.getBusStopGraph().getVerticesList();
            Iterator<Vertex> iter = busesStop.iterator();
            System.out.println("\n. . . Paradas de la Ciudad . . .\n");

            int i=1;
            while (iter.hasNext()) {
                String bs = ((BusStop) iter.next().getInfo()).getName();
                if(i<10)
                    System.out.print(0);
                System.out.println(i+". " + bs);
                i++;
            }

            System.out.println("\n. . . . . . . .");

        }

        public static void show_shortestPath(City city, int pos1, int pos2) {

            Deque<BusStop>path = city.shortestPath(pos1, pos2);
            String bs1 = ((BusStop) city.getBusStopGraph().getVerticesList().get(pos1).getInfo()).getName();
            String bs2 = ((BusStop) city.getBusStopGraph().getVerticesList().get(pos2).getInfo()).getName();

            System.out.println("\n. . . Camino mas corto . . .");
            System.out.println("Entre las paradas: \"" + bs1 + "\" y \"" + bs2 + "\"\n");
            int i=1;
            while(!path.isEmpty()){
                if(i<10)
                    System.out.print(0);
                System.out.println(i+". " + path.pop().getName());
                i++;
            }
            System.out.println("\n. . . . . .");

        }

        public static void show_shortestPathResponse(City city, int pos1, int pos2) {

            ArrayList<Object[]> test2 = city.shortPathResponse(pos1, pos2);
            String bs1 = ((BusStop) city.getBusStopGraph().getVerticesList().get(pos1).getInfo()).getName();
            String bs2 = ((BusStop) city.getBusStopGraph().getVerticesList().get(pos2).getInfo()).getName();

            System.out.println("\n. . . Camino mas corto con la ruta que debes coger o si es caminando . . .");
            System.out.println("Entre las paradas: \"" + bs1 + "\" y \"" + bs2 + "\"\n");

            System.out.print("Parada: " + ((BusStop) test2.get(0)[0]).getName());
            for(Object[] o : test2){
                String route = (String) o[1];
                if(route.equalsIgnoreCase("no"))
                    route = "Caminando";
                System.out.println(". Ruta: " + route);
                System.out.print("Parada: " + ((BusStop) o[2]).getName());
            }
            System.out.println("\n\n. . . . . . .");
        }





    }

