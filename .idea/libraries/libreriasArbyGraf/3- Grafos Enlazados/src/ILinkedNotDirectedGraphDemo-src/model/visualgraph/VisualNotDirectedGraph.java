package model.visualgraph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import cu.edu.cujae.ceis.graph.edge.Edge;
import cu.edu.cujae.ceis.graph.interfaces.ILinkedNotDirectedGraph;
import cu.edu.cujae.ceis.graph.vertex.Vertex;

/**
 * Grafica un grafo no dirigido.
 */
public class VisualNotDirectedGraph extends JPanel {

	private static final long serialVersionUID = 4844448980727237308L;
	private ILinkedNotDirectedGraph originalGraph;
	private LinkedList<VisualVertex> verticesList;					
	private int vertexWidth  = 30;
	private int vertexHeight = 30;
	
	private int centerX;
	private int centerY;

	private int dragX;	
	private int dragY;	

	private int radio = 150;
	
	public VisualNotDirectedGraph(int centerX, int centerY) {
		super();
		verticesList = new LinkedList<VisualVertex>();
		this.setLayout(null);
		setForeground(Color.gray);
		this.centerX = centerX;
		this.centerY = centerY;
	}		

	/**
	 * Reescribimos paint para poder dibujar las aristas
	 * entre los vértices.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);	
		
		try {
			Iterator<VisualVertex> iter = verticesList.iterator();
			
			while(iter.hasNext()) {
				VisualVertex current = iter.next();
				drawVertexEdges(g, current);		
			}		
			
			String cyclic = "Cíclico: ";
			if(originalGraph.cyclicND())
				cyclic += "si";
			else
				cyclic += "no";
			
			g.drawString(cyclic, 5, getHeight()-5);
		} catch(ConcurrentModificationException exc) {
			
		}
	}
	
	private void autoPosVertices() {
		final Timer timer = new Timer();

		TimerTask task = new TimerTask() {
			private int animStep = 25;
			private int animInterp = 0;
			private int animMaxFrame = 500;			
			
			@Override
			public void run() {
				if(animInterp < animMaxFrame) {
					animInterp += animStep;
					
					/*
					 * El polinomio de la animación se calculó con el MN usando
					 * interpolación, para cambiar el radio máximo debe cambiarse
					 * el polinomio para que la animación sea coherente.
					 */
					double currentRadio = -0.001083 * Math.pow(animInterp, 2) + 0.841667 * animInterp;
					
