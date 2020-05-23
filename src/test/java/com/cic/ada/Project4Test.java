package com.cic.ada;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.cic.ada.Grafo.Graph;
import com.cic.ada.Grafo.Vertex;

import org.junit.Test;

public class Project4Test {
    int few = 30; // Few nodes (<50)
    int many = 100; // Many nodes (<100)
    int lots = 500; // Lots of nodes
    float min = 1.0f, max = 50.0f;
    String path = "/home/victor/Documents/grafos/ArchivosGenerados_Proyecto4";
    String fileExt = ".gv";

    // CLRS pag 632
    @Test
    public void CLRSPrimTest() throws IOException {
        String type = "CLRSExample";
        Graph graph = new Graph(false);
        // Vertices
        Vertex a = new Vertex("a");
		Vertex b = new Vertex("b");
		Vertex c = new Vertex("c");
		Vertex d = new Vertex("d");
        Vertex e = new Vertex("e");
        Vertex f = new Vertex("f");
		Vertex g = new Vertex("g");
		Vertex h = new Vertex("h");
		Vertex i = new Vertex("i");
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		graph.addVertex(f);
		graph.addVertex(g);
		graph.addVertex(h);
        graph.addVertex(i);

        //Edges 
        Map<String, Object> properties = new HashMap<>();
        // a
        properties.put(Graph.WEIGHT, Float.valueOf(4.0f));
        graph.addEdge(a, b, properties);

        properties.put(Graph.WEIGHT, Float.valueOf(8.0f));
        graph.addEdge(a, h, properties);
        // b 
        properties.put(Graph.WEIGHT, Float.valueOf(11.0f));
        graph.addEdge(b, h, properties);

        properties.put(Graph.WEIGHT, Float.valueOf(8.0f));
        graph.addEdge(b, c, properties);
        
        // c 
        properties.put(Graph.WEIGHT, Float.valueOf(7.0f));
        graph.addEdge(c, d, properties);

        properties.put(Graph.WEIGHT, Float.valueOf(4.0f));
        graph.addEdge(c, f, properties);

        properties.put(Graph.WEIGHT, Float.valueOf(2.0f));
        graph.addEdge(c, i, properties);

        // d
        properties.put(Graph.WEIGHT, Float.valueOf(9.0f));
        graph.addEdge(d, e, properties);

        properties.put(Graph.WEIGHT, Float.valueOf(14.0f));
        graph.addEdge(d, f, properties);

        //e
        properties.put(Graph.WEIGHT, Float.valueOf(10.0f));
        graph.addEdge(e, f, properties);

        //f
        properties.put(Graph.WEIGHT, Float.valueOf(2.0f));
        graph.addEdge(f, g, properties);

        //g
        properties.put(Graph.WEIGHT, Float.valueOf(1.0f));
        graph.addEdge(g, h, properties);
        properties.put(Graph.WEIGHT, Float.valueOf(6.0f));
        graph.addEdge(g, i, properties);

        //h
        properties.put(Graph.WEIGHT, Float.valueOf(7.0f));
        graph.addEdge(h, i, properties);
        

        Graph result = graph.Kruskal_D();
        graph.writeToFile(path, type + result.getVertices().size() + fileExt);
        result.writeToFile(path, type + "Kruskal_D" + result.getVertices().size() + fileExt);
    }
    
}