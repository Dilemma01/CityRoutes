//import Interfaz.Principal;
import logic.BusStop;
import logic.Init;

import java.util.ArrayList;
import java.util.Deque;

public class Main {

    public static void main(String[] args) {

        Init init = new Init();
        //Principal p = new Principal();
        //p.setVisible(true);

        Deque<BusStop>path = init.getCity().shortestPath(1,10);
        System.out.println("*****Camino mas corto*****");
        while(!path.isEmpty()){
            System.out.println(path.pop());
        }
        System.out.println("");
        System.out.println("*****Camino mas corto con ruta que debes coger o si es caminando*****");
        ArrayList<Object[]> test2 = init.getCity().shortPathResponse(1, 10);
        System.out.println(test2.get(0)[0]);
        for(Object[] o : test2){
            System.out.println(o[1]);
            System.out.println(o[2]);
        }

    }

}
