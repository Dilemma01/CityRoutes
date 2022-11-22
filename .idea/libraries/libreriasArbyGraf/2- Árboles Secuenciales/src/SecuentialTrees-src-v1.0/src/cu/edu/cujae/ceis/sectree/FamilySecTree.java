package src.cu.edu.cujae.ceis.sectree;

import java.util.ArrayList;
import java.util.HashMap;

import src.cu.edu.cujae.ceis.sectree.node.FamilyNode;
import src.cu.edu.cujae.ceis.sectree.node.Node;
import cu.edu.cujae.ceis.tree.binary.BinaryTreeNode;
import cu.edu.cujae.ceis.tree.general.GeneralTree;

/**
 * <h1>Árbol secuencial de orden familiar</h1>
 * @author Joaquín Pinillos IF-24 Curso 2013-2014
 *
 * @param <E> Tipo de dato del árbol
 */
public class FamilySecTree<E> extends SecTree<E> {
	
	private final int SEC_FAMILIAR_TREE_FILE_SIGNATURE = 2;
	private int loadedNodes;
	
	public FamilySecTree() {
		super(true);
	}
	
	public FamilySecTree(GeneralTree<E> tree) {
		super(true);
		loadedNodes = 0;
		
		HashMap<BinaryTreeNode<E>, Integer> hashMap;
		hashMap = new HashMap<BinaryTreeNode<E>, Integer>();	
		insertNode((BinaryTreeNode<E>) tree.getRoot(), hashMap);
	}
	
	/**
	 * Insertamos a un nodo con todos sus hijos 
	 * en postorden invertido (RDI).
	 * 
	 * @param root Nodo.
	 * @param hashMap HashMap (NodoIzquierdo -> Posición en el árbol secuencial 
	 * 							del nodo que necesita ese enlace izquierdo).
	 */
	private void insertNode(BinaryTreeNode<E> root, HashMap<BinaryTreeNode<E>, Integer> hashMap) {
		Node<E> secNode = new FamilyNode<E>(root.getInfo(), 
				-1,
				root.getRight() == null);
		
		secList.add(secNode);

		/*
		 * Almacenamos en el HashMap:
		 * NodoIzquierdo -> Posición en el árbol secuencial del nodo que necesita ese enlace izquierdo.
		 */
		if(root.getLeft() != null) 
			hashMap.put(root.getLeft(), loadedNodes);
		
		/*
		 * Luego cuando más adelante nos corresponde insertar
		 * al que es izquierdo de otro ya insertado anteriormente, con el hashmap 
		 * podemos saber de quién este índice es izquierdo y accedemos en el 
		 * arreglo la posición donde corresponde asignar el enlace.
		 * 
		 * Con una sola iteración sobre el árbol original los
		 * enlaces quedan asignados sin necesidad de recorrer luego
		 * la lista del árbol secuencial. 
		 */
		if(hashMap.containsKey(root)) //si soy el enlace izquierdo de alguien 
			((FamilyNode<E>) secList.get(hashMap.get(root))).setLeftLink(loadedNodes); //me acaban de crear, este es mi índice.

		loadedNodes++;
		
		if(root.getRight() != null)
			insertNode(root.getRight(), hashMap);
		
		if(root.getLeft() != null)
			insertNode(root.getLeft(), hashMap);
		
	}			
	
	/**
	 * Indica si un nodo es hijo de otro. Recorre todos los hijos
	 * del nodo padre hasta encontrar el índice del hijo que buscamos o
	 * hasta que sobrepasamos ese índice.
	 * 
	 * @param childIndex Índice del nodo hijo en la lista secuencial.
	 * @param parentIndex Índice del nodo padre en la lista secuencial.
	 * @return True si es nodo hijo, false si no.
	 */
	private boolean sonOf(int childIndex, int parentIndex) {
		boolean isChild = false;		
		int nextNode = ((FamilyNode<E>) secList.get(parentIndex)).getLeftLink();
		
		if(-1 != nextNode) {
			if(nextNode != childIndex) {			
				while(!isChild && nextNode < childIndex && !((FamilyNode<E>) secList.get(nextNode)).isFam()) {
					nextNode++;
					
					if(nextNode == childIndex)
						isChild = true;
				}
			} else 
				isChild = true;
		}
		return isChild;
	}
	
	
	@Override
	public ArrayList<Integer> sons(int pos) {
		ArrayList<Integer> childs = new ArrayList<Integer>();		
		boolean done = false;
		
		if(pos > -1 && pos < secList.size()-1) {
			int nextNode = ((FamilyNode<E>) secList.get(pos)).getLeftLink();
			
			if(-1 != nextNode) {
				while(!done) {
					childs.add(nextNode);				
					done = ((FamilyNode<E>) secList.get(nextNode)).isFam();
					nextNode++;
				}
			}
		}
		
		return childs;
	}
	
	@Override
	public int father(int pos) {
		int parent = -1;
		
		if(pos > 0 && pos < secList.size()) {
			boolean found = false;
			int i = 0;
			
			while(!found && i < pos) {
				
				if(sonOf(pos, i)) {
					found = true;
					parent = i;
				}
					
				i++;
			}
		}
		
		return parent;
	}
	
	@Override
	public ArrayList<Integer> brothers(int pos) {
		ArrayList<Integer> siblings = new ArrayList<Integer>();
		
		int parent = father(pos);
		if(-1 != parent) {
			siblings = sons(parent);
			siblings.remove(new Integer(pos));
		}
		
		return siblings;
	}			
	
	@Override
	protected int getFileSignature() {
		return SEC_FAMILIAR_TREE_FILE_SIGNATURE;
	}
}
