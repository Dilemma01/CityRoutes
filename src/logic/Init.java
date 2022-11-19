/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

//import Interfaz.Principal;

/**
 *
 * @author PC-2018
 */
public class Init {
    private City city;

    public City getCity() {
        return city;
    }


    public Init() {

        city = new City("Havana");

        //************GUAGUAS************
        Bus b1 = new Bus("P1");
        Bus b2 = new Bus("P2");
        Bus b3 = new Bus("P3");
        Bus b4 = new Bus("P4");
        Bus b5 = new Bus("P5");
        Bus b6 = new Bus("P6");
        Bus b7 = new Bus("P7");
        Bus b8 = new Bus("P8");
        Bus b9 = new Bus("P9");

        //************PARADAS************
        BusStop bs1 = new BusStop("La Rosita");
        BusStop bs2 = new BusStop("La Cumbre");
        BusStop bs3 = new BusStop("Dolores");
        BusStop bs4 = new BusStop("Carolina");
        BusStop bs5 = new BusStop("Alberro");
        BusStop bs6 = new BusStop("Bello Palmar");
        BusStop bs7 = new BusStop("Terminal Cotorro");
        BusStop bs8 = new BusStop("Alamar");
        BusStop bs9 = new BusStop("Tunel de Linea");
        BusStop bs10 = new BusStop("Hanoi");
        BusStop bs11 = new BusStop("San Agustin");
        BusStop bs12 = new BusStop("51 y 250");
        BusStop bs13 = new BusStop("La Punta");
        BusStop bs14 = new BusStop("Parcelacion");
        BusStop bs15 = new BusStop("Rpto Electrico");
        BusStop bs16 = new BusStop("Yumuri");
        BusStop bs17 = new BusStop("Palace");
        BusStop bs18 = new BusStop("Las Granjas");
        BusStop bs19 = new BusStop("San Pedro");
        BusStop bs20 = new BusStop("Siboney");
        BusStop bs21 = new BusStop("Las Marias");

        //************ASIGNACION DE PARADAS A LAS GUAGUAS************
        b1.getRoute().add(bs1);
        b1.getRoute().add(bs2);
        b1.getRoute().add(bs3);
        b1.getRoute().add(bs4);

        b2.getRoute().add(bs5);
        b2.getRoute().add(bs6);
        b2.getRoute().add(bs2);
        b2.getRoute().add(bs7);

        b3.getRoute().add(bs8);
        b3.getRoute().add(bs3);
        b3.getRoute().add(bs4);
        b3.getRoute().add(bs9);

        b4.getRoute().add(bs1);
        b4.getRoute().add(bs10);
        b4.getRoute().add(bs2);

        b5.getRoute().add(bs11);
        b5.getRoute().add(bs5);
        b5.getRoute().add(bs12);
        b5.getRoute().add(bs13);

        b6.getRoute().add(bs14);
        b6.getRoute().add(bs15);
        b6.getRoute().add(bs7);
        b6.getRoute().add(bs16);

        b7.getRoute().add(bs14);
        b7.getRoute().add(bs17);
        b7.getRoute().add(bs18);
        b7.getRoute().add(bs19);

        b8.getRoute().add(bs20);
        b8.getRoute().add(bs12);
        b8.getRoute().add(bs19);

        b9.getRoute().add(bs21);
        b9.getRoute().add(bs19);
        b9.getRoute().add(bs18);

        //************INSERTAR GUAGUAS ************
        //El METODO GETBUSLIST NO DEBE DEVOLVER LA LINKED LIST
        city.getBusList().add(b1);
        city.getBusList().add(b2);
        city.getBusList().add(b3);
        city.getBusList().add(b4);
        city.getBusList().add(b5);
        city.getBusList().add(b6);
        city.getBusList().add(b7);
        city.getBusList().add(b8);
        city.getBusList().add(b9);
        
        //************INSERTAR PARADAS EN EL GRAFO************
        city.getBusStopGraph().insertVertex(bs1);
        city.getBusStopGraph().insertVertex(bs2);
        city.getBusStopGraph().insertVertex(bs3);
        city.getBusStopGraph().insertVertex(bs4);
        city.getBusStopGraph().insertVertex(bs5);
        city.getBusStopGraph().insertVertex(bs6);
        city.getBusStopGraph().insertVertex(bs7);
        city.getBusStopGraph().insertVertex(bs8);
        city.getBusStopGraph().insertVertex(bs9);
        city.getBusStopGraph().insertVertex(bs10);
        city.getBusStopGraph().insertVertex(bs11);
        city.getBusStopGraph().insertVertex(bs12);
        city.getBusStopGraph().insertVertex(bs13);
        city.getBusStopGraph().insertVertex(bs14);
        city.getBusStopGraph().insertVertex(bs15);
        city.getBusStopGraph().insertVertex(bs16);
        city.getBusStopGraph().insertVertex(bs17);
        city.getBusStopGraph().insertVertex(bs18);
        city.getBusStopGraph().insertVertex(bs19);
        city.getBusStopGraph().insertVertex(bs20);
        city.getBusStopGraph().insertVertex(bs21);

        //************INSERTAR PESO ENTRE LAS PARADAS************
        city.getBusStopGraph().insertWEdgeNDG(1, 0, 800);
        city.getBusStopGraph().insertWEdgeNDG(9, 0, 700);
        city.getBusStopGraph().insertWEdgeNDG(4, 0, 400);
        city.getBusStopGraph().insertWEdgeNDG(9, 1, 800);
        city.getBusStopGraph().insertWEdgeNDG(6, 1, 900);
        city.getBusStopGraph().insertWEdgeNDG(2, 1, 550);
        city.getBusStopGraph().insertWEdgeNDG(5, 1, 800);
        city.getBusStopGraph().insertWEdgeNDG(6, 2, 200);
        city.getBusStopGraph().insertWEdgeNDG(7, 2, 600);
        city.getBusStopGraph().insertWEdgeNDG(9, 6, 600);
        city.getBusStopGraph().insertWEdgeNDG(3, 2, 700);
        city.getBusStopGraph().insertWEdgeNDG(8, 3, 800);
        city.getBusStopGraph().insertWEdgeNDG(16, 3, 400);
        city.getBusStopGraph().insertWEdgeNDG(5, 4, 800);
        city.getBusStopGraph().insertWEdgeNDG(11, 4, 1000);
        city.getBusStopGraph().insertWEdgeNDG(10, 4, 1150);
        city.getBusStopGraph().insertWEdgeNDG(7, 5, 100);
        city.getBusStopGraph().insertWEdgeNDG(15, 6, 1300);
        city.getBusStopGraph().insertWEdgeNDG(14, 6, 700);
        city.getBusStopGraph().insertWEdgeNDG(2, 8, 200);
        city.getBusStopGraph().insertWEdgeNDG(19, 10, 100);
        city.getBusStopGraph().insertWEdgeNDG(12, 11, 1150);
        city.getBusStopGraph().insertWEdgeNDG(19, 11, 1100);
        city.getBusStopGraph().insertWEdgeNDG(18, 11, 1300);
        city.getBusStopGraph().insertWEdgeNDG(14, 13, 600);
        city.getBusStopGraph().insertWEdgeNDG(16, 13, 1150);
        city.getBusStopGraph().insertWEdgeNDG(17, 16, 1100);
        city.getBusStopGraph().insertWEdgeNDG(18, 17, 1300);
        city.getBusStopGraph().insertWEdgeNDG(20, 18, 100);


        /*Deque<BusStop> test_path = city.shortestPath(1, 20);
        while (!test_path.isEmpty()) {
            System.out.println(test_path.pop());
        }*/
        

    }


    
    

}
