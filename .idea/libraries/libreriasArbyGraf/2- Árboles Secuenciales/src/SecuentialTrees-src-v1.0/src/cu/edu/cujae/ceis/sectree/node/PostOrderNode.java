package src.cu.edu.cujae.ceis.sectree.node;

/**
 * <h1>Nodo post orden</h1>
 *
 * @param <E> Tipo de dato de la información del nodo
 */
public class PostOrderNode<E> extends Node<E> {
	private static final long serialVersionUID = 4778937936925794932L;
	
	/**
	 * Grado.
	 */
	private int grade;
	
	public PostOrderNode() {
		super();
		setGrade(0);
	}
	
	public PostOrderNode(E info, int degree) {
		super(info);
		setGrade(degree);
	}
	
	/**
	 * Devuelve el grado del nodo.
	 * @return Grado del nodo.
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * Indica el grado del nodo.
	 * @param grade Grado del nodo.
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	@Override
	public String toString() {
		String text = "|" + info.toString() + "|" + String.valueOf(grade) + "|";
		return text;
	}



}
