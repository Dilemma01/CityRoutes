import gui.MainFrame;
import logic.BusStop;
import logic.Init;

import java.util.ArrayList;
import java.util.Deque;

public class Main {

    public static void main(String[] args) {

        Init init = new Init();
        init.getCity().deleteBusStop("Pruebas");
        init.getCity().deleteBusStop("Alberro");
        init.getCity().insertBusStop("Alberro");
        init.getCity().insertPathBetweenBusStop("Alberro", "La Rosita", 400);
        init.getCity().insertPathBetweenBusStop("Alberro", "Terminal Cotorro", 800);
        init.getCity().insertPathBetweenBusStop("Alberro", "51 y 250", 1150);
        init.getCity().insertPathBetweenBusStop("Alberro", "La Punta", 1000);

        //Test.show_shortestPathResponse(init.getCity(), 1, 10);
        Test.show_Buses(init.getCity());
        Test.show_Buses_with_route(init.getCity());
        Test.show_BusesStop(init.getCity());
        //MainFrame p = new MainFrame(init.getCity());
        //p.setVisible(true);

        Deque<BusStop>path = init.getCity().shortestPath(1,9);
        System.out.println("*****Camino mas corto*****");
        while(!path.isEmpty()){
            System.out.println(path.pop());
        }
        System.out.println("");
        System.out.println("*****Camino mas corto con ruta que debes coger o si es caminando*****");
        ArrayList<Object[]> test2 = init.getCity().shortPathResponse(1, 9);
        System.out.println(test2.get(0)[0]);
        for(Object[] o : test2){
            System.out.println(o[1]);
            System.out.println(o[2]);
        }

    }

}
