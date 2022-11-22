package test;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cu.edu.cujae.ceis.graph.LinkedGraph;
import cu.edu.cujae.ceis.graph.interfaces.ILinkedWeightedEdgeDirectedGraph;

/**
 * Pruebas de unidad para las funciones de los
 * grafos dirigidos de aristas ponderadas.
 */
public class WeightedEdgeDirectedGraphTest extends TestCase {
	
	private ILinkedWeightedEdgeDirectedGraph graph = new LinkedGraph();
	
	@Before
	public void setUp() throws Exception {
		graph.insertVertex("A");	//pos 0
		graph.insertVertex("B");	//pos 1
		graph.insertVertex("C");	//pos 2
		graph.insertVertex("D");	//pos 3
		graph.insertVertex("E");	//pos 4
		graph.insertVertex("F");	//pos 5 (este es un vértice aislado)
		
		graph.insertWEdgeDG(0, 2, new Integer(1));
		graph.insertWEdgeDG(0, 3, new Integer(1));		
		graph.insertWEdgeDG(1, 4, new Integer(1));
		graph.insertWEdgeDG(2, 1, new Integer(1));
		graph.insertWEdgeDG(3, 1, new Integer(1));
		graph.insertWEdgeDG(3, 2, new Integer(1));
		graph.insertWEdgeDG(4, 0, new Integer(1));
	}

	@After
	public void tearDown() throws Exception {
	}	

	@Test
	public void testInsertWEdgeDG() {
		int outDegreeF = graph.outDegree(5);
		int inDegreeC  = graph.inDegreeDG(2);
		graph.insertWEdgeDG(5, 2, new Integer(2));
		
		boolean degreeCheck = ((graph.outDegree(5) == outDegreeF+1) && 
				(graph.inDegreeDG(2) == inDegreeC+1));				
		
		if(!degreeCheck || !graph.areAdjacents(5, 2))
			fail("Error insertando arista.");
	}

}
