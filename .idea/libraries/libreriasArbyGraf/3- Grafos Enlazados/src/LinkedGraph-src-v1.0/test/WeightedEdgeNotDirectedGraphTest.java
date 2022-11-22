package test;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cu.edu.cujae.ceis.graph.LinkedGraph;
import cu.edu.cujae.ceis.graph.interfaces.ILinkedWeightedEdgeNotDirectedGraph;

/**
 * Pruebas de unidad para las funciones de los
 * grafos no dirigidos de aristas ponderadas.
 */
public class WeightedEdgeNotDirectedGraphTest extends TestCase {
	
	private ILinkedWeightedEdgeNotDirectedGraph graph = new LinkedGraph();
	
	@Before
	public void setUp() throws Exception {
		graph.insertVertex("A");	//pos 0
		graph.insertVertex("B");	//pos 1
		graph.insertVertex("C");	//pos 2
		graph.insertVertex("D");	//pos 3
		graph.insertVertex("E");	//pos 4
		graph.insertVertex("F");	//pos 5 (este es un vértice aislado)
		
		graph.insertWEdgeNDG(0, 2, new Integer(1));
		graph.insertWEdgeNDG(0, 3, new Integer(1));		
		graph.insertWEdgeNDG(1, 4, new Integer(1));
		graph.insertWEdgeNDG(2, 1, new Integer(1));
		graph.insertWEdgeNDG(3, 1, new Integer(1));
		graph.insertWEdgeNDG(3, 2, new Integer(1));
		graph.insertWEdgeNDG(4, 0, new Integer(1));
	}

	@After
	public void tearDown() throws Exception {
	}	

	@Test
	public void testInsertWEdgeDG() {
		int degreeF = graph.degreeND(5);
		int degreeC  = graph.degreeND(2);
		graph.insertWEdgeNDG(5, 2, new Integer(2));
		
		boolean degreeCheck = ((graph.degreeND(5) == degreeF+1) && 
				(graph.degreeND(2) == degreeC+1));				
		
		if(!degreeCheck || !graph.areAdjacents(5, 2))
			fail("Error insertando arista.");
		
		//creando un lazo
		degreeC  = graph.degreeND(2);
		graph.insertWEdgeNDG(2, 2, new Integer(2));
		
		degreeCheck = (graph.degreeND(2) == degreeC+1);				
		
		if(!degreeCheck)
			fail("Error insertando lazo.");
	}

}
