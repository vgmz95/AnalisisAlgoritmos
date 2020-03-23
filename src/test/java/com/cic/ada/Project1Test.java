package com.cic.ada;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import com.cic.ada.Grafo.Graph;
import com.cic.ada.Grafo.Vertex;
import org.junit.Test;


public class Project1Test{
    @Test
    public void AddVertex() {
        Graph graph = new Graph(false);
        Vertex vertex = new Vertex("test", null);
        graph.addVertex(vertex);
        Vertex result = graph.getVertices().get("test");
        assertNotNull(result);
        assertEquals(vertex, graph.getVertices().get("test"));
    }

    @Test
    public void AddEdge() {
        Graph graph = new Graph(false);
        Vertex vertex1 = new Vertex("test1", null);
        Vertex vertex2 = new Vertex("test2", null);
        Vertex vertex3 = new Vertex("test3", null);
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);
        graph.addEdge(vertex1, vertex2, null);
        assertTrue(graph.existEdge(vertex1, vertex2));
        assertTrue(graph.existEdge(vertex2, vertex1));
        assertTrue(!graph.existEdge(vertex1, vertex3));
    }

    @Test
    public void CheckIfVertexExist() {
        Graph graph = new Graph(false); // Not directed
        Vertex vertex1 = new Vertex("test1", null);
        Vertex vertex2 = new Vertex("test2", null);
        Vertex vertex3 = new Vertex("test3", null);
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);
        graph.addEdge(vertex1, vertex2, null);
        assertTrue(graph.existEdge(vertex1, vertex2));
        assertTrue(graph.existEdge(vertex2, vertex1));
        assertTrue(!graph.existEdge(vertex1, vertex3));
    }

    /**
     * Test Erdos-Renyi Graph Generation
     */
    @Test
    public void ErdosRenyiTest() {
        int n = 200, m = 300;
        Graph graph = Graph.generateErdosRenyiGraph(n, m, false, true);
        System.out.println("Graph: \n" + graph.toString());
    }

    /**
     * Test Gilbert Graph Generation
     */
    @Test
    public void GilbertTest() {
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
    public void GeographicTest() throws IOException {
        int n = 200;
        double r = 0.3;
        Graph graph = Graph.generateGeographicGraph(n, r, false, true);
        System.out.println("Graph: \n" + graph.toString());
    }

    /**
     * Test Barabasi Albert Graph Generation
     */
    @Test
    public void BarabasiAlbertTest() {
        int n = 20;
        double m = 2;
        Graph graph = Graph.generateBarabasiAlbertGraph(n, m, false, true);
        System.out.println("Graph: \n" + graph.toString());
    }

    /* Write to file tests */

    int few = 30; // Few nodes (<50)
    int many = 100; // Many nodes (<100)
    int lots = 500; // Lots of nodes
    String path = "C:\\Users\\victo\\Documents\\grafos";
    String fileExt = ".gv";

    @Test
    public void ErdosRenyiFileTest() throws IOException {
        // Erdos-Renyi
        Graph[] erdosRenyiGraphs = {Graph.generateErdosRenyiGraph(few, 100, false, false),
                Graph.generateErdosRenyiGraph(many, 200, false, false),
                Graph.generateErdosRenyiGraph(lots, 300, false, false),};
        for (Graph graph : erdosRenyiGraphs) {
            graph.writeToFile(path, "1_ErdosRenyi-" + graph.getVertices().size() + fileExt);
        }
    }

    @Test
    public void GilbertFileTest() throws IOException {
        // Gilbert
        Graph[] GilbertGraphs = {Graph.generateGilbertGraph(few, 0.02, false, false),
                Graph.generateGilbertGraph(many, 0.02, false, false),
                Graph.generateGilbertGraph(lots, 0.02, false, false),};

        for (Graph graph : GilbertGraphs) {
            graph.writeToFile(path, "2_Gilbert-" + graph.getVertices().size() + fileExt);
        }
    }

    @Test
    public void GeographicFileTest() throws IOException {
        // Geographical
        Graph[] GeoGraphs = {Graph.generateGeographicGraph(few, 0.5, false, false),
                Graph.generateGeographicGraph(many, 0.2, false, false),
                Graph.generateGeographicGraph(lots, 0.08, false, false),};

        for (Graph graph : GeoGraphs) {
            graph.writeToFile(path, "3_Geograph-" + graph.getVertices().size() + fileExt);
        }

    }

    @Test
    public void BarabasiAlbertFileTest() throws IOException {
        // Barabasi-Albert graphs
        Graph[] barabasiGraphs = {Graph.generateBarabasiAlbertGraph(few, 12, false, false),
                Graph.generateBarabasiAlbertGraph(many, 12, false, false),
                Graph.generateBarabasiAlbertGraph(lots, 15, false, false),};
        for (Graph graph : barabasiGraphs) {
            graph.writeToFile(path, "4_BarabasiAlbert-" + graph.getVertices().size() + fileExt);
        }
    }


}
