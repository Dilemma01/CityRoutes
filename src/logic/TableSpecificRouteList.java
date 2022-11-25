/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import cu.edu.cujae.ceis.graph.vertex.Vertex;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC-2018
 */
public class TableSpecificRouteList  extends DefaultTableModel {
    
    Bus aux;
    private boolean result;
        public TableSpecificRouteList(City city, LinkedList<Vertex> lista, String i) {
        result = false;
        String[] columnNames = {"Listado de Paradas"};
        this.setColumnIdentifiers(columnNames);
        
        LinkedList<Bus> listaBus = city.getBusList();
        Iterator<Bus> iterator = listaBus.iterator();
        while(iterator.hasNext() && !result){
             aux = iterator.next();
            if(aux.getId().equalsIgnoreCase(i)){
                result = true;
            }
        }
        LinkedList<BusStop> listaParadas = aux.getRoute();
        
        Iterator<BusStop> iter = listaParadas.iterator();
        
        while(iter.hasNext()) {
            BusStop v = iter.next();
            
            Object[] newRow = new Object[]{v.getName()};
           
            addRow(newRow);
          
        }

        
    }
}
