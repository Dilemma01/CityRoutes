package test;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cu.edu.cujae.ceis.graph.edge.Edge;
import cu.edu.cujae.ceis.graph.vertex.Vertex;

/**
 * Pruebas de unidad para Vertex.
 */
public class VertexTest extends TestCase {
	private Vertex vertex = new Vertex("A");
	
	private Vertex vertexB;
	private Vertex vertexC;
	private Vertex vertexD;
	

	@Before
	public void setUp() throws Exception {
		vertexB = new Vertex("B");
		vertexC = new Vertex("C");
		vertexD = new Vertex("D");
		
		vertex.getEdgeList().add(new Edge(vertexB));
		vertex.getEdgeList().add(new Edge(vertexC));
		vertex.getEdgeList().add(new Edge(vertexD));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDeleteEdge() {
		vertex.deleteEdge(vertexC);
		
		if(!vertex.isAdjacent(vertexB) || vertex.isAdjacent(vertexC) || !vertex.isAdjacent(vertexD))
			fail("Error eliminando arista.");
	}

	@Test
	public void testIsAdjacent() {
		if(!vertex.isAdjacent(vertexB) || !vertex.isAdjacent(vertexC) || !vertex.isAdjacent(vertexD))
			fail("Error determinando vértices adyacentes.");
	}

}
