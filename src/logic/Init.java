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

   
    
    public Init() {

        City city = new City("Havana");

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
        city.getBusStopList().insertVertex(bs1);
        city.getBusStopList().insertVertex(bs2);
        city.getBusStopList().insertVertex(bs3);
        city.getBusStopList().insertVertex(bs4);
        city.getBusStopList().insertVertex(bs5);
        city.getBusStopList().insertVertex(bs6);
        city.getBusStopList().insertVertex(bs7);
        city.getBusStopList().insertVertex(bs8);
        city.getBusStopList().insertVertex(bs9);
        city.getBusStopList().insertVertex(bs10);
        city.getBusStopList().insertVertex(bs11);
        city.getBusStopList().insertVertex(bs12);
        city.getBusStopList().insertVertex(bs13);
        city.getBusStopList().insertVertex(bs14);
        city.getBusStopList().insertVertex(bs15);
        city.getBusStopList().insertVertex(bs16);
        city.getBusStopList().insertVertex(bs17);
        city.getBusStopList().insertVertex(bs18);
        city.getBusStopList().insertVertex(bs19);
        city.getBusStopList().insertVertex(bs20);
        city.getBusStopList().insertVertex(bs21);

        //************INSERTAR PESO ENTRE LAS PARADAS************
       /* city.getRoutes().insertWEdgeNDG(1, 0, 800);
        city.getRoutes().insertWEdgeNDG(9, 0, 700);
        city.getRoutes().insertWEdgeNDG(4, 0, 400);
        city.getRoutes().insertWEdgeNDG(9, 1, 800);
        city.getRoutes().insertWEdgeNDG(6, 1, 900);
        city.getRoutes().insertWEdgeNDG(2, 1, 550);
        city.getRoutes().insertWEdgeNDG(5, 1, 800);
        city.getRoutes().insertWEdgeNDG(6, 2, 200);
        city.getRoutes().insertWEdgeNDG(7, 2, 600);
        city.getRoutes().insertWEdgeNDG(9, 6, 600);
        city.getRoutes().insertWEdgeNDG(3, 2, 700);
        city.getRoutes().insertWEdgeNDG(8, 3, 800);
        city.getRoutes().insertWEdgeNDG(16, 3, 400);
        city.getRoutes().insertWEdgeNDG(5, 4, 800);
        city.getRoutes().insertWEdgeNDG(11, 4, 1000);
        city.getRoutes().insertWEdgeNDG(10, 4, 1150);
        city.getRoutes().insertWEdgeNDG(7, 5, 100);
        city.getRoutes().insertWEdgeNDG(15, 6, 1300);
        city.getRoutes().insertWEdgeNDG(14, 6, 700);
        city.getRoutes().insertWEdgeNDG(2, 8, 200);
        city.getRoutes().insertWEdgeNDG(19, 10, 100);
        city.getRoutes().insertWEdgeNDG(12, 11, 1150);
        city.getRoutes().insertWEdgeNDG(19, 11, 1100);
        city.getRoutes().insertWEdgeNDG(18, 11, 1300);
        city.getRoutes().insertWEdgeNDG(14, 13, 600);
        city.getRoutes().insertWEdgeNDG(16, 13, 1150);
        city.getRoutes().insertWEdgeNDG(17, 16, 1100);
        city.getRoutes().insertWEdgeNDG(18, 17, 1300);
        city.getRoutes().insertWEdgeNDG(20, 18, 100); */


        /*Deque<BusStop> test_path = city.shortestPath(1, 20);
        while (!test_path.isEmpty()) {
            System.out.println(test_path.pop());
        }*/
        

    }


    
    

}
