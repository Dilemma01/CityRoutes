import logic.BusStop;
import logic.City;

import java.util.ArrayList;
import java.util.Deque;

public class Main {
    public static void main(String[] args) {
        City city = new City();

        /*ArrayList<String> array1 = new ArrayList<>();
        array1.add("p1");

        ArrayList<String> array2 = new ArrayList<>();
        array2.add("p2");

        ArrayList<String> array3 = new ArrayList<>();
        array3.add("p3");

        ArrayList<String> array4 = new ArrayList<>();
        array4.add("p1");
        array4.add("p2");

        ArrayList<String> array5 = new ArrayList<>();
        array5.add("p2");

        ArrayList<String> array6 = new ArrayList<>();
        array6.add("p3");

        ArrayList<String> array7 = new ArrayList<>();
        array7.add("p4");

        ArrayList<String> array8 = new ArrayList<>();
        array8.add("p1");
        array8.add("p2");
        array8.add("p4");

        ArrayList<String> array9 = new ArrayList<>();
        array9.add("p1");
        array9.add("p4");

        ArrayList<String> array10 = new ArrayList<>();
        array10.add("p3");
        array10.add("p4");

        ArrayList<String> array11 = new ArrayList<>();
        array11.add("p4");

        ArrayList<String> array12 = new ArrayList<>();
        array12.add("p3");

        ArrayList<String> array13 = new ArrayList<>();
        array13.add("p3");

        ArrayList<String> array14 = new ArrayList<>();
        array14.add("p1");*/




        BusStop bs1 = new BusStop("a");
        BusStop bs2 = new BusStop( "b");
        BusStop bs3 = new BusStop( "c");
        BusStop bs4 = new BusStop( "d");
        BusStop bs5 = new BusStop( "e");
        BusStop bs6 = new BusStop( "f");
        BusStop bs7 = new BusStop( "g");
        BusStop bs8 = new BusStop("h");
        BusStop bs9 = new BusStop( "i");
        BusStop bs10 = new BusStop( "j");
        BusStop bs11 = new BusStop( "k");
        BusStop bs12 = new BusStop( "l");
        BusStop bs13 = new BusStop( "m");
        BusStop bs14 = new BusStop( "n");


        city.getRoutes().insertVertex(bs1);
        city.getRoutes().insertVertex(bs2);
        city.getRoutes().insertVertex(bs3);
        city.getRoutes().insertVertex(bs4);
        city.getRoutes().insertVertex(bs5);
        city.getRoutes().insertVertex(bs6);
        city.getRoutes().insertVertex(bs7);
        city.getRoutes().insertVertex(bs8);
        city.getRoutes().insertVertex(bs9);
        city.getRoutes().insertVertex(bs10);
        city.getRoutes().insertVertex(bs11);
        city.getRoutes().insertVertex(bs12);
        city.getRoutes().insertVertex(bs13);
        city.getRoutes().insertVertex(bs14);

        city.getRoutes().insertWEdgeNDG(1, 3, 200 );
        city.getRoutes().insertWEdgeNDG(3, 0, 200);
        city.getRoutes().insertWEdgeNDG(7, 3, 400);
        city.getRoutes().insertWEdgeNDG(8, 7, 800);
        city.getRoutes().insertWEdgeNDG(13, 8, 900);
        city.getRoutes().insertWEdgeNDG(5, 2, 300);
        city.getRoutes().insertWEdgeNDG(11, 5, 800);
        city.getRoutes().insertWEdgeNDG(12, 11, 1000);
        city.getRoutes().insertWEdgeNDG(9, 12, 500);
        city.getRoutes().insertWEdgeNDG(9, 6, 600);
        city.getRoutes().insertWEdgeNDG(7, 9, 200);
        city.getRoutes().insertWEdgeNDG(10, 9, 800);
        city.getRoutes().insertWEdgeNDG(8, 10, 600);
        city.getRoutes().insertWEdgeNDG(3, 2, 800);
        city.getRoutes().insertWEdgeNDG(4, 7, 1000);
        city.getRoutes().insertWEdgeNDG(6, 5, 150);
        city.getRoutes().insertWEdgeNDG(2, 0, 100);
        city.getRoutes().insertWEdgeNDG(13, 10, 300);

        Deque<BusStop>test_path = city.shortestPath(1,13);
        while(!test_path.isEmpty()){
            System.out.println(test_path.pop());
        }




        /*Vertex v1 = new Vertex(bs1);
        Vertex v2 = new Vertex(bs2);
        Vertex v3 = new Vertex(bs3);
        Vertex v4 = new Vertex(bs4);
        Vertex v5 = new Vertex(bs5);
        Vertex v6 = new Vertex(bs6);
        Vertex v7 = new Vertex(bs7);
        Vertex v8 = new Vertex(bs8);
        Vertex v9 = new Vertex(bs9);
        Vertex v10 = new Vertex(bs10);
        Vertex v11 = new Vertex(bs11);
        Vertex v12 = new Vertex(bs12);
        Vertex v13 = new Vertex(bs13);
        Vertex v14 = new Vertex(bs14);*/

    }


}