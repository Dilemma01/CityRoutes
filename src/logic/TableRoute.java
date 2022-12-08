/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC-2018
 */
public class TableRoute extends DefaultTableModel {

    
    public TableRoute(LinkedList<Bus> lista) {

        String[] columnNames = {"Guagua", "Ruta Inicio", "Ruta Final"};
        this.setColumnIdentifiers(columnNames);
        
            System.out.println(lista.size());

        for (int i = 0; i < lista.size(); i++) {
            
            
            Object[] newRow = new Object[]{lista.get(i).getId(), ((Bus) lista.get(i)).getRoute().get(0).getName(), 
            ((Bus) lista.get(i)).getRoute().get(((Bus) lista.get(i)).getRoute().size()-1).getName()};
           
            addRow(newRow);
          
        }

        
    }
    
    
    
}
