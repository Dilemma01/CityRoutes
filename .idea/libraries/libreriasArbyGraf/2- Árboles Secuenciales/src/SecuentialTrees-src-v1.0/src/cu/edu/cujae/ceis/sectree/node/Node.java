package src.cu.edu.cujae.ceis.sectree.node;

import java.io.Serializable;

/**
 * <h1>Nodo</h1>
 * Nodo abstracto de donde deben heredar todos los nodos para
 * árboles secuenciales.
 *
 * @param <E>
 */
public abstract class Node<E> implements Serializable {
	private static final long serialVersionUID = -6457222155588259273L;
	
	/**
	 * Información del nodo definida por el usuario.
	 */
	protected E info;
	
	public Node() {	
	}
	
	public Node(E info) {
		this.info = info;
	}

	/**
	 * Devuelve la información del nodo.
	 * @return Información del nodo.
	 */
	public E getInfo() {
		return info;
	}

	/**
	 * Indica la información del nodo.
	 * @param info Información del nodo.
	 */
	public void setInfo(E info) {
		this.info = info;
	}
	
	@Override
	public String toString() {
		return info.toString();
	}
}
