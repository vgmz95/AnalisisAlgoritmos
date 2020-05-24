package com.cic.ada;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.cic.ada.Grafo.Graph;
import com.cic.ada.Grafo.Vertex;
import org.junit.Test;

public class Project3Test {
	int few = 30; // Few nodes (<50)
	int many = 100; // Many nodes (<100)
	int lots = 500; // Lots of nodes
	float min = 1.0f, max = 50.0f;
	String path = "/home/victor/Documents/grafos/ArchivosGenerados_Proyecto3";
	String fileExt = ".gv";

	@Test
	public void shortestPathTest() throws IOException {
		erdosRenyiShortestPathTest();
		gilbertShortestPathTest();
		geoGraphicShortestPathTest();
		barabasiAlbertShortestPathTest();
	}

	@Test
	public void erdosRenyiShortestPathTest() throws IOException {
		String type = "1_ErdosRenyi-";
		Graph[] erdosRenyiGraphs = { Graph.generateErdosRenyiGraph(few, 100, false, false),
				Graph.generateErdosRenyiGraph(many, 200, false, false),
				Graph.generateErdosRenyiGraph(lots, 300, false, false), };
		for (Graph graph : erdosRenyiGraphs) {
			shortestPath(type, graph);
		}
	}

	@Test
	public void gilbertShortestPathTest() throws IOException {
		String type = "2_Gilbert-";
		Graph[] gilbertGraphs = { Graph.generateGilbertGraph(few, 0.02, false, false),
				Graph.generateGilbertGraph(many, 0.02, false, false),
				Graph.generateGilbertGraph(lots, 0.02, false, false), };
		for (Graph graph : gilbertGraphs) {
			shortestPath(type, graph);
		}
	}

	@Test
	public void geoGraphicShortestPathTest() throws IOException {
		String type = "3_Geograph-";

		Graph[] geoGraphs = { Graph.generateGeographicGraph(few, 0.5, false, false),
				Graph.generateGeographicGraph(many, 0.2, false, false),
				Graph.generateGeographicGraph(lots, 0.08, false, false), };

		for (Graph graph : geoGraphs) {
			shortestPath(type, graph);
		}

	}

	@Test
	public void barabasiAlbertShortestPathTest() throws IOException {
		String type = "4_BarabasiAlbert-";
		Graph[] barabasiGraphs = { Graph.generateBarabasiAlbertGraph(few, 12, false, false),
				Graph.generateBarabasiAlbertGraph(many, 12, false, false),
				Graph.generateBarabasiAlbertGraph(lots, 15, false, false), };
		for (Graph graph : barabasiGraphs) {
			shortestPath(type, graph);
		}

	}

	public void shortestPath(String type, Graph graph) throws IOException {
		graph.randomEdgeValues(min, max);
		graph.writeToFile(path, type + graph.getVertices().size() + fileExt);
		Vertex source = graph.getVertexNameWithMaxOutDegree();
		(graph.dijkstra(source)).writeToFile(path, type + graph.getVertices().size() + "-Dijkstra" + fileExt);
	}

	@Test
	public void randomEdgesTest() throws IOException {
		String type = "randomWeights";
		Graph graph = Graph.generateBarabasiAlbertGraph(30, 12, false, false);
		graph.randomEdgeValues(0, 20);
		graph.writeToFile(path, type + graph.getVertices().size() + fileExt);
	}

	@Test
	public void clrsDijkstraExampleTest() throws IOException {
		Graph g = new Graph(true);
		String type = "CLRSExample";
		// Vertices
		Vertex s = new Vertex("s");
		Vertex t = new Vertex("t");
		Vertex x = new Vertex("x");
		Vertex y = new Vertex("y");
		Vertex z = new Vertex("z");
		g.addVertex(s);
		g.addVertex(t);
		g.addVertex(x);
		g.addVertex(y);
		g.addVertex(z);
		// Edges
		// s
		Map<String, Object> properties = new HashMap<>();
		properties.put(Graph.WEIGHT, Float.valueOf(10.0f));
		g.addEdge(s, t, properties);
		properties.put(Graph.WEIGHT, Float.valueOf(5.0f));
		g.addEdge(s, y, properties);
		// t
		properties.put(Graph.WEIGHT, Float.valueOf(2.0f));
		g.addEdge(t, y, properties);
		properties.put(Graph.WEIGHT, Float.valueOf(1.0f));
		g.addEdge(t, x, properties);
		// x
		properties.put(Graph.WEIGHT, Float.valueOf(10.0f));
		g.addEdge(x, z, properties);
		// y
		properties.put(Graph.WEIGHT, Float.valueOf(3.0f));
		g.addEdge(y, t, properties);
		properties.put(Graph.WEIGHT, Float.valueOf(2.0f));
		g.addEdge(y, z, properties);
		properties.put(Graph.WEIGHT, Float.valueOf(9.0f));
		g.addEdge(y, x, properties);
		// z
		properties.put(Graph.WEIGHT, Float.valueOf(6.0f));
		g.addEdge(z, x, properties);
		properties.put(Graph.WEIGHT, Float.valueOf(7.0f));
		g.addEdge(z, s, properties);

		// Shortest path
		Graph result = g.dijkstra(s);
		result.writeToFile(path, type + result.getVertices().size() + fileExt);
	}

}