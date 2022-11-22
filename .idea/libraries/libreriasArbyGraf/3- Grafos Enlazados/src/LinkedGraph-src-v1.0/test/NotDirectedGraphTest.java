package test;

import java.util.ArrayList;
import java.util.LinkedList;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cu.edu.cujae.ceis.graph.LinkedGraph;
import cu.edu.cujae.ceis.graph.interfaces.ILinkedNotDirectedGraph;
import cu.edu.cujae.ceis.graph.vertex.Vertex;

/**
 * Pruebas de unidad para las funciones de los
 * grafos no dirigidos.
 */
public class NotDirectedGraphTest extends TestCase {
	
	private ILinkedNotDirectedGraph graph = new LinkedGraph();

	@Before
	public void setUp() throws Exception {
		graph.insertVertex("A");	//pos 0
		graph.insertVertex("B");	//pos 1
		graph.insertVertex("C");	//pos 2
		graph.insertVertex("D");	//pos 3
		graph.insertVertex("E");	//pos 4
		graph.insertVertex("F");	//pos 5 (este es un vértice aislado)

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
	public void testDegreeND() {
		int size, degree;
		ArrayList<Integer> expectedValues = new ArrayList<Integer>();		

		expectedValues.add(3);	//A en pos 0
		expectedValues.add(3);	//B en pos 1
		expectedValues.add(3);	//C en pos 2
		expectedValues.add(3);	//D en pos 3
		expectedValues.add(2);	//E en pos 4
		expectedValues.add(0);	//F en pos 5
		size = expectedValues.size();

		for(int i=0; i<size; i++) {
			degree = graph.degreeND(i);			
			if(degree != expectedValues.get(i))							
				fail(String.format("Error determinando grado del vértice %1$d. " +
						"Resultado esperado: %2$d. Resultado obtenido:%3$d", i, expectedValues.get(i), degree));
		}
	}

	@Test
	public void testDeleteEdgeND() {
		boolean success = graph.deleteEdgeND(1, 4);

		if(!success || graph.getVerticesList().get(1).getAdjacents().size() != 2 || 
				graph.getVerticesList().get(4).getAdjacents().size() != 1)
			fail("Error eliminando arista.");
	}

	@Test
	public void testInsertEdgeNDG() {
		int degreeE = graph.degreeND(4);
		int degreeD = graph.degreeND(3);

		boolean success = graph.insertEdgeNDG(3, 4);

		int degreeEAft = graph.degreeND(4);
		int degreeDAft = graph.degreeND(3);

		if(!success || degreeE + 1 != degreeEAft || degreeD + 1 != degreeDAft)
			fail("Error insertando arista.");
				
		//creando un lazo
		degreeD = graph.degreeND(3);

		success = graph.insertEdgeNDG(3, 3);
		degreeDAft = graph.degreeND(3);

		if(!success || degreeD + 1 != degreeDAft)
			fail("Error insertando lazo.");
		
	}

	@Test
	public void testRemoveDisconnectVerticesND() {
		//El único vértice aislado es el F, es el único que se puede eliminar.
		LinkedList<Vertex> deleted = graph.removeDisconnectVerticesND();

		if(deleted.size() == 1) {
			String data = (String) deleted.get(0).getInfo();
			if(!(data.equalsIgnoreCase("F"))) 
				fail("No se eliminó el vértice esperado.");
		} else {
			fail("No se eliminaron la cantidad esperada de vértices aislados.");
		}
	}		
	
	@Test
	public void testCyclicND() {
		if(!graph.cyclicND())
			fail("No se ha detectado un cyclo existente.");

		graph.getVerticesList().clear();
		graph.insertVertex(new Vertex("A"));
		graph.insertVertex(new Vertex("B"));
		
		graph.insertEdgeNDG(0, 1);
		
		if(graph.cyclicND())
			fail("Se ha detectado un cyclo no existente.");
	}
}
