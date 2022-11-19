//import Interfaz.Principal;
import logic.BusStop;
import logic.Init;

import java.util.Deque;

public class Main {

    public static void main(String[] args) {

        Init init = new Init();
        //Principal p = new Principal();
        //p.setVisible(true);

        Deque<BusStop>path = init.getCity().shortestPath(1,10);
        while(!path.isEmpty()){
            System.out.println(path.pop());
        }

    }

}
