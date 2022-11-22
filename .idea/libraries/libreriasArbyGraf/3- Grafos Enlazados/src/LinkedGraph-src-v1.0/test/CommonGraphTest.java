package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cu.edu.cujae.ceis.graph.LinkedGraph;
import cu.edu.cujae.ceis.graph.vertex.Vertex;

/**
 * Pruebas de unidad para las funciones comunes a todos
 * los grafos.
 */
public class CommonGraphTest extends TestCase {

	private LinkedGraph graph = new LinkedGraph();
	
	@Before
	public void setUp() throws Exception {
		graph.insertVertex("A");	//pos 0
		graph.insertVertex("B");	//pos 1
		graph.insertVertex("C");	//pos 2
		graph.insertVertex("D");	//pos 3
		graph.insertVertex("E");	//pos 4
		graph.insertVertex("F");	//pos 5 (este será un vértice aislado)

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
	public void testIsEmpty() {
		if(graph.isEmpty())
			fail("El grafo no debe estar vacío");	

		graph.getVerticesList().clear();

		if(!graph.isEmpty())
			fail("El grafo debe estar vacío");
	}	

	@Test
	public void testAreAdjacents() {
		int tail, head;

		/*
		 * Vamos a improvisarnos una clase que nos sirva como 
		 * vector bidimensional para asociar dos posiciones de
		 * vértices adyacentes.
		 */
		class Adjacents {
			private int tail;
			private int head;

			public Adjacents(int t, int h) {
				tail = t;
				head = h;
			}

			public int getTail() { return tail; }
			public int getHead() { return head; }
		}

		//esta es la lista de los que se espera que sean adyacentes.
		ArrayList<Adjacents> adjs = new ArrayList<Adjacents>();
		adjs.add(new Adjacents(0, 2));
		adjs.add(new Adjacents(0, 3));
		adjs.add(new Adjacents(1, 4));
		adjs.add(new Adjacents(2, 1));
		adjs.add(new Adjacents(3, 1));
		adjs.add(new Adjacents(3, 2));
		adjs.add(new Adjacents(4, 0));

		//ahora algunos de los que se espera que no sean adyacentes, hay más.
		ArrayList<Adjacents> notAdjs = new ArrayList<Adjacents>();
		notAdjs.add(new Adjacents(5, 0));
		notAdjs.add(new Adjacents(5, 1));
		notAdjs.add(new Adjacents(5, 2));
		notAdjs.add(new Adjacents(5, 3));
		notAdjs.add(new Adjacents(5, 4));
		notAdjs.add(new Adjacents(1, 0));
		notAdjs.add(new Adjacents(5, 4));
		notAdjs.add(new Adjacents(3, 4));


		for(int i=0; i<adjs.size(); i++) {
			tail = adjs.get(i).getTail();
			head = adjs.get(i).getHead();

			if(!graph.areAdjacents(tail, head)) {
				fail(String.format("Los vértices %1$d y %2$d son adyacentes y no se detectaron como tal.", tail, head));
			}
		}

		for(int i=0; i<adjs.size(); i++) {
			tail = notAdjs.get(i).getTail();
			head = notAdjs.get(i).getHead();

			if(graph.areAdjacents(tail, head)) {
				fail(String.format("Los vértices %1$d y %2$d no son adyacentes y se detectaron como tal.", tail, head));
			}
		}
	}	

	@Test
	public void testInsertVertex() {
		int size = graph.getVerticesList().size();
		boolean success = graph.insertVertex("G");

		if(!success || graph.getVerticesList().size() != size+1) 
			fail("Error insertando vértice.");
	}

	@Test
	public void testPathWithLength() {
		
		/*int s = 1;
		int e = 2;
		int l = 3;
		if(!pathWithLength(s, e, l))
			fail(String.format("Se esperaba encontrar un camino de longitud %1$d de %2$d a %3$d.", 
					l, s, e));*/

		class GraphPath { 
			private int start;
			private int end;
			private int length;

			public GraphPath(int start, int end, int length) {
				this.start  = start;
				this.end    = end;
				this.length = length;
			}

			public int getStart()  { return start;  }
			public int getEnd()    { return end;    }
			public int getLength() { return length;	}

		}

		ArrayList<GraphPath> pathList = new ArrayList<GraphPath>();

		//estos son los path que se espera que se detecten
		pathList.add(new GraphPath(0, 3, 1));
		pathList.add(new GraphPath(0, 1, 2));
		pathList.add(new GraphPath(0, 0, 4));
		pathList.add(new GraphPath(3, 2, 13));

		for(int i=0; i<pathList.size(); i++) {
			int start  = pathList.get(i).getStart();
			int end    = pathList.get(i).getEnd();
			int length = pathList.get(i).getLength();

			if(!graph.pathWithLength(start, end, length))
				fail(String.format("Se esperaba encontrar un camino de longitud %1$d de %2$d a %3$d.", 
						length, start, end));
		}

		pathList.clear();
		
		//estos path no existen
		pathList.add(new GraphPath(0, 0, 1));
		pathList.add(new GraphPath(0, 3, 2));
		pathList.add(new GraphPath(1, 2, 5));
		pathList.add(new GraphPath(0, 5, 10));
		pathList.add(new GraphPath(3, 0, 2));

		for(int i=0; i<pathList.size(); i++) {
			int start  = pathList.get(i).getStart();
			int end    = pathList.get(i).getEnd();
			int length = pathList.get(i).getLength();

			if(graph.pathWithLength(start, end, length))
				fail(String.format("No se esperaba encontrar un camino de longitud %1$d de %2$d a %3$d.", 
						length, start, end));
		}
	}

	@Test
	public void testAdjacentsG() {
		//Vamos a pedir los adyacentes a D 
		LinkedList<Vertex> adjacents = graph.adjacentsG(3);
		ArrayList<String> vertsNames = new ArrayList<String>();
		ArrayList<String> expectedsVertsNames = new ArrayList<String>();		

		//estos son los adyacentes que se deben obtener
		expectedsVertsNames.add("B");
		expectedsVertsNames.add("C");

		//estos son los que realmente se obtienen
		for (Vertex vertex : adjacents) {
			vertsNames.add(vertex.toString());
		}

		if(adjacents.size() == expectedsVertsNames.size()) {
			int size = vertsNames.size();

			for(int i=0; i<size; i++) {
				if(!vertsNames.contains(expectedsVertsNames.get(i))) {
					fail("Se esperaba que el vértice " + expectedsVertsNames.get(i) + " se obtuviera como adyacente.");		
				}
			}
		} else
			fail("No se obtuvo la cantidad de vértices adyacentes esperados.");	
	}	
	
	@Test
	public void testDeleteVertex() {
		int size = graph.getVerticesList().size();
		Vertex v = graph.deleteVertex(3);
		
		/*
		 * Verificamos que no se quedó en la lista de adyacentes
		 * de otro vértice.
		 */
		boolean adj = false;
		Iterator<Vertex> iter = graph.getVerticesList().iterator();
		while(!adj && iter.hasNext()) {
			if(iter.next().isAdjacent(v))
				adj = true;
		}
		
		if(v == null || graph.getVerticesList().size() != size-1 || adj) 
			fail("Error eliminando vértice.");
	}
	
	@Test
	public void testDeleteVertexCascade() {
		/*
		 * Eliminando el vértice A se debe eliminar en cascada B, C, D y E,
		 * quedando solamente el vértice aislado F.
		 */
		LinkedList<Vertex> deleted = graph.deleteVertexCascade(0);
		ArrayList<String> deletedVertsNames   = new ArrayList<String>();
		ArrayList<String> expectedsVertsNames = new ArrayList<String>();		
		
		//estos son los que se deben haber eliminado
		expectedsVertsNames.add("A");
		expectedsVertsNames.add("B");
		expectedsVertsNames.add("C");
		expectedsVertsNames.add("D");
		expectedsVertsNames.add("E");
		
		//estos son los que realmente se eliminaron
		for (Vertex vertex : deleted) {
			deletedVertsNames.add(vertex.toString());
		}
		
		if(deleted.size() == expectedsVertsNames.size()) {
			int size = deletedVertsNames.size();
			
			for(int i=0; i<size; i++) {
				if(!deletedVertsNames.contains(expectedsVertsNames.get(i))) {
					fail("Se esperaba que se eliminara en cascada el vértice " + expectedsVertsNames.get(i) + ".");		
				}
			}
		}
		
		if(graph.getVerticesList().size() > 1)
			fail("No se eliminaron realmente los vértices en cascada");
	}
}
