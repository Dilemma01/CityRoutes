import gui.MainFrame;
import logic.BusStop;
import logic.Init;

import java.util.ArrayList;
import java.util.Deque;

public class Main {

    public static void main(String[] args) {

        Init init = new Init();

        init.getCity().deleteBusStop("Pruebas");
        init.getCity().deleteBusStop("La Rosita");

        //Test.show_shortestPathResponse(init.getCity(), 1, 10);
        Test.show_Buses(init.getCity());
        Test.show_Buses_with_route(init.getCity());
        Test.show_BusesStop(init.getCity());
        //MainFrame p = new MainFrame(init.getCity());
        //p.setVisible(true);

        Deque<BusStop>path = init.getCity().shortestPath(1,21);
        System.out.println("*****Camino mas corto*****");
        while(!path.isEmpty()){
            System.out.println(path.pop());
        }
        System.out.println("");
        System.out.println("*****Camino mas corto con ruta que debes coger o si es caminando*****");
        ArrayList<Object[]> test2 = init.getCity().shortPathResponse(1, 21);
        System.out.println(test2.get(0)[0]);
        for(Object[] o : test2){
            System.out.println(o[1]);
            System.out.println(o[2]);
        }

    }

}
