package cu.edu.cujae.ceis.graph.edge;

import cu.edu.cujae.ceis.graph.vertex.Vertex;

/**
 * <h1>Arista elemental</h1>
 */
public class Edge {
	
	/**
	 * Vértice asociado a la arista.
	 */
	protected Vertex vertex;

	/**
	 * Inicia la instancia con el vértice
	 * asociado a la arista.
	 * 
	 * @param vertex vértice.
	 */
	public Edge(Vertex vertex) {
		this.vertex = vertex;
	}
	
	/**
	 * Devuelve el vértice asociado
	 * a esta arista.
	 * 
	 * @return vértice.
	 */
	public Vertex getVertex() {
		return vertex;
	}
	
	/**
	 * Indica el vértice asociado
	 * con esta arista.
	 * 
	 * @param vertex vértice.
	 */
	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}
}
