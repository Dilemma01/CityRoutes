package cu.edu.cujae.ceis.graph.vertex;

/**
 * <h1>V�rtice con peso</h1>
 */
public class WeightedVertex extends Vertex {
	
	/**
	 * Objeto que representa el peso del v�rtice.
	 */
	private Object weight;
	
	/**
	 * Inicia la instancia con la informaci�n del 
	 * v�rtice y su peso.
	 * 
	 * @param info Informaci�n del v�rtice.
	 * @param weight Peso del v�rtice.
	 */
	public WeightedVertex(Object info, Object weight) {
		super(info);
		this.weight = weight;
	}
	
	/**
	 * Obtiene el peso del v�rtice.
	 * @return Peso.
	 */
	public Object getWeight() {
		return weight;
	}
}
