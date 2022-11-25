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
public class TableRouteList extends DefaultTableModel
{
    
    public TableRouteList(LinkedList<Vertex> lista) {

        String[] columnNames = {"Listado de Paradas"};
        this.setColumnIdentifiers(columnNames);
        
        System.out.println(lista.size());

        Iterator<Vertex> iter = lista.iterator();
        
        while(iter.hasNext()) {
            Vertex v = iter.next();
            
            Object[] newRow = new Object[]{((BusStop)v.getInfo()).getName()};
           
            addRow(newRow);
          
        }

        
    }
    
}
