package test;

import java.util.Iterator;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cu.edu.cujae.ceis.graph.LinkedGraph;
import cu.edu.cujae.ceis.graph.interfaces.ILinkedWeightedVertexNotDirectedGraph;
import cu.edu.cujae.ceis.graph.vertex.Vertex;
import cu.edu.cujae.ceis.graph.vertex.WeightedVertex;

/**
 * Pruebas de unidad para las funciones de los
 * grafos con vértices ponderados.
 */
public class WeightedVertexGraphTest extends TestCase {
	
	private ILinkedWeightedVertexNotDirectedGraph graph = new LinkedGraph();
	
	@Before
	public void setUp() throws Exception {
		graph.insertWVertex("A", new Integer(1));	//pos 0
		graph.insertWVertex("B", new Integer(1));	//pos 1
		graph.insertWVertex("C", new Integer(1));	//pos 2
		graph.insertWVertex("D", new Integer(1));	//pos 3
		graph.insertWVertex("E", new Integer(1));	//pos 4
		graph.insertWVertex("F", new Integer(1));	//pos 5 (este es un vértice aislado)
		
		graph.insertEdgeNDG(0, 2);
		graph.insertEdgeNDG(0, 3);		
		graph.insertEdgeNDG(1, 4);
		graph.insertEdgeNDG(2, 1);
		graph.insertEdgeNDG(3, 1);
		graph.insertEdgeNDG(3, 2);
		graph.insertEdgeNDG(4, 0);
	}

	@After
	public void tearDown() throws Exception {
	}	

	@Test
	public void testInsertWVertex() {
		graph.insertWVertex("T", new Integer(1));
		int totalW = 0;		
		Iterator<Vertex> iter = graph.getVerticesList().iterator();
		
		while(iter.hasNext()) {
			totalW += (Integer)((WeightedVertex) iter.next()).getWeight();
		}
		
		if(totalW != 7)
			fail("Error con los vértices ponderados");
	}

}
