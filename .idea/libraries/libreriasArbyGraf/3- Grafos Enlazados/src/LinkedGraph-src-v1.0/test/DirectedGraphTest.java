package test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cu.edu.cujae.ceis.graph.LinkedGraph;
import cu.edu.cujae.ceis.graph.interfaces.ILinkedDirectedGraph;
import cu.edu.cujae.ceis.graph.vertex.Vertex;

/**
 * Pruebas de unidad para las funciones de los
 * grafos dirigidos.
 */
public class DirectedGraphTest extends TestCase {
	
	private ILinkedDirectedGraph graph = new LinkedGraph();
	
	@Before
	public void setUp() throws Exception {
		graph.insertVertex("A");	//pos 0
		graph.insertVertex("B");	//pos 1
		graph.insertVertex("C");	//pos 2
		graph.insertVertex("D");	//pos 3
		graph.insertVertex("E");	//pos 4
		graph.insertVertex("F");	//pos 5 (este es un vértice aislado)
		
		graph.insertEdgeDG(0, 2);
		graph.insertEdgeDG(0, 3);		
		graph.insertEdgeDG(1, 4);
		graph.insertEdgeDG(2, 1);
		graph.insertEdgeDG(3, 1);
		graph.insertEdgeDG(3, 2);
		graph.insertEdgeDG(4, 0);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDegreeDG() {
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
			degree = graph.degreeDG(i);			
			if(degree != expectedValues.get(i))							
				fail(String.format("Error determinando grado del vértice %1$d. " +
						"Resultado esperado: %2$d. Resultado obtenido:%3$d", i, expectedValues.get(i), degree));
		}
	}

	@Test
	public void testInDegreeDG() {
		int size, degree;
		ArrayList<Integer> expectedValues = new ArrayList<Integer>();		
		
		expectedValues.add(1);	//A en pos 0
		expectedValues.add(2);	//B en pos 1
		expectedValues.add(2);	//C en pos 2
		expectedValues.add(1);	//D en pos 3
		expectedValues.add(1);	//E en pos 4
		expectedValues.add(0);	//F en pos 5
		size = expectedValues.size();
				
		for(int i=0; i<size; i++) {
			degree = graph.inDegreeDG(i);			
			if(degree != expectedValues.get(i))							
				fail(String.format("Error determinando grado de entrada del vértice %1$d. " +
						"Resultado esperado: %2$d. Resultado obtenido:%3$d", i, expectedValues.get(i), degree));
		}
	}

	@Test
	public void testOutDegree() {
		int size, degree;
		ArrayList<Integer> expectedValues = new ArrayList<Integer>();		
		
		expectedValues.add(2);	//A en pos 0
		expectedValues.add(1);	//B en pos 1
		expectedValues.add(1);	//C en pos 2
		expectedValues.add(2);	//D en pos 3
		expectedValues.add(1);	//E en pos 4
		expectedValues.add(0);	//F en pos 5
		size = expectedValues.size();
				
		for(int i=0; i<size; i++) {
			degree = graph.outDegree(i);			
			if(degree != expectedValues.get(i))							
				fail(String.format("Error determinando grado de salida del vértice %1$d. " +
						"Resultado esperado: %2$d. Resultado obtenido:%3$d", i, expectedValues.get(i), degree));
		}
	}

	@Test
	public void testDeleteEdgeD() {
		boolean success = graph.deleteEdgeD(1, 4);
		
		if(!success || graph.getVerticesList().get(1).getAdjacents().size() > 0)
			fail("Error eliminando arista.");
	}

	@Test
	public void testInsertEdgeDG() {
		int outDegreeF = graph.outDegree(5);
		int inDegreeC  = graph.inDegreeDG(2);
		graph.insertEdgeDG(5, 2);
		
		boolean degreeCheck = ((graph.outDegree(5) == outDegreeF+1) && 
				(graph.inDegreeDG(2) == inDegreeC+1));
		
		if(!degreeCheck || !graph.areAdjacents(5, 2))
			fail("Error insertando arista.");
	}

	@Test
	public void testRemoveDisconnectVerticesDG() {
		//El único vértice aislado es el F, es el único que se puede eliminar.
		LinkedList<Vertex> deleted = graph.removeDisconnectVerticesDG();
		
		if(deleted.size() == 1) {
			String data = (String) deleted.get(0).getInfo();
			if(!(data.equalsIgnoreCase("F"))) 
				fail("No se eliminó el vértice esperado.");
		} else {
			fail("No se eliminaron la cantidad esperada de vértices aislados.");
		}
	}	
	
	@Test
	public void testCyclicDG() {
		if(!graph.cyclicDG())
			fail("No se ha detectado un ciclo existente.");

		//vamos a quitar los ciclos
		graph.deleteEdgeD(0, 3);
		graph.deleteEdgeD(2, 1);
		graph.deleteEdgeD(0, 2);

		if(graph.cyclicDG())
			fail("Se ha detectado un ciclo no existente.");

		//ya aquí estoy garantizando que hay al menos un ciclo
		graph.insertEdgeDG(0, 5);
		graph.insertEdgeDG(5, 2); 

		//ahora vamos a insertar entre 3 y 100 aristas aleatorias
		Random r = new Random();
		int count = r.nextInt(100)+3;

		for(int i=0; i<count; i++) {
			graph.insertEdgeDG(r.nextInt(5), r.nextInt(5));
		}

		if(!graph.cyclicDG())
			fail("No se ha detectado un ciclo existente.");
		
		graph.getVerticesList().clear();
		graph.insertVertex("C");
		graph.insertVertex("E");
		graph.insertVertex("F");
		
		graph.insertEdgeDG(1, 0);
		graph.insertEdgeDG(2, 0);
		graph.insertEdgeDG(2, 1);
		
		if(graph.cyclicDG())
			fail("Se ha detectado un ciclo no existente.");
		
		graph.getVerticesList().clear();
		graph.insertVertex("A");
		graph.insertVertex("B");
		graph.insertVertex("C");
		graph.insertVertex("D");
		
		graph.insertEdgeDG(0, 1);
		graph.insertEdgeDG(1, 2);
		graph.insertEdgeDG(2, 3);
		graph.insertEdgeDG(3, 1);
		
		if(!graph.cyclicDG())
			fail("No se ha detectado un cyclo existente.");
	}
}
