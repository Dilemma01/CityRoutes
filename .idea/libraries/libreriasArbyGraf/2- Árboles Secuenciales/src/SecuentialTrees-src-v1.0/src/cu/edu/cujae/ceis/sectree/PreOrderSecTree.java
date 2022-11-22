package src.cu.edu.cujae.ceis.sectree;

import java.util.ArrayList;

import src.cu.edu.cujae.ceis.sectree.node.PreOrderNode;
import cu.edu.cujae.ceis.tree.binary.BinaryTreeNode;
import cu.edu.cujae.ceis.tree.general.GeneralTree;

/**
 * <h1>Árbol de preorden secuencial</h1>
 * @author Diana R. Colli IF-24 Curso 2013-2014
 *
 * @param <E> Tipo de dato del árbol
 */
public class PreOrderSecTree<E> extends SecTree<E> {
	
	private final int SEC_PREORDER_TREE_FILE_SIGNATURE = 1;
	private int pos=0;
	
////////////////Arbol recorrido en Preorden(RID)////////////////////

	public PreOrderSecTree() {
		super(true);
	}
	
	public PreOrderSecTree(GeneralTree<E> tree){
		super(true);
		insertNode((BinaryTreeNode<E>)tree.getRoot(),tree);
	}

	private void insertNode(BinaryTreeNode<E> root,GeneralTree<E> tree){
		processNode(root,tree);

		if(root.getLeft() != null){
			insertNode(root.getLeft(),tree);
		    
		}
		if(root.getRight() != null){
			insertNode(root.getRight(),tree);
		}
	}

	//En este metodo se procesa cada nodo en cuestion

	private void processNode(BinaryTreeNode<E> node,GeneralTree<E> tree){

		PreOrderNode<E> nodeToProcess = new PreOrderNode<E>();
		nodeToProcess.setInfo(node.getInfo());

		if(node.getRight()==null){
			nodeToProcess.setRigthLink(-1);
		}
		else{
			int ancestors = ancestors(node);
			nodeToProcess.setRigthLink(pos+ancestors);
			//La posicion del enlace derecho de este nodo va a ser su posicion mas la cantidad de ancestros 
		}
		if(node.getLeft()==null){
			nodeToProcess.setTerm(true);
		}
		else{
			nodeToProcess.setTerm(false);
		}
		secList.add(nodeToProcess);
		pos++;
	}
	
	private int ancestors(BinaryTreeNode<E> node) {
		int n = 0;

		if(null != node) {
			n++; //cuenta el propio nodo

			if(node.getLeft() != null) {  					//si tiene izquierdo
				BinaryTreeNode<E> pointer = node.getLeft(); //muevete al izquierdo
				n += ancestors(pointer);					//cuenta recursivamente todos los ancestros de ese izquierdo

				while(pointer.getRight() != null) {	//si tiene derecho
					pointer = pointer.getRight();	//muvete al derecho
					n += ancestors(pointer);		//cuenta recursivamente todos los ancestros de ese derecho
				}									
			}
		}
		return n;
	}
	
////////////////Obtener todas las posiciones de los hijos de un nodo////////////////////	
	
	public ArrayList<Integer> sons(int pos){
		ArrayList<Integer> listOfSons= new ArrayList<Integer>();
		if(pos >= 0 && pos < secList.size() && !((PreOrderNode<E>)secList.get(pos)).isTerm()){
			int child = pos+1;
			listOfSons.add(child);
			while(((PreOrderNode<E>)secList.get(child)).getRigthLink()!=-1){
				child = ((PreOrderNode<E>)secList.get(child)).getRigthLink();
				listOfSons.add(child);
			}
		}
		return listOfSons;
	}
	
////////////////Obtener la posicion del padre de un nodo//////////////////////////
	
	private boolean childOf(int posNode,int posFather){
		boolean childOf = false;
		int currentNode = posFather+1;
		if(currentNode != posNode){

			while(!childOf && currentNode< secList.size() && currentNode <posNode && currentNode> posFather){
				currentNode = ((PreOrderNode<E>)( secList.get(currentNode))).getRigthLink();

				if(currentNode == posNode){
					childOf = true;
				}

			}
		}
		else
			childOf = true;

		if(((PreOrderNode<E>)( secList.get(posFather))).isTerm()){
			childOf= false;
		}
		return childOf;
	}
	
	public int father(int pos){
		int posFather =-1;
		boolean found = false;
		if(pos!=0){
			int currentPos = pos-1;
			while(currentPos >=0 && !found){
				if(childOf(pos, currentPos)){
					posFather=currentPos;
					found = true;
				}
				else currentPos--;
			}
			
		}
		return posFather;
	}
	
////////////////Obtener el listado de las posiciones de los hermanos//////////////////////////	
	 
	public ArrayList<Integer> brothers(int pos){
		
		int father = father(pos);
		ArrayList<Integer> listOfBrothers= sons(father);
		Integer node = pos;
		listOfBrothers.remove(node);
		return listOfBrothers;
	}	

	@Override
	protected int getFileSignature() {
		return SEC_PREORDER_TREE_FILE_SIGNATURE;
	}
}
