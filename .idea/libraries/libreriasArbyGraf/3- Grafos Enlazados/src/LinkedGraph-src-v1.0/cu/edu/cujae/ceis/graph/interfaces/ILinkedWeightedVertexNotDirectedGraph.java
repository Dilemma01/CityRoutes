package cu.edu.cujae.ceis.graph.interfaces;

/**
 * <h1>Interface para grafos no dirigidos con v�rtices ponderados</h1>
 */
public interface ILinkedWeightedVertexNotDirectedGraph extends
		ILinkedNotDirectedGraph {
	public boolean insertWVertex(Object info, Object weight);
}
