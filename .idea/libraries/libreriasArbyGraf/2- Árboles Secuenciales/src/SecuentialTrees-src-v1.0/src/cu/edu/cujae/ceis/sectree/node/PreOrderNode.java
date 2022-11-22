package src.cu.edu.cujae.ceis.sectree.node;

/**
 * <h1>Nodo pre orden</h1>
 *
 * @param <E> Tipo de dato de la informaci�n del nodo
 */
public class PreOrderNode<E> extends Node<E> {
	private static final long serialVersionUID = 4778937936925794932L;
	
	/**
	 * �ndice del enlace derecho.
	 */
	private int rigthLink;
	
	/**
	 * Terminal.
	 */
	private boolean term;

	public PreOrderNode(){
		super.setInfo(null);
		rigthLink = -2;
		term=false;
	}

	public PreOrderNode(E info, int righrigthLink , boolean term){
		this.rigthLink=righrigthLink;
		this.term=term;
		super.setInfo(info);
	}

	/**
	 * Indica el �ndice del enlace derecho.
	 * @param rigthLink �ndice del enlace derecho.
	 */
	public void setRigthLink(int rigthLink) {
		this.rigthLink = rigthLink;
	}
	
	/**
	 * Devuelve el �ndice del enlace derecho.
	 * @return �ndice del enlace derecho.
	 */
	public int getRigthLink() {
		return rigthLink;
	}
	
	/**
	 * Indica si es terminal.
	 * @param term terminal
	 */
	public void setTerm(boolean term) {
		this.term = term;
	}
	
	/**
	 * Devuelve si es terminal.
	 * @return True si es terminal, false si no.
	 */
	public boolean isTerm() {
		return term;
	}
}
