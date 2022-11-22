package test;

import java.util.List;

import cu.edu.cujae.ceis.tree.TreeNode;
import cu.edu.cujae.ceis.tree.binary.BinaryTree;
import cu.edu.cujae.ceis.tree.binary.BinaryTreeNode;
import cu.edu.cujae.ceis.tree.iterators.binary.PosOrderIterator;
import cu.edu.cujae.ceis.tree.iterators.binary.PreorderIterator;
import cu.edu.cujae.ceis.tree.iterators.binary.SymmetricIterator;



public class BinaryTreeTester {
	public static void main(String[] args) {	
		BinaryTree<String> tree = new BinaryTree<String>();
		
		BinaryTreeNode<String> root = new BinaryTreeNode<String>("A");
		
		//insert root
		tree.insertNode(root, 'R', null);
		
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
		
		tree.insertNode(nodeB, 'L', root);
		tree.insertNode(nodeC, 'R', root);
		tree.insertNode(nodeD, 'L', nodeB);
		tree.insertNode(nodeE, 'R', nodeB);
		tree.insertNode(nodeF, 'L', nodeC);
		tree.insertNode(nodeG, 'R', nodeC);
		
		tree.insertNode(nodeH, 'L', nodeD);
		tree.insertNode(nodeI, 'L', nodeH);
		
		tree.insertNode(nodeJ, 'L', nodeF);
		tree.insertNode(nodeK, 'R', nodeG);
		
		tree.insertNode(nodeL, 'L', nodeE);
		tree.insertNode(nodeM, 'R', nodeE);
		
		
		//tree.deleteNode(nodeC);
		//tree.deleteNode(nodeK);
		//tree.deleteNode(nodeE);
		//tree.deleteNode(nodeB);
		//tree.deleteNode(nodeD);
		//tree.deleteNode(nodeF);
		//tree.deleteNode(root);
		//tree.deleteNode(nodeF);
		//tree.deleteNode(nodeG);
		
		//move inside tree
		PreorderIterator<String> preOrderIterator = tree.preOrderIterator();			
		
		System.out.println("Recorrido en Pre Orden:");
		
		String preorderText = "";
		
		//preOrder
		while (preOrderIterator.hasNext()){
			String val = preOrderIterator.next();
			
			preorderText += val + ", ";
		}
		
		System.out.println(preorderText);								
				
		
		System.out.println();
		System.out.println("Recorrido en Simétrico:");				
		
		SymmetricIterator<String> symmetricIteratorIterator = tree.symmetricIterator();			
		
		String symmetricText = "";
		
		//symmetric
		while (symmetricIteratorIterator.hasNext()){
			String val = symmetricIteratorIterator.next();
			
			symmetricText += val + ", ";
		}
		
		System.out.println(symmetricText);
		
		
		
		PosOrderIterator<String> posOrderIterator = tree.posOrderIterator();														
		
		System.out.println();
		System.out.println("Recorrido en Pos Orden");
		
		String posorderText = "";
		
		//posOrder
		while (posOrderIterator.hasNext()){
			String val = posOrderIterator.next();
			
			posorderText += val + ", ";
		}
		
		System.out.println(posorderText);
		
		
		//int level = tree.treeLevel();
		
		/*BinaryTree<String> treeA = new BinaryTree<String>();
		BinaryTree<String> treeB = new BinaryTree<String>();
		
		boolean did = tree.divideTree(nodeC, treeA, treeB);
		
		//treeA
		System.out.println(did);
		
		PosOrderIterator<String> posOrderIterator = treeA.posOrderIterator();														
		
		System.out.println();
		System.out.println("TreeA Recorrido en Pos Orden");
		
		String posorderText = "";
		
		//posOrder
		while (posOrderIterator.hasNext()){
			String val = posOrderIterator.next();
			
			posorderText += val + ", ";
		}
		
		System.out.println(posorderText);
		
		//treeB
		System.out.println(did);
		
		PosOrderIterator<String> posOrderIteratorB = treeB.posOrderIterator();														
		
		System.out.println();
		System.out.println("TreeB Recorrido en Pos Orden");
		
		String posorderTextB = "";
		
		//posOrder
		while (posOrderIteratorB.hasNext()){
			String val = posOrderIteratorB.next();
			
			posorderTextB += val + ", ";
		}
		
		System.out.println(posorderTextB);
		*/
		
		/*List<TreeNode<String>> leaves = tree.getLeaves();
		
		for (TreeNode<String> treeNode : leaves) {
			System.out.println(((BinaryTreeNode<String>)treeNode).getInfo());
		}*/
		
		/*BinaryTree<String> subtree = tree.getSubTree(nodeB);
		
		PosOrderIterator<String> posOrderIteratorB = subtree.posOrderIterator();														
		
		System.out.println();
		System.out.println("SubTree Recorrido en Pos Orden");
		
		String posorderTextB = "";
		
		//posOrder
		while (posOrderIteratorB.hasNext()){
			String val = posOrderIteratorB.next();
			
			posorderTextB += val + ", ";
		}
		
		System.out.println(posorderTextB);*/
		
		//System.out.println(tree.totalNodes());
	}

}
