package com.cic.ada;

import java.io.IOException;
import com.cic.ada.Grafo.Graph;
import com.cic.ada.Grafo.Vertex;
import org.json.JSONObject;
import org.junit.Test;

public class Project3Test {

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

}