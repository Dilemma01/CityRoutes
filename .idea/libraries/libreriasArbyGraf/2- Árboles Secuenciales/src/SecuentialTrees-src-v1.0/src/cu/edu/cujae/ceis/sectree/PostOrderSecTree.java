package src.cu.edu.cujae.ceis.sectree;

import java.util.ArrayDeque;
import java.util.ArrayList;

import src.cu.edu.cujae.ceis.sectree.node.Node;
import src.cu.edu.cujae.ceis.sectree.node.PostOrderNode;
import cu.edu.cujae.ceis.tree.binary.BinaryTreeNode;
import cu.edu.cujae.ceis.tree.general.GeneralTree;

/**
 * <h1>Árbol de post orden secuencial</h1>
 * @author Omar Torres IF-24 Curso 2013-2014
 *
 * @param <E> Tipo de dato del árbol
 */
public class PostOrderSecTree<E> extends SecTree<E> {

	private final int SEC_POSTORDER_TREE_FILE_SIGNATURE = 3;
	
	public PostOrderSecTree(){
		super(false);
	}

	public PostOrderSecTree(GeneralTree<E> generalTree){
		super(false);
		if(generalTree != null){
			secList = new ArrayList<Node<E>>();
			BinaryTreeNode<E> root = (BinaryTreeNode<E>) generalTree.getRoot();
			if(root != null){
				BinaryTreeNode<E> left 	= root.getLeft();
				BinaryTreeNode<E> right = root.getRight();
				if(left != null)				
					recursive(left);
				secList.add(posOrderNode(root));
				if(right != null)
					recursive(right);
			}
		}
	}

	private void recursive(BinaryTreeNode<E> node){
		BinaryTreeNode<E> left = node.getLeft();
		BinaryTreeNode<E> right = node.getRight();

		if(left != null)
			recursive(left);

		secList.add(posOrderNode(node));

		if(right != null)
			recursive(right);
	}

	private PostOrderNode<E> posOrderNode(BinaryTreeNode<E> node){
		int grade = 0; 

		if(node != null){
			BinaryTreeNode<E> left = node.getLeft();

			if(left != null){
				GeneralTree<E> subTree = new GeneralTree<E>(node);
				grade = subTree.getSons(node).size();
			}		
		}
		PostOrderNode<E> posOrderNode = new PostOrderNode<E>(node.getInfo(), grade);
		return posOrderNode;
	}

	@Override
	public ArrayList<Integer> sons(int pos){
		ArrayList<Integer> childs = new ArrayList<Integer>();
		int i = pos-1;
		
		if(pos > 0 && pos < secList.size()) {
			PostOrderNode<E> current = ((PostOrderNode<E>) secList.get(pos));
			int nodeChilds = current.getGrade();
			int totalChildsCount = current.getGrade();
			
			while(i > -1 && nodeChilds > 0) {
				current = ((PostOrderNode<E>) secList.get(i));								
								
				if(totalChildsCount <= nodeChilds) {
					childs.add(i);
					nodeChilds--;
				}
				
				totalChildsCount += current.getGrade();
				totalChildsCount--;
				i--;
			}
		}
		
		//en este punto la lista de hijos está invertida, si la dejamos así el árbol se crea invertido sobre el eje y
		ArrayDeque<Integer> stack = new ArrayDeque<Integer>();
		
		for(int j=0; j<childs.size(); j++) {
			stack.push(childs.get(j));
		}
		
		childs.clear();
		
		while(!stack.isEmpty()) 
			childs.add(stack.pop());
		
		return childs;
	}

	@Override
	public int father(int pos){
		int father = -1;
		int brothers = 0;
		boolean find = false;
		int size = secList.size();

		if(pos >= 0 && pos < size - 1){
			pos++;
			if(pos < size){
				PostOrderNode<E> node = (PostOrderNode<E>) secList.get(pos);
				if(node.getGrade() != 0){
					father = pos;
					find = true;
				}
				else{
					brothers++;
					pos++;
				}
				while(pos < size && !find){
					node = (PostOrderNode<E>) secList.get(pos);
					if(node.getGrade() == 0)
						brothers++;
					else{
						if(node.getGrade() >= brothers + 1){
							father = pos;
							find = true;
						}
						else{
							brothers = brothers - node.getGrade() + 1;
						}
					}
					pos++;
				}
			}
		}
		return father;
	}

	@Override
	public ArrayList<Integer> brothers(int pos){
		ArrayList<Integer> brothers = new ArrayList<Integer>();
		int father = father(pos);

		if(father != -1){
			brothers = sons(father);
		}
		else{
			for(int i = 0; i < secList.size(); i++){
				father = father(i);
				if(father == -1){
					brothers.add(i);
				}
			}
		}
		brothers.remove((Integer)pos);
		return brothers;
	}

	@Override
	protected int getFileSignature() {
		return SEC_POSTORDER_TREE_FILE_SIGNATURE;
	}
}
