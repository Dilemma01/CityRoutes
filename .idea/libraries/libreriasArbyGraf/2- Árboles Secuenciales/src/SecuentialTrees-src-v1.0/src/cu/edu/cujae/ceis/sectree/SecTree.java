package src.cu.edu.cujae.ceis.sectree;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import src.cu.edu.cujae.ceis.sectree.node.Node;

import cu.edu.cujae.ceis.tree.binary.BinaryTreeNode;
import cu.edu.cujae.ceis.tree.general.GeneralTree;

/**
 * <h1>Árbol secuencial</h1> 
 * Árbol secuencial abstracto de donde deben heredar todos los árboles
 * secuenciales.
 *
 * @param <E> Tipo de datos del árbol secuencial.
 */
public abstract class SecTree<E> {
	
	protected ArrayList<Node<E>> secList;
	protected boolean firstIsRoot;
	
	/**
	 * Código de operación para la carga de ficheros
	 */
	public enum LoadOpResult { success, signatureError, error };
	
	/**
	 * Inserta un nodo de la lista secuencial en un árbol general
	 * junto con todos sus descendientes. Esta función recursiva
	 * se utiliza para generar un GeneralTree a partir de un árbol
	 * secuencial cargado desde un archivo.
	 * 
	 * @param tree Árbol general.
	 * @param childIndex Índice del nodo en la lista secuencial.
	 * @param parentNode Nodo padre dentro del árbol.
	 */
	private void insertNodeInGTree(GeneralTree<E> tree, int childIndex, BinaryTreeNode<E> parentNode) {
		BinaryTreeNode<E> childNode = new BinaryTreeNode<E>(getNode(childIndex).getInfo());		
		tree.insertNode(childNode, parentNode);
		
		List<Integer> childs = sons(childIndex);
		Iterator<Integer> iter = childs.iterator();
		
		while(iter.hasNext()) 
			insertNodeInGTree(tree, iter.next(), childNode);		
		
	}
	
	public SecTree(boolean firstIsRoot) {
		secList = new ArrayList<Node<E>>();
		this.firstIsRoot = firstIsRoot;
	}

	/**
	 * Devuelve el padre de un nodo.
	 * 
	 * @param pos Índice del nodo hermano en la lista secuencial.
	 * @return Lista de índices de sus hermanos.
	 */
	public ArrayList<Integer> brothers(int pos) {
		return null;
	}

	/**
	 * Devuelve el padre de un nodo.
	 * 
	 * @param pos Índice del nodo hijo en la lista secuencial.
	 * @return Índice del nodo padre si existe, de lo contrario -1.
	 */
	public int father(int pos) {
		return -1;
	}

	/**
	 * Devuelve todos los hijos de un nodo.
	 * 
	 * @param pos Índice del nodo en la lista secuencial.
	 * @return Lista de índices de sus hijos.
	 */
	public ArrayList<Integer> sons(int pos) {
		return null;
	}

	/**
	 * Devuelve un nodo a partir de su índice previa validación.
	 * @param index Índice del nodo en la lista secuencial.
	 * @return Nodo.
	 */
	protected Node<E> getNode(int index) {
		Node<E> n = null;
		if(index > -1 && index < secList.size())
			n = secList.get(index);
		
		return n;
	}		
	
	/**
	 * Guarda la estructura en un archivo.
	 * 
	 * @return True si se realizó la operación satisfactoria,
	 * 			False si no.
	 */
	public boolean save(String filename) {
		boolean success = false;
		Node<E> current = null;
		byte[] buffer = null;
		
		try {
			RandomAccessFile raf = new RandomAccessFile(filename, "rw");
			raf.writeInt(getFileSignature());
			raf.writeInt(secList.size());
			
			for(int i=0; i<secList.size(); i++) {
				current = secList.get(i);
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(current);
				buffer = baos.toByteArray();
				
				raf.writeInt(buffer.length);
				raf.write(buffer);
			}
			
			raf.close();
			success = true;
		} catch (Exception e) {}
		
		return success;
	}
	
	/**
	 * Carga la estructura de un archivo.
	 * 
	 * @return LoadOpResult.success si la operación se completa satisfactoriamente,
	 * 			LoadOpResult.signatureError si el árbol que se intenta cargar no
	 * 			tiene la firma apropiada y LoadOpResult.error si ocurre una excepción.
	 */
	@SuppressWarnings("unchecked")
	public LoadOpResult load(String filename) {
		LoadOpResult result = LoadOpResult.error;
		Node<E> current = null;
		byte[] buffer = null;
		int totalNodes = 0;
		int size = 0;
		int signature = 0;
		
		try {
			RandomAccessFile raf = new RandomAccessFile(filename, "r");
			signature = raf.readInt();
			
			if(signature == getFileSignature()) {
				totalNodes = raf.readInt();
				
				for(int i=0; i<totalNodes; i++) {
					size = raf.readInt();
					buffer = new byte[size];
					raf.read(buffer);
					current = (Node<E>) new ObjectInputStream(new ByteArrayInputStream(buffer)).readObject();		
					secList.add(current);
				}
				
				raf.close();
				result = LoadOpResult.success;
			} else {
				raf.close();
				result = LoadOpResult.signatureError;
			}
		} catch (Exception e) {	}
		
		return result;
	}	
	
	/**
	 * Permite obtener el árbol general 
	 * generado por esta representación secuencial.
	 * 
	 * @return Árbol general.
	 */
	public GeneralTree<E> createGeneralTree() {
		GeneralTree<E> tree = new GeneralTree<E>();
		int rootElement = 0;
		
		if(!firstIsRoot)
			rootElement = secList.size()-1;
		
		if(rootElement > -1 && rootElement < secList.size()) {
			insertNodeInGTree(tree, rootElement, null);
		}
		
		return tree;
	}
	
	/**
	 * Esta función devuelve la firma del archivo. Cada tipo de árbol
	 * secuencial debe tener una firma diferente, por tanto todas las clases
	 * de árboles secuenciales TIENEN que reescribir esta función y devolver un 
	 * valor distinto. 
	 * 
	 * La firma del archivo sirve para garantizar que un tipo de árbol secuencial
	 * no intente cargar un archivo creado por otro tipo de árbol secuencial.
	 */
	protected int getFileSignature() {
		return -1;
	}
	
	/**
	 * Devuelve la lista secuencial.
	 * @return Lista secuencial.
	 */
	public ArrayList<Node<E>> getSecList() {
		return secList;
	}
	
	/**
	 * Devuelve la información del elemento
	 * con índice especificado.
	 * 
	 * @param index Índice.
	 * @return Información del nodo.
	 */
	public E get(int index){
		E result = null;
		
		if(index > -1 && index < secList.size())
			result = secList.get(index).getInfo();
		
        return result;
	}
	
	/**
	 * Indica el tamaño de la lista secuencial.
	 * @return Tamaño de la lista.
	 */
	public int getSecListSize() {
		return secList.size();
	}
}
