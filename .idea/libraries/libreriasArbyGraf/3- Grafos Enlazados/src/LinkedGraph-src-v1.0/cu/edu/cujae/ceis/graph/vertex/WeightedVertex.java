package cu.edu.cujae.ceis.graph.vertex;

/**
 * <h1>Vértice con peso</h1>
 */
public class WeightedVertex extends Vertex {
	
	/**
	 * Objeto que representa el peso del vértice.
	 */
	private Object weight;
	
	/**
	 * Inicia la instancia con la información del 
	 * vértice y su peso.
	 * 
	 * @param info Información del vértice.
	 * @param weight Peso del vértice.
	 */
	public WeightedVertex(Object info, Object weight) {
		super(info);
		this.weight = weight;
	}
	
	/**
	 * Obtiene el peso del vértice.
	 * @return Peso.
	 */
	public Object getWeight() {
		return weight;
	}
}
