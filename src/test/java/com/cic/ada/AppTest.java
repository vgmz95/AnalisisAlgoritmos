package com.cic.ada;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.cic.ada.Grafo.Graph;
import com.cic.ada.Grafo.Vertex;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
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
        graph.addEdge(vertex1, vertex2, null);
        assertTrue(graph.existEdge(vertex1, vertex2));
        assertTrue(!graph.existEdge(vertex1, vertex3));
    }

    /**
     * Test Erdos-Renyi Graph Generation
     */
    @Test
    public void ErdosRenyiTest() {
        int n = 200, m = 300;
        Graph graph = Graph.generateErdosRenyiGraph(n, m, false, true);
        System.out.println("Graph: " + graph.toString());

    }

    /**
     * Test Gilbert Graph Generation
     */
    @Test
    public void GilbertTest() {
        int n = 25;
        double m = 0.5;
        Graph graph = Graph.generateGilbertGraph(n, m, false, true);
        System.out.println("Graph: " + graph.toString());
    }
}
