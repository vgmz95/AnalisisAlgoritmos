package com.cic.ada;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import com.cic.ada.Grafo.Graph;
import com.cic.ada.Grafo.Vertex;
import org.junit.Test;

public class Project1Test {

	int few = 30; // Few nodes (<50)
	int many = 100; // Many nodes (<100)
	int lots = 500; // Lots of nodes
	String path = "/home/victor/Documents/grafos/ArchivosGenerados_Proyecto1";
	String fileExt = ".gv";

	@Test
	public void graphGenerationTest() throws IOException {
		erdosRenyiFileTest();
		gilbertFileTest();
		geographicFileTest();
		barabasiAlbertFileTest();
	}

	/* Write to file tests */
	@Test
	public void erdosRenyiFileTest() throws IOException {
		// Erdos-Renyi
		Graph[] erdosRenyiGraphs = { Graph.generateErdosRenyiGraph(few, 100, false, false),
				Graph.generateErdosRenyiGraph(many, 200, false, false),
				Graph.generateErdosRenyiGraph(lots, 300, false, false), };
		for (Graph graph : erdosRenyiGraphs) {
			graph.writeToFile(path, "1_ErdosRenyi-" + graph.getVertices().size() + fileExt);
		}
	}

	@Test
	public void gilbertFileTest() throws IOException {
		// Gilbert
		Graph[] gilbertGraphs = { Graph.generateGilbertGraph(few, 0.02, false, false),
				Graph.generateGilbertGraph(many, 0.02, false, false),
				Graph.generateGilbertGraph(lots, 0.02, false, false), };

		for (Graph graph : gilbertGraphs) {
			graph.writeToFile(path, "2_Gilbert-" + graph.getVertices().size() + fileExt);
		}
	}

	@Test
	public void geographicFileTest() throws IOException {
		// Geographical
		Graph[] geoGraphs = { Graph.generateGeographicGraph(few, 0.5, false, false),
				Graph.generateGeographicGraph(many, 0.2, false, false),
				Graph.generateGeographicGraph(lots, 0.08, false, false), };

		for (Graph graph : geoGraphs) {
			graph.writeToFile(path, "3_Geograph-" + graph.getVertices().size() + fileExt);
		}

	}

	@Test
	public void barabasiAlbertFileTest() throws IOException {
		// Barabasi-Albert graphs
		Graph[] barabasiGraphs = { Graph.generateBarabasiAlbertGraph(few, 12, false, false),
				Graph.generateBarabasiAlbertGraph(many, 12, false, false),
				Graph.generateBarabasiAlbertGraph(lots, 15, false, false), };
		for (Graph graph : barabasiGraphs) {
			graph.writeToFile(path, "4_BarabasiAlbert-" + graph.getVertices().size() + fileExt);
		}
	}

	@Test
	public void addVertexTest() {
		Graph graph = new Graph(false);
		Vertex vertex = new Vertex("test");
		graph.addVertex(vertex);
		Vertex result = graph.getVertices().get("test");
		assertNotNull(result);
		assertEquals(vertex, graph.getVertices().get("test"));
	}

	@Test
	public void addEdgeTest() {
		Graph graph = new Graph(false);
		Vertex vertex1 = new Vertex("test1");
		Vertex vertex2 = new Vertex("test2");
		Vertex vertex3 = new Vertex("test3");
		graph.addVertex(vertex1);
		graph.addVertex(vertex2);
		graph.addVertex(vertex3);
		graph.addEdge(vertex1, vertex2);
		assertTrue(graph.existEdge(vertex1, vertex2));
		assertTrue(graph.existEdge(vertex2, vertex1));
		assertTrue(!graph.existEdge(vertex1, vertex3));
	}

	@Test
	public void checkIfVertexExistTest() {
		Graph graph = new Graph(false); // Not directed
		Vertex vertex1 = new Vertex("test1");
		Vertex vertex2 = new Vertex("test2");
		Vertex vertex3 = new Vertex("test3");
		graph.addVertex(vertex1);
		graph.addVertex(vertex2);
		graph.addVertex(vertex3);
		graph.addEdge(vertex1, vertex2);
		assertTrue(graph.existEdge(vertex1, vertex2));
		assertTrue(graph.existEdge(vertex2, vertex1));
		assertTrue(!graph.existEdge(vertex1, vertex3));
	}

	/**
	 * Test Erdos-Renyi Graph Generation
	 */
	@Test
	public void erdosRenyiTest() {
		int n = 200, m = 300;
		Graph graph = Graph.generateErdosRenyiGraph(n, m, false, true);
		System.out.println("Graph: \n" + graph.toString());
	}

	/**
	 * Test Gilbert Graph Generation
	 */
	@Test
	public void gilbertTest() {
		int n = 25;
		double m = 0.5;
		Graph graph = Graph.generateGilbertGraph(n, m, false, true);
		System.out.println("Graph: \n" + graph.toString());
	}

	/**
	 * Test Geographic Graph Generation
	 * 
	 * @throws IOException
	 */
	@Test
	public void geographicTest() throws IOException {
		int n = 200;
		double r = 0.3;
		Graph graph = Graph.generateGeographicGraph(n, r, false, true);
		System.out.println("Graph: \n" + graph.toString());
	}

	/**
	 * Test Barabasi Albert Graph Generation
	 */
	@Test
	public void barabasiAlbertTest() {
		int n = 20;
		double m = 2;
		Graph graph = Graph.generateBarabasiAlbertGraph(n, m, false, true);
		System.out.println("Graph: \n" + graph.toString());
	}

}
