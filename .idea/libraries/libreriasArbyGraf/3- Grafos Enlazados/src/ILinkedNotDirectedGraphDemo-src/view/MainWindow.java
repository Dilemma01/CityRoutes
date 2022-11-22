package view;

import java.awt.EventQueue;

import javax.swing.JFrame;

import cu.edu.cujae.ceis.graph.LinkedGraph;
import cu.edu.cujae.ceis.graph.interfaces.ILinkedNotDirectedGraph;
import cu.edu.cujae.ceis.graph.vertex.Vertex;
import model.visualgraph.VisualNotDirectedGraph;

import java.awt.BorderLayout;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.ListSelectionModel;

public class MainWindow {

	private JFrame frmMain;
	ILinkedNotDirectedGraph graph;
	private VisualNotDirectedGraph visualGraph;
	private JTable table;
	private DefaultTableModel tableVertModel;
	private DefaultTableModel tableEdgesModel;
	private JMenuItem mntmDeleteDisconnected;
	private JMenuItem mntmDeleteCascade;
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}		
	
	public MainWindow() {
		initialize();
	}
	
	private void initialize() {
		frmMain = new JFrame();
		frmMain.setTitle(Messages.getString("MainWindow.Title"));
		frmMain.setResizable(false);
		frmMain.setBounds(100, 100, 800, 570);
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMain.setLocationRelativeTo(null);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frmMain.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		visualGraph = new VisualNotDirectedGraph(frmMain.getWidth()/2, frmMain.getHeight()/2-120);
		splitPane.setLeftComponent(visualGraph);
		splitPane.setDividerLocation(frmMain.getHeight()-200);
		
		createRandomGraph();
		visualGraph.setGraph(graph);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);
		
		table = new JTable() {
			private static final long serialVersionUID = -8826733137099010616L;

			@Override
			public boolean isCellEditable(int row, int column) {                
				return false;               
			};
		};
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		tableVertModel = new DefaultTableModel();
		tableVertModel.addColumn(Messages.getString("MainWindow.Vertex")); 
		tableVertModel.addColumn(Messages.getString("MainWindow.Grade")); 
		tableVertModel.addColumn(Messages.getString("MainWindow.Adjacents")); 
		table.setModel(tableVertModel);
		
		tableEdgesModel = new DefaultTableModel();
		tableEdgesModel.addColumn(Messages.getString("MainWindow.Edges"));
		
		createMainMenu();
		createPopupMenu();						
		refreshVerticesTable();
	}
	
	/**
	 * Crea un grafo aleatorio.
	 */
	private void createRandomGraph() {
		graph = new LinkedGraph();
		
		Random rand = new Random();
		String alphabet = Messages.getString("MainWindow.Alphabet"); 
		int size = rand.nextInt(alphabet.length()-5)+5;			
		int edgesCount = rand.nextInt(size*2)+5;
				
		for(int i=0; i<size; i++) {
			graph.insertVertex(alphabet.charAt(i));
		}
								
		for(int i=0; i<edgesCount; i++) {
			int a = rand.nextInt(size);
			int b = rand.nextInt(size);
			
			if(!graph.areAdjacents(a, b))
				graph.insertEdgeNDG(a, b);
		}
	}
	
	/**
	 * Obtiene el índice de un vértice a partir
	 * de su información.
	 * @param value información.
	 * @return índice del vértice en el grafo.
	 */
	private int getVertexIndexForValue(Object value) {
		int index = 0;
		int result = -1;		
		Iterator<Vertex> iter = graph.getVerticesList().iterator();
		
		while(result == -1 && iter.hasNext()) {
			Vertex v = iter.next();
			
			if(v.getInfo().equals(value)) {
				result = index;
			}
			index++;
		}
		
		return result;
	}
	
	/**
	 * Elimina una arista seleccionada de la tabla.
	 * @param index índice de la fila seleccionada.
	 */
	private void deleteSelectedEdge(int index) {
		String edge = (String) tableEdgesModel.getValueAt(index, 0);
		char info1 = edge.charAt(0);
		char info2 = edge.charAt(4);
		int index1 = getVertexIndexForValue(info1);
		int index2 = getVertexIndexForValue(info2);
		
		graph.deleteEdgeND(index1, index2);	
		visualGraph.deleteEdgeND(index1, index2);
	}
	
	/**
	 * Crea el menú contextual de la tabla.
	 */
	private void createPopupMenu() {
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
				checkPopupMenuVisibility();
			}
		});
		addPopup(table, popupMenu);
		
		JMenuItem mntmDelete = new JMenuItem(Messages.getString("MainWindow.Delete")); 
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				delete();
			}
		});
		popupMenu.add(mntmDelete);
		
		mntmDeleteCascade = new JMenuItem(Messages.getString("MainWindow.DeleteInCascade")); 
		mntmDeleteCascade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteCascade();
			}
		});
		popupMenu.add(mntmDeleteCascade);
		
		mntmDeleteDisconnected = new JMenuItem(Messages.getString("MainWindow.DeleteDisconnecteds")); 
		mntmDeleteDisconnected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteDisconnecteds();
			}
		});
		popupMenu.add(mntmDeleteDisconnected);
	}
	
	/**
	 * Crea el menú principal.
	 */
	private void createMainMenu() {
		JMenuBar menuBar = new JMenuBar();
		frmMain.setJMenuBar(menuBar);
		
		JMenu mnGrafo = new JMenu(Messages.getString("MainWindow.Graph")); 
		menuBar.add(mnGrafo);
		
		JMenuItem mntmRandomize = new JMenuItem(Messages.getString("MainWindow.Randomize")); 
		mntmRandomize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				visualGraph.clear();
				createRandomGraph();
				visualGraph.setGraph(graph);
				refreshVerticesTable();
				refreshEdgesTable();
			}
		});
		mnGrafo.add(mntmRandomize);
		
		JMenu mnView = new JMenu(Messages.getString("MainWindow.View")); 
		menuBar.add(mnView);
		
		JMenuItem mntmVrtices = new JMenuItem(Messages.getString("MainWindow.Vertices"));
		mntmVrtices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table.setModel(tableVertModel);
				refreshVerticesTable();
			}
		});
		mnView.add(mntmVrtices);
		
		JMenuItem mntmEdges = new JMenuItem(Messages.getString("MainWindow.Edges"));
		mntmEdges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table.setModel(tableEdgesModel);
				refreshEdgesTable();
			}
		});
		mnView.add(mntmEdges);
	}	
	
	/**
	 * Refresca el modelo de los vértices.
	 */
	private void refreshVerticesTable() {
		while(tableVertModel.getRowCount() > 0)
			tableVertModel.removeRow(0);
			
		Iterator<Vertex> iter = graph.getVerticesList().iterator();
		int index = 0;
		
		while(iter.hasNext()) {
			Vertex v = iter.next();
			Vector<String> row = new Vector<String>();
			row.add(v.getInfo().toString());
			row.add(String.valueOf(graph.degreeND(index)));
			row.add(getAdjacentsAsString(v));
			
			tableVertModel.addRow(row);
			
			index++;
		}
	}
	
	/**
	 * Refresca el modelo de las aristas.
	 */
	private void refreshEdgesTable() {
		while(tableEdgesModel.getRowCount() > 0)
			tableEdgesModel.removeRow(0);
			
		LinkedList<String> rows = new LinkedList<String>();
		Iterator<Vertex> iter = graph.getVerticesList().iterator();
		
		while(iter.hasNext()) {
			Vertex v = iter.next();
			
			LinkedList<Vertex> adjs = v.getAdjacents();
			Iterator<Vertex> iterAdjs = adjs.iterator();
			
			while(iterAdjs.hasNext()) {
				Vertex v2 = iterAdjs.next();
				String edge  = v.getInfo().toString() + " - " + v2.getInfo().toString();
				String edge2 = v2.getInfo().toString() + " - " + v.getInfo().toString();
				
				if(!rows.contains(edge) && !rows.contains(edge2)) {
					Vector<String> row = new Vector<String>();
					row.add(edge);
					tableEdgesModel.addRow(row);
					rows.add(edge);
				}
				
			}
		}
	}
	
	/**
	 * Obtiene la lista de adyacentes a un vértice como
	 * una cadena de caracteres con elementos separados por
	 * coma.
	 * 
	 * @param v vértice.
	 * @return cadena de caracteres.
	 */
	private String getAdjacentsAsString(Vertex v) {
		String adjs = ""; 
		LinkedList<Vertex> list = v.getAdjacents();
		Iterator<Vertex> iter = list.iterator();
		
		while(iter.hasNext()) {
			adjs += iter.next().getInfo().toString();
			
			if(iter.hasNext())
				adjs += ", "; 
		}
		
		return adjs;
	}
	
	/**
	 * Vertifica y controla la visibilidad de los elementos
	 * del menú contextual según el modelo seleccionado para
	 * la tabla.
	 */
	private void checkPopupMenuVisibility() {
		if(table.getModel().equals(tableVertModel)) {
			mntmDeleteCascade.setVisible(true);
			mntmDeleteDisconnected.setVisible(true);
		} else {
			mntmDeleteCascade.setVisible(false);
			mntmDeleteDisconnected.setVisible(false);
		}
	}
	
	/**
	 * Elimina los nodos desconectados de 
	 * la estructura de datos y del componente
	 * visual.
	 */
	private void deleteDisconnecteds() {
		graph.removeDisconnectVerticesND();
		visualGraph.removeDisconnectVertices();
		refreshVerticesTable();
	}
	
	/**
	 * Elimina un vértice o una arista dependiendo
	 * del modelo activo en la tabla.
	 */
	private void delete() {
		int index = table.getSelectedRow();
		if(-1 != index) {
			if(table.getModel().equals(tableVertModel)) {
				graph.deleteVertex(index);
				visualGraph.deleteVertex(index);
				refreshVerticesTable();						
			} else {
				deleteSelectedEdge(index);
				refreshEdgesTable();
			}
		}
	}
	
	/**
	 * Elimina en cascada un vértice de la
	 * estructura de datos y del componente
	 * visual.
	 */
	private void deleteCascade() {
		int index = table.getSelectedRow();
		if(-1 != index) {
			graph.deleteVertexCascade(index);
			visualGraph.deleteVertexCascade(index);
			refreshVerticesTable();
		}
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
