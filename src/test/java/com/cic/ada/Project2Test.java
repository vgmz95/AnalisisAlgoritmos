package com.cic.ada;

import java.io.IOException;
import com.cic.ada.Grafo.Graph;
import com.cic.ada.Grafo.Vertex;
import org.junit.Test;

public class Project2Test {

	/* Write to file tests */

	int few = 30; // Few nodes (<50)
	int many = 100; // Many nodes (<100)
	int lots = 500; // Lots of nodes
	String path = "C:\\Users\\victo\\Documents\\grafos";
	String fileExt = ".gv";

	@Test
	public void DFS_BFS_Test() throws IOException {
		erdosRenyiBFSTest();
		gilbertBFSTest();
		// GeographicFileTest
		// BarabasiAlbertFileTest

	}

	@Test
	public void erdosRenyiBFSTest() throws IOException {
		String type = "1_ErdosRenyi-";
		Graph[] erdosRenyiGraphs = { Graph.generateErdosRenyiGraph(few, 100, false, false),
				Graph.generateErdosRenyiGraph(many, 200, false, false),
				Graph.generateErdosRenyiGraph(lots, 300, false, false), };
		for (Graph graph : erdosRenyiGraphs) {
			Vertex source = graph.getVertexByName(graph.getVertexNameWithMaxOutDegree());
			graph.writeToFile(path, type + graph.getVertices().size() + fileExt);
			graph.BFS(source).writeToFile(path, type + "BFS-" + graph.getVertices().size() + fileExt);
			graph.DFS_I(source).writeToFile(path, type + "DFS_I-" + graph.getVertices().size() + fileExt);
			graph.DFS_R(source).writeToFile(path, type + "DFS_R-" + graph.getVertices().size() + fileExt);
		}
	}

	public void gilbertBFSTest() throws IOException {
		String type = "2_Gilbert-";
		Graph[] GilbertGraphs = { Graph.generateGilbertGraph(few, 0.02, false, false),
				Graph.generateGilbertGraph(many, 0.02, false, false),
				Graph.generateGilbertGraph(lots, 0.02, false, false), };

		for (Graph graph : GilbertGraphs) {
			Vertex source = graph.getVertexByName(graph.getVertexNameWithMaxOutDegree());
			graph.writeToFile(path, type + graph.getVertices().size() + fileExt);
			graph.BFS(source).writeToFile(path, type + "BFS-" + graph.getVertices().size() + fileExt);
			graph.DFS_I(source).writeToFile(path, type + "DFS_I-" + graph.getVertices().size() + fileExt);
			graph.DFS_R(source).writeToFile(path, type + "DFS_R-" + graph.getVertices().size() + fileExt);
		}
	}

}
