package com.cic.ada;

import java.io.IOException;
import com.cic.ada.Grafo.Graph;
import com.cic.ada.Grafo.Vertex;

import org.junit.Test;

public class Project2Test {

	int few = 30; // Few nodes (<50)
	int many = 100; // Many nodes (<100)
	int lots = 500; // Lots of nodes
	String path = "/home/victor/Documents/grafos/ArchivosGenerados_Proyecto2";
	String fileExt = ".gv";

	@Test
	public void traversalTest() throws IOException {
		erdosRenyiTraversalTest();
		gilbertTraversalTest();
		geoGraphicTraversalTest();
		barabasiAlbertTraversalTest();
	}

	@Test
	public void erdosRenyiTraversalTest() throws IOException {
		String type = "1_ErdosRenyi-";
		Graph[] erdosRenyiGraphs = { Graph.generateErdosRenyiGraph(few, 100, false, false),
				Graph.generateErdosRenyiGraph(many, 200, false, false),
				Graph.generateErdosRenyiGraph(lots, 300, false, false), };
		for (Graph graph : erdosRenyiGraphs) {
			traversal(type, graph);
		}
	}

	@Test
	public void gilbertTraversalTest() throws IOException {
		String type = "2_Gilbert-";
		Graph[] GilbertGraphs = { Graph.generateGilbertGraph(few, 0.02, false, false),
				Graph.generateGilbertGraph(many, 0.02, false, false),
				Graph.generateGilbertGraph(lots, 0.02, false, false), };

		for (Graph graph : GilbertGraphs) {
			traversal(type, graph);
		}
	}

	@Test
	public void geoGraphicTraversalTest() throws IOException {
		String type = "3_Geograph-";

		Graph[] GeoGraphs = { Graph.generateGeographicGraph(few, 0.5, false, false),
				Graph.generateGeographicGraph(many, 0.2, false, false),
				Graph.generateGeographicGraph(lots, 0.08, false, false), };

		for (Graph graph : GeoGraphs) {
			traversal(type, graph);
		}

	}

	@Test
	public void barabasiAlbertTraversalTest() throws IOException {
		String type = "4_BarabasiAlbert-";
		Graph[] barabasiGraphs = { Graph.generateBarabasiAlbertGraph(few, 12, false, false),
				Graph.generateBarabasiAlbertGraph(many, 12, false, false),
				Graph.generateBarabasiAlbertGraph(lots, 15, false, false), };
		for (Graph graph : barabasiGraphs) {
			traversal(type, graph);
		}

	}

	private void traversal(String type, Graph graph) throws IOException {
		graph.writeToFile(path, type + graph.getVertices().size() + fileExt);
		Vertex source = graph.getVertexNameWithMaxOutDegree();
		graph.BFS(source).writeToFile(path, type + graph.getVertices().size() + "-BFS" + fileExt);
		graph.DFS_I(source).writeToFile(path, type + graph.getVertices().size() + "-DFS_I" + fileExt);
		graph.DFS_R(source).writeToFile(path, type + graph.getVertices().size() + "-DFS_R" + fileExt);
	}

	@Test
	public void traversalClrsTest() throws IOException {
		bfsClrsTest();
		dfsClrsTest();
	}

	@Test
	public void bfsClrsTest() throws IOException {
		Graph graph = new Graph(false);
		Vertex r, s, t, u, v, w, x, y;
		String type = "BFS-CLRS-";
		r = new Vertex("r");
		s = new Vertex("s");
		t = new Vertex("t");
		u = new Vertex("u");
		v = new Vertex("v");
		w = new Vertex("w");
		x = new Vertex("x");
		y = new Vertex("y");
		graph.addEdge(r, s);
		graph.addEdge(r, v);
		graph.addEdge(s, w);
		graph.addEdge(w, x);
		graph.addEdge(w, t);
		graph.addEdge(t, u);
		graph.addEdge(t, x);
		graph.addEdge(x, u);
		graph.addEdge(x, y);
		graph.addEdge(y, u);
		Graph bfs = graph.BFS(s);
		bfs.writeToFile(path, type + graph.getVertices().size() + fileExt);
	}

	@Test
	public void dfsClrsTest() throws IOException {
		Graph graph = new Graph(true);
		Vertex u, v, w, x, y, z;
		String type = "DFSR-CLRS-";
		u = new Vertex("u");
		v = new Vertex("v");
		w = new Vertex("w");
		x = new Vertex("x");
		y = new Vertex("y");
		z = new Vertex("z");
		graph.addEdge(u, v);
		graph.addEdge(u, x);
		graph.addEdge(x, v);
		graph.addEdge(v, y);
		graph.addEdge(w, y);
		graph.addEdge(w, z);
		graph.addEdge(z, z);
		Graph bfs = graph.DFS_R(u);
		bfs.writeToFile(path, type + graph.getVertices().size() + fileExt);
	}

}
