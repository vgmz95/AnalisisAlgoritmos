package com.cic.ada;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.cic.ada.Grafo.Graph;
import com.cic.ada.Grafo.Vertex;

import org.junit.Assert;
import org.junit.Test;

public class Project4Test {
    int few = 30; // Few nodes (<50)
    int many = 100; // Many nodes (<100)
    int lots = 500; // Lots of nodes
    float min = 1.0f, max = 50.0f;
    String path = "/home/victor/Documents/grafos/ArchivosGenerados_Proyecto4";
    String fileExt = ".gv";

    @Test
    public void mstTest() throws IOException {
        erdosRenyiMstTest();
        gilbertMstTest();
        geoGraphicMstTest();
        barabasiAlbertMstTest();
    }

    @Test
    public void erdosRenyiMstTest() throws IOException {
        String type = "1_ErdosRenyi-";
        Graph[] erdosRenyiGraphs = { Graph.generateErdosRenyiGraph(few, 100, false, false),
                Graph.generateErdosRenyiGraph(many, 200, false, false),
                Graph.generateErdosRenyiGraph(lots, 300, false, false), };
        for (Graph graph : erdosRenyiGraphs) {
            mst(type, graph);
        }
    }

    @Test
    public void gilbertMstTest() throws IOException {
        String type = "2_Gilbert-";
        Graph[] GilbertGraphs = { Graph.generateGilbertGraph(few, 0.02, false, false),
                Graph.generateGilbertGraph(many, 0.02, false, false),
                Graph.generateGilbertGraph(lots, 0.02, false, false), };

        for (Graph graph : GilbertGraphs) {
            mst(type, graph);
        }
    }

    @Test
    public void geoGraphicMstTest() throws IOException {
        String type = "3_Geograph-";

        Graph[] GeoGraphs = { Graph.generateGeographicGraph(few, 0.5, false, false),
                Graph.generateGeographicGraph(many, 0.2, false, false),
                Graph.generateGeographicGraph(lots, 0.08, false, false), };

        for (Graph graph : GeoGraphs) {
            mst(type, graph);
        }

    }

    @Test
    public void barabasiAlbertMstTest() throws IOException {
        String type = "4_BarabasiAlbert-";
        Graph[] barabasiGraphs = { Graph.generateBarabasiAlbertGraph(few, 12, false, false),
                Graph.generateBarabasiAlbertGraph(many, 12, false, false),
                Graph.generateBarabasiAlbertGraph(lots, 15, false, false), };
        for (Graph graph : barabasiGraphs) {
            mst(type, graph);
        }

    }

    public void mst(String type, Graph graph) throws IOException {
        graph.randomEdgeValues(min, max);
        System.out.println(type + graph.getVertices().size());
        graph.writeToFile(path, type + graph.getVertices().size() + fileExt);
        graph.Kruskal_D().writeToFile(path, type + graph.getVertices().size() + "-Kruskal_D" + fileExt);
        graph.Kruskal_I().writeToFile(path, type + graph.getVertices().size() + "-Kruskal_I" + fileExt);
        graph.Prim().writeToFile(path, type + graph.getVertices().size() + "-Prim" + fileExt);
        System.out.println("_________________________________");

    }

    @Test
    public void removeEdgeTest() {
        Graph g = new Graph(false);
        Vertex vertex1 = new Vertex("a"), vertex2 = new Vertex("b"), vertex3 = new Vertex("c");
        g.addEdge(vertex1, vertex2);
        g.addEdge(vertex1, vertex3);
        g.deleteEdge(vertex1, vertex2);
        Assert.assertFalse(g.existEdge(vertex1, vertex2));
    }

    @Test
    public void conectivityTest() {
        Graph g = new Graph(false);
        Vertex vertex1 = new Vertex("a"), vertex2 = new Vertex("b"), vertex3 = new Vertex("c");
        g.addEdge(vertex1, vertex2);
        g.addEdge(vertex1, vertex3);
        Assert.assertTrue(g.isConnected());
        g.deleteEdge(vertex1, vertex2);
        Assert.assertFalse(g.isConnected());
    }

    // CLRS pag 632
    @Test
    public void clrsMstTest() throws IOException {
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

        // Edges
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

        // e
        properties.put(Graph.WEIGHT, Float.valueOf(10.0f));
        graph.addEdge(e, f, properties);

        // f
        properties.put(Graph.WEIGHT, Float.valueOf(2.0f));
        graph.addEdge(f, g, properties);

        // g
        properties.put(Graph.WEIGHT, Float.valueOf(1.0f));
        graph.addEdge(g, h, properties);
        properties.put(Graph.WEIGHT, Float.valueOf(6.0f));
        graph.addEdge(g, i, properties);

        // h
        properties.put(Graph.WEIGHT, Float.valueOf(7.0f));
        graph.addEdge(h, i, properties);

        mst(type, graph);
    }

}