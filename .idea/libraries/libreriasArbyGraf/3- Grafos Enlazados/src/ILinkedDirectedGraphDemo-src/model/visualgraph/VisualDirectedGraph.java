package model.visualgraph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import cu.edu.cujae.ceis.graph.interfaces.ILinkedDirectedGraph;
import cu.edu.cujae.ceis.graph.vertex.Vertex;

/**
 * Grafica un grafo dirigido.
 */
public class VisualDirectedGraph extends JPanel {

	private static final long serialVersionUID = 4844448980727237308L;
	private ILinkedDirectedGraph originalGraph;
	private LinkedList<VisualVertex> verticesList;					
	private int vertexWidth  = 30;
	private int vertexHeight = 30;

	private int centerX;
	private int centerY;

	private int dragX;	
	private int dragY;	

	private int radio = 150;

	public VisualDirectedGraph(int centerX, int centerY) {
		super();
		verticesList = new LinkedList<VisualVertex>();
		this.setLayout(null);
		setForeground(Color.gray);
		this.centerX = centerX;
		this.centerY = centerY;
	}		

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
			if(originalGraph.cyclicDG())
				cyclic += "si";
			else
				cyclic += "no";
	
			g.drawString(cyclic, 5, getHeight()-5);
		} catch (ConcurrentModificationException exc) {
			
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
					VisualDirectedGraph.this.repaint();
				} else {
					VisualDirectedGraph.this.repaint();
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

	/**
	 * 
	 * @param g
	 * @param vertex
	 */
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
		g.fillArc(vertex.getX()+vertex.getWidth()/2-3, vertex.getY()-3, 7, 7, 0, 360);
	}

	private double getLineAngle(int x1, int x2, int y1, int y2) {
		double angle = 0.0d;

		double hip = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
		double catX = x1 - x2;
		double catY = y1 - y2;
		double sin = catY/hip;
		double cos = catX/hip;				

		double arcsin = Math.toDegrees(Math.asin(sin));
		double arccos = Math.toDegrees(Math.acos(cos));

		if (arcsin >= 0 )
			angle = arcsin;
		else
			angle = 360-arccos;

		return angle;
	}

	private void drawHead(Graphics g, int x1, int y1, int x2, int y2, int angle) {
		Graphics2D g2 = (Graphics2D) g;
		int size = 7;
		g2.fillArc(x2-size/2, y2-size/2, size, size, 0, 360);
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
		drawHead(g, x1, y1, x2, y2, (int) getLineAngle(x1, x2, y1, y2));
	}

	public void setGraph(ILinkedDirectedGraph graph) {
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
					VisualDirectedGraph.this.repaint();
				} else {
					remove(visualVertex.getLabel());
					VisualDirectedGraph.this.repaint();
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

				VisualDirectedGraph.this.setComponentZOrder(vertexLabel, 0);
				VisualDirectedGraph.this.repaint();
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
				VisualDirectedGraph.this.repaint();
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
			//removeVertexFromContainer();
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
						VisualDirectedGraph.this.repaint();
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
			if(degreeDG(i) == 0) {
				VisualVertex v = verticesList.get(i);
				verts.add(v);
				verticesList.remove(i);
				removeVertexFromContainer(v);
			} else
				i++;
		}
		repaint();
		return verts;
	}

	private int degreeDG(int pos) {
		int degree = -1;
		if(posInRange(pos)) {
			degree = inDegreeDG(pos) + outDegree(pos);
		}
		return degree;
	}

	private int inDegreeDG(int pos) {
		int degree = -1;

		if(posInRange(pos)) {
			degree = 0;
			Vertex vertex = verticesList.get(pos);
			Iterator<VisualVertex> iter = verticesList.iterator();

			while(iter.hasNext()) {
				if(iter.next().isAdjacent(vertex))
					degree++;
			}
		}
		return degree;
	}

	private int outDegree(int pos) {
		int degree = -1;

		if(posInRange(pos))
			degree = verticesList.get(pos).getAdjacents().size();

		return degree;
	}	

	public boolean deleteEdgeD(int posTail, int posHead) {
		boolean success = false;

		if(posInRange(posTail) && posInRange(posHead)) {
			Vertex tail = verticesList.get(posTail);
			Vertex head = verticesList.get(posHead);			
			success = tail.deleteEdge(head);
		}
		repaint();
		return success;
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
}
