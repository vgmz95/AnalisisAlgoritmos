package com.cic.ada;

import java.io.IOException;
import com.cic.ada.Grafo.Graph;
import com.cic.ada.Grafo.Vertex;
import org.json.JSONObject;
import org.junit.Test;

public class Project3Test {
	int few = 30; // Few nodes (<50)
	int many = 100; // Many nodes (<100)
	int lots = 500; // Lots of nodes
	float min = 1.0f, max = 50.0f;
	String path = "C:\\Users\\victo\\Documents\\grafos\\ArchivosGenerados_Proyecto3";
	String fileExt = ".gv";

	@Test
	public void testRandomEdges() throws IOException {
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
		Vertex s = new Vertex("s", new JSONObject());
		Vertex t = new Vertex("t", new JSONObject());
		Vertex x = new Vertex("x", new JSONObject());
		Vertex y = new Vertex("y", new JSONObject());
		Vertex z = new Vertex("z", new JSONObject());
		g.addVertex(s);
		g.addVertex(t);
		g.addVertex(x);
		g.addVertex(y);
		g.addVertex(z);
		// Edges
		//s 
		g.addEdge(s, t, new JSONObject().put(Graph.WEIGHT, 10.0));
		g.addEdge(s, y, new JSONObject().put(Graph.WEIGHT, 5.0));
		//t
		g.addEdge(t, y, new JSONObject().put(Graph.WEIGHT, 2.0));
		g.addEdge(t, x, new JSONObject().put(Graph.WEIGHT, 1.0));
		//x
		g.addEdge(x, z, new JSONObject().put(Graph.WEIGHT, 10.0));
		//y 
		g.addEdge(y, t, new JSONObject().put(Graph.WEIGHT, 3.0));
		g.addEdge(y, z, new JSONObject().put(Graph.WEIGHT, 2.0));
		g.addEdge(y, x, new JSONObject().put(Graph.WEIGHT, 9.0));
		//z
		g.addEdge(z, x, new JSONObject().put(Graph.WEIGHT, 6.0));
		g.addEdge(z, s, new JSONObject().put(Graph.WEIGHT, 7.0));	

		// Shortest path
		Graph result = g.dijkstra(s);
		result.writeToFile(path, type + result.getVertices().size() + fileExt);
	}


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
		Graph[] GilbertGraphs = { Graph.generateGilbertGraph(few, 0.02, false, false),
				Graph.generateGilbertGraph(many, 0.02, false, false),
				Graph.generateGilbertGraph(lots, 0.02, false, false), };
		for (Graph graph : GilbertGraphs) {		
			shortestPath(type, graph);
		}
	}

	@Test
	public void geoGraphicShortestPathTest() throws IOException {
		String type = "3_Geograph-";

		Graph[] GeoGraphs = { Graph.generateGeographicGraph(few, 0.5, false, false),
				Graph.generateGeographicGraph(many, 0.2, false, false),
				Graph.generateGeographicGraph(lots, 0.08, false, false), };

		for (Graph graph : GeoGraphs) {			
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

	public void shortestPath (String type, Graph graph) throws IOException {
		graph.randomEdgeValues(min, max);
		graph.writeToFile(path, type + graph.getVertices().size() + fileExt);
		Vertex source = graph.getVertexNameWithMaxOutDegree();
		(graph.dijkstra(source)).writeToFile(path, type + graph.getVertices().size() + "-Dijkstra" + fileExt);
	}

}