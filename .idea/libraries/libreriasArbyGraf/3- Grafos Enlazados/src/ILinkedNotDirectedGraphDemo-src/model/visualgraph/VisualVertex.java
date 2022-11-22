package model.visualgraph;

import javax.swing.JLabel;

import cu.edu.cujae.ceis.graph.vertex.Vertex;

/**
 * Representación visual de un vértice.
 * 
 * @author Joaquín Pinillos IF-24 Curso 2013-2014
 */
public class VisualVertex extends Vertex {
	
	private JLabel label;

	public VisualVertex(Object info) {
		super(info);
	}

	public JLabel getLabel() {
		return label;
	}
	
	public void setLabel(JLabel label) {
		this.label = label;
	}
	
	public int getWidth() {
		return (null != label)?label.getWidth():0;
	}
	
	public int getHeight() {
		return (null != label)?label.getHeight():0;
	}

	public int getX() {
		return (null != label)?label.getX():0;
	}
	
	public int getY() {
		return (null != label)?label.getY():0;
	}
	
	public String getName() {
		return (null != label)?label.getText():"";
	}
	

	public void translate(int x, int y) {
		if(label != null)
			label.setLocation(label.getX() + x, label.getY() + y);		
	}
	
	public void moveTo(int x, int y) {
		if(label != null)
			label.setLocation(x, y);		
	}
}