					autoPosVertices((int) currentRadio);
					VisualNotDirectedGraph.this.repaint();
				} else {
					VisualNotDirectedGraph.this.repaint();
					autoPosVertices(radio);
					timer.cancel();
				}
			}
		};

		timer.schedule(task, 1, 1);
	}

	private void autoPosVertices(int radio) {
		Iterator<VisualVertex> iter = verticesList.iterator();
		int x;
		int y;
		int step = 0;
		double period = 2*Math.PI/verticesList.size();

		while(iter.hasNext()) {
			VisualVertex current = iter.next();
			x = centerX + (int) (radio * Math.sin(step*period));
			y = centerY + (int) (radio * Math.cos(step*period));				
			step += 1;

			current.moveTo(x, y);
		}		
	}
	
	private void addVertexToContainer(VisualVertex vertex) {
		add(vertex.getLabel());
		vertex.getLabel().setBounds(0, 0, vertexWidth, vertexHeight);
	}

	private void drawVertexEdges(Graphics g, VisualVertex vertex) {	
		VisualVertex dest;
		LinkedList<Vertex> adjacents = vertex.getAdjacents();

		for(int i=0; i<adjacents.size(); i++) {
			dest = (VisualVertex) adjacents.get(i);
			
			if(!vertex.equals(dest))
				line(g, vertex, dest);
			else {
				arc(g, vertex);
			}
		}
	}
	
	private void arc(Graphics g, VisualVertex vertex) {
		
		g.drawArc(vertex.getX()-vertex.getWidth()/2, 
					vertex.getY()-vertex.getHeight()/2, 
					vertexWidth, vertexHeight, 0, 270);
	}
	
	private void line(Graphics g, VisualVertex v1, VisualVertex v2) {
		int x1 = v1.getX() ;
		int y1 = v1.getY();
		int x2 = v2.getX() ;
		int y2 = v2.getY();
		
		if(Math.abs(v2.getY() - v1.getY()) > 40) {
			if(v2.getY() > v1.getY()) 
				y1 += v1.getHeight();			
			 else
				y2 += v2.getHeight();
		} else {
			y1 += v1.getHeight()/2;
			y2 += v2.getHeight()/2;
		}
		
		if(Math.abs(v2.getX() - v1.getX()) > 40) {
			if(v2.getX() > v1.getX()) 
				x1 += v1.getWidth();			
			 else
				x2 += v2.getWidth();
		} else {
			x1 += v1.getWidth()/2;
			x2 += v2.getWidth()/2;
		}
		
		g.drawLine(x1, y1, x2, y2);
	}

	public void setGraph(ILinkedNotDirectedGraph graph) {
		this.originalGraph = graph;
		
		Iterator<Vertex> iterVertices = originalGraph.getVerticesList().iterator();
		
		while(iterVertices.hasNext()) {
			VisualVertex current = convertToVisualVertex(iterVertices.next());
			verticesList.add(current);			
		}
		
		Iterator<VisualVertex> iterVisualVertices = verticesList.iterator();
		iterVertices = originalGraph.getVerticesList().iterator();
		
		while(iterVertices.hasNext() && iterVisualVertices.hasNext()) {
			Vertex vertex = iterVertices.next();
			VisualVertex visualVertex = iterVisualVertices.next();
			

			LinkedList<Vertex> adjacents = vertex.getAdjacents();
			Iterator<Vertex> iter = adjacents.iterator();
			
			while(iter.hasNext()) {
				Vertex adjVert = iter.next();		
				int index = originalGraph.getVerticesList().indexOf(adjVert);
				visualVertex.getEdgeList().add(new Edge(verticesList.get(index)));
			}

			visualVertex.getHeight();
			addVertexToContainer(visualVertex);
		}
		
		autoPosVertices();
		repaint();
	}

	private void removeVertexFromContainer(final VisualVertex visualVertex) {
		final int step = 4;
		final Timer timer = new Timer();

		TimerTask task = new TimerTask() {	
			private int size = vertexWidth;

			@Override
			public void run() {
				if(size > 0) {
					size -= step;

					visualVertex.getLabel().setLocation(visualVertex.getX() + step/2, 
							visualVertex.getY() + step);
					visualVertex.getLabel().setSize(size, visualVertex.getHeight());
					VisualNotDirectedGraph.this.repaint();
				} else {
					remove(visualVertex.getLabel());
					VisualNotDirectedGraph.this.repaint();
					timer.cancel();
				}
			}
		};

		timer.schedule(task, 1, 1);		
	}		

	private VisualVertex convertToVisualVertex(Vertex vertex) {
		final VisualVertex visualVertex = new VisualVertex(vertex.getInfo());
		visualVertex.setLabel(new JLabel(vertex.getInfo().toString()));
		visualVertex.getLabel().setVisible(true);	
		visualVertex.getLabel().setHorizontalTextPosition(JLabel.RIGHT);
		visualVertex.getLabel().setBorder(new LineBorder(Color.lightGray, 1, false));
		visualVertex.getLabel().setHorizontalAlignment(SwingConstants.CENTER);
		visualVertex.getLabel().setToolTipText(vertex.getInfo().toString());
		
		Color backg = new Color(255, 255, 230);
		visualVertex.getLabel().setBackground(backg);
		visualVertex.getLabel().setOpaque(true);		
		
		visualVertex.getLabel().addMouseMotionListener(new MouseMotionAdapter() {			
			@Override
			public void mouseDragged(MouseEvent e) {		
				JLabel vertexLabel = (JLabel)e.getSource();

				int x = e.getX();
				int y = e.getY();

				if(e.isControlDown()) {
					vertexLabel.setLocation(vertexLabel.getX() + (x - dragX), 
							vertexLabel.getY() + (y - dragY));
				} else {
					visualVertex.translate((x - dragX), (y - dragY));
				}

				VisualNotDirectedGraph.this.setComponentZOrder(vertexLabel, 0);
				VisualNotDirectedGraph.this.repaint();
			}
		});

		visualVertex.getLabel().addMouseListener(new MouseAdapter() {						
			@Override
			public void mousePressed(MouseEvent e) {
				dragX = e.getX();
				dragY = e.getY();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				VisualNotDirectedGraph.this.repaint();
			}
		});		

		return visualVertex;
	}

	/**
	 * Esto es necesario para el scroll del componente.
	 */
	@Override
	public Dimension getPreferredSize() {
		Dimension d = new Dimension(10000, 10000);
		return d;
	}
	
	/**
	 * Vacía el componente.
	 */
	public void clear() {
		Iterator<VisualVertex> iter = verticesList.iterator();
		
		while(iter.hasNext()) {
			remove(iter.next().getLabel());
			//removeVertexFromContainer(iter.next());
		}
		
		verticesList.clear();
		repaint();
	}
	
	private boolean posInRange(int pos) {
		return pos > -1 && pos < verticesList.size();
	}
	
	private void deleteVertex(Vertex vertex) {
		verticesList.remove(vertex);

		Iterator<VisualVertex> iter = verticesList.iterator();
		while(iter.hasNext()) {
			iter.next().deleteEdge(vertex);
		}
	}
	
	public Vertex deleteVertex(int pos) {
		VisualVertex v = null;
		if(posInRange(pos)) {
			v = verticesList.get(pos);
			deleteVertex(v);	
			removeVertexFromContainer(v);
		}
		repaint();
		return v;
	}
	
	private void selectVerticesInCascadeRecurs(VisualVertex vertex, LinkedList<VisualVertex> deleted) {
		if(!deleted.contains(vertex)) {
			VisualVertex current;
			LinkedList<Edge> edges = vertex.getEdgeList();
			Iterator<Edge> iter = edges.iterator();					
			deleted.add(vertex);
			
			while(iter.hasNext()) {
				current = (VisualVertex) iter.next().getVertex();			
				selectVerticesInCascadeRecurs(current, deleted);
			}		
		}
	}
	
	private int getVertexIndex(Vertex vertex) {
		int count = 0;
		int index = -1;
		Iterator<VisualVertex> iter = verticesList.iterator();

		while(index == -1 && iter.hasNext()) {
			if(iter.next().equals(vertex))
				index = count;
			count++;
		}

		return index;
	}
	
	public LinkedList<VisualVertex> deleteVertexCascade(int pos) {

		LinkedList<VisualVertex> deleted = new LinkedList<VisualVertex>();		

		if(posInRange(pos)) {
			selectVerticesInCascadeRecurs(verticesList.get(pos), deleted);			
			final Iterator<VisualVertex> iter = deleted.iterator();

			final Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				
				@Override
				public void run() {
					if(iter.hasNext()) {
						VisualVertex v = iter.next();
						deleteVertex(getVertexIndex(v));

						removeVertexFromContainer(v);
						VisualNotDirectedGraph.this.repaint();
					} else {
						timer.cancel();
					}
				}
			};
			timer.schedule(task, 1, 100);
			
			
			/*while(iter.hasNext()) {
				VisualVertex v = iter.next();
				deleteVertex(getVertexIndex(v));

				removeVertexFromContainer(v);
				VisualDirectedGraph.this.repaint();
			}*/
		}
		repaint();
		return deleted;
	}	
		
	public LinkedList<VisualVertex> removeDisconnectVertices() {
		LinkedList<VisualVertex> verts = new LinkedList<VisualVertex>();					
		
		for(int i=0; i<verticesList.size();) {
			VisualVertex v = verticesList.get(i);
			if(v.getEdgeList().size() == 0) {
				verts.add(v);
				verticesList.remove(i);
				removeVertexFromContainer(v);
			} else
				i++;
		}
		repaint();
		return verts;
	}
	
	public boolean deleteEdgeND(int posTail, int posHead) {
		boolean success = false;
		
		if(posInRange(posTail) && posInRange(posHead)) {
			success  = deleteEdgeD(posTail, posHead);
			success &= deleteEdgeD(posHead, posTail);
		}
		repaint();
		return success;
	}
	
	private boolean deleteEdgeD(int posTail, int posHead) {
		boolean success = false;
		
		if(posInRange(posTail) && posInRange(posHead)) {
			Vertex tail = verticesList.get(posTail);
			Vertex head = verticesList.get(posHead);			
			success = tail.deleteEdge(head);
		}
		
		return success;
	}
}
