package com.cic.ada;

import java.io.IOException;
import java.util.stream.Stream;

import com.cic.ada.Grafo.Graph;

/**
 * 
 *
 */
public class GraphApp {
	public static void main(String[] args) {

		int few = 40; // Few nodes (<50)
		int many = 100; // Many nodes (<100)
		int lots = 500; // Lots of nodes
		String path = "C:\\Users\\victo\\Documents\\grafos";
		String fileExt = ".gv";

		// Erdos-Renyi
		Graph[] erdosRenyiGraphs = { Graph.generateErdosRenyiGraph(few, 100, false, false),
				Graph.generateErdosRenyiGraph(many, 350, false, false),
				Graph.generateErdosRenyiGraph(lots, 700, false, false), };
		Stream.of(erdosRenyiGraphs).forEach(graph -> {
			try {
				graph.writeToFile(path, "ErdosRenyi-" + graph.getVertices().size() + fileExt);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		// Gilbert
		Graph[] GilbertGraphs = { Graph.generateGilbertGraph(few, 0.03, false, false),
				Graph.generateGilbertGraph(many, 0.03, false, false),
				Graph.generateGilbertGraph(lots, 0.02, false, false), };
		Stream.of(GilbertGraphs).forEach(graph -> {
			try {
				graph.writeToFile(path, "Gilbert-" + graph.getVertices().size() + fileExt);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		// Geographical
		Graph[] GeoGraphs = { Graph.generateGeographicGraph(few, 0.5, false, false),
				Graph.generateGeographicGraph(many, 0.2, false, false),
				Graph.generateGeographicGraph(lots, 0.08, false, false), };
		Stream.of(GeoGraphs).forEach(graph -> {
			try {
				graph.writeToFile(path, "Geograph-" + graph.getVertices().size() + fileExt);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		// Barabasi-Albert graphs
		Graph[] barabasiGraphs = { Graph.generateBarabasiAlbertGraph(few, 5, false, false),
				Graph.generateBarabasiAlbertGraph(many, 4, false, false),
				Graph.generateBarabasiAlbertGraph(lots, 3, false, false), };
		Stream.of(barabasiGraphs).forEach(graph -> {
			try {
				graph.writeToFile(path, "BarabasiAlbert-" + graph.getVertices().size() + fileExt);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		System.out.println("Graphs generated to: " + path);

	}
}
