package test;

import java.util.List;

import cu.edu.cujae.ceis.tree.TreeNode;
import cu.edu.cujae.ceis.tree.binary.BinaryTreeNode;
import cu.edu.cujae.ceis.tree.general.GeneralTree;
import cu.edu.cujae.ceis.tree.iterators.general.InBreadthIterator;
import cu.edu.cujae.ceis.tree.iterators.general.InDepthIterator;

public class GeneralTreeTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GeneralTree<String> tree = new GeneralTree<String>();
		
		BinaryTreeNode<String> root = new BinaryTreeNode<String>("A");
		
		//insert root
		tree.insertNode(root, null);
		
		//insert the others nodes
		BinaryTreeNode<String> nodeB = new BinaryTreeNode<String>("B");
		BinaryTreeNode<String> nodeC = new BinaryTreeNode<String>("C");
		BinaryTreeNode<String> nodeD = new BinaryTreeNode<String>("D");
		BinaryTreeNode<String> nodeE = new BinaryTreeNode<String>("E");
		BinaryTreeNode<String> nodeF = new BinaryTreeNode<String>("F");
		BinaryTreeNode<String> nodeG = new BinaryTreeNode<String>("G");
		BinaryTreeNode<String> nodeH = new BinaryTreeNode<String>("H");
		BinaryTreeNode<String> nodeI = new BinaryTreeNode<String>("I");
		BinaryTreeNode<String> nodeJ = new BinaryTreeNode<String>("J");
		BinaryTreeNode<String> nodeK = new BinaryTreeNode<String>("K");
		BinaryTreeNode<String> nodeL = new BinaryTreeNode<String>("L");
		BinaryTreeNode<String> nodeM = new BinaryTreeNode<String>("M");				
		
		tree.insertNode(nodeB, root);
		tree.insertNode(nodeC, root);
		tree.insertNode(nodeD, root);
		
		tree.insertNode(nodeF, nodeB);
		tree.insertNode(nodeG, nodeB);
		
		tree.insertNode(nodeH, nodeC);
		
		tree.insertNode(nodeI, nodeD);
		tree.insertNode(nodeJ, nodeD);
		tree.insertNode(nodeK, nodeD);
		
		tree.insertNode(nodeE, nodeI);
		
		tree.insertNode(nodeL, nodeJ);
		tree.insertNode(nodeM, nodeJ);					
		
		//System.out.println(tree.totalNodes());
		
		//tree.deleteNode(root);
		
		
		//move inside tree
		InBreadthIterator<String> inBreadthIterator = tree.inBreadthIterator();			
		
		System.out.println("Recorrido a lo ancho:");
		
		String breadthText = "";
		
		//preOrder
		while (inBreadthIterator.hasNext()){
			String val = inBreadthIterator.next();
			
			breadthText += val + ", ";
		}
		
		System.out.println(breadthText);								
				
		
		System.out.println();
		System.out.println("Recorrido en profundidad:");				
		
		InDepthIterator<String> inDepthIterator = tree.inDepthIterator();			
		
		String inDepthText = "";
		
		//symmetric
		while (inDepthIterator.hasNext()){
			String val = inDepthIterator.next();
			
			inDepthText += val + ", ";
		}
		
		System.out.println(inDepthText);
		
		//System.out.println(tree.getFather(root).getInfo());
		
		/*List<TreeNode<String>> list = tree.getLeaves();
		
		for (TreeNode<String> node : list) {
			System.out.println(((BinaryTreeNode<String>)node).getInfo());
		}*/
		
		/*List<BinaryTreeNode<String>> sons = tree.getSons(nodeJ);
		
		for (BinaryTreeNode<String> node : sons) {
			System.out.println(node.getInfo());
		}*/
		
		//System.out.println(tree.nodeLevel(nodeM));
		
		//System.out.println(tree.nodeDegree(nodeJ));
		
		//System.out.println(tree.treeHeight());
	}
	
	

}
