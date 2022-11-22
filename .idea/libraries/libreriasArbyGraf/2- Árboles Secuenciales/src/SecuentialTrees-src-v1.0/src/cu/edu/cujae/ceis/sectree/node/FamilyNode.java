package src.cu.edu.cujae.ceis.sectree.node;

/**
 * <h1>Nodo familiar</h1>
 *
 * @param <E> Tipo de dato de la informaci�n del nodo
 */
public class FamilyNode<E> extends Node<E> {
	private static final long serialVersionUID = 4444638403021786727L;
	
	/**
	 * �ndice del enlace izquierdo.
	 */
	private int leftLink;
	
	/**
	 * �ltimo hermano.
	 */
	private boolean fam;
	
	public FamilyNode() {
		super();
		leftLink = -1;
	}

	public FamilyNode(E info, int leftLink, boolean fam) {
		super(info);
		this.leftLink = leftLink;
		this.setFam(fam);		
	}
	
	/**
	 * Devuelve el �ndice del enlace izquierdo.
	 * @return �ndice del enlace izquierdo.
	 */
	public int getLeftLink() {
		return leftLink;
	}

	/**
	 * Indica el �ndice del enlace izquierdo.
	 * @param leftLink �ndice del enlace izquierdo.
	 */
	public void setLeftLink(int leftLink) {
		this.leftLink = leftLink;
	}
	
	/**
	 * Chequea si es �ltimo hermano.
	 * @return True si es el �ltimo hermano, false si no.
	 */
	public boolean isFam() {
		return fam;
	}

	/**
	 * Indica si es �ltimo hermano.
	 * @param fam es �ltimo hermano.
	 */
	public void setFam(boolean fam) {
		this.fam = fam;
	}
	
	@Override
	public String toString() {
		String text = "|" + info.toString() + "|" + String.valueOf(leftLink) + "|";
		if(isFam())
			text += "T|";
		return text;
	}


}
