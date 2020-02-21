package com.cic.ada.Grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONObject;

public class Graph {

    HashMap<String, Vertex> Vertices;
    HashMap<String, List<Edge>> Edges;
    boolean directed;

    public Graph() {
        this.Vertices = new HashMap<>();
        this.Edges = new HashMap<>();
    }

    public Graph(boolean directed) {
        this.Vertices = new HashMap<>();
        this.Edges = new HashMap<>();
        this.directed = directed;
    }

    public Graph(HashMap<String, Vertex> vertices, HashMap<String, List<Edge>> edges, boolean directed) {
        Vertices = vertices;
        Edges = edges;
        this.directed = directed;
    }

    public void addVertex(Vertex vertex1) {
        Vertices.put(vertex1.getName(), vertex1);
        Edges.put(vertex1.getName(), new ArrayList<Edge>());
    }

    public void addEdge(Edge edge1) {
        if (!Edges.containsKey(edge1.Node1.getName())) {
            Edges.put(edge1.Node1.getName(), new ArrayList<Edge>());
        }
        Edges.get(edge1.Node1.getName()).add(edge1);
    }

    public void addEdge(String id, Vertex vertex1, Vertex vertex2, JSONObject data) {
        Edge edge = new Edge(id, vertex1, vertex2, data);
        this.addEdge(edge);
    }

    public void addEdge(Vertex vertex1, Vertex vertex2, JSONObject data) {
        String id;
        if (directed)
            id = vertex1.getName() + "->" + vertex2.getName();
        else
            id = vertex1.getName() + "--" + vertex2.getName();
        Edge edge = new Edge(id, vertex1, vertex2, data);
        this.addEdge(edge);
    }

    public boolean existEdge(Vertex vertex1, Vertex vertex2) {
        if (Edges.get(vertex1.getName()).isEmpty())
            return false;
        else
            return Edges.get(vertex1.getName()).stream().anyMatch(edge -> edge.Node2.equals(vertex2));
    }

    // First one
    public static Graph generateErdosRenyiGraph(int n, int m, boolean directed, boolean selfRelated) {
        Graph graph = new Graph(directed);
        // Create n vertices
        for (int i = 0; i < n; i++) {
            Vertex v = new Vertex(i + "", new JSONObject());
            graph.addVertex(v);
        }

        // Randomly choose m different pairs of different vertices
        for (int i = 0; i < m; i++) {
            int v1 = ThreadLocalRandom.current().nextInt(n);
            int v2 = ThreadLocalRandom.current().nextInt(n);
            Vertex vertex1 = graph.getVertices().get(v1 + "");
            Vertex vertex2 = graph.getVertices().get(v2 + "");

            if (v1 == v2 && !selfRelated) { // Same vertex
                i--;
            } else if (graph.existEdge(vertex1, vertex2)) {
                i--;
            } else {
                graph.addEdge(vertex1, vertex2, new JSONObject());
            }
        }
        return graph;
    }

    // Second one

    public static Graph generateGilbertGraph(int n, double p, boolean directed, boolean selfRelated) {
        Graph graph = new Graph(directed);
        // Create n vertices
        for (int i = 0; i < n; i++) {
            Vertex v = new Vertex(i + "", new JSONObject());
            graph.getVertices().put(v.getName(), v);
        }

        // Create a vertex if random>=p
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j && !selfRelated)
                    continue;
                // TODO: checar si la arista ya existe
                double random = ThreadLocalRandom.current().nextDouble();
                if (random <= p) {
                    Vertex vertex1 = graph.getVertices().get(i + "");
                    Vertex vertex2 = graph.getVertices().get(j + "");
                    graph.addEdge(vertex1, vertex2, new JSONObject());
                }
            }
        }
        return graph;
    }

    // Getters and setters
    public HashMap<String, Vertex> getVertices() {
        return Vertices;
    }

    public void setVertices(HashMap<String, Vertex> vertices) {
        Vertices = vertices;
    }

    public HashMap<String, List<Edge>> getEdges() {
        return Edges;
    }

    public void setEdges(HashMap<String, List<Edge>> edges) {
        Edges = edges;
    }

    public boolean isDirected() {
        return directed;
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        if (directed)
            str.append("digraph G{\n");
        else
            str.append("graph G{\n");
        this.Edges.values().forEach((edgeList) -> edgeList.forEach((edge) -> str.append(edge.id).append(";\n")));
        str.append("}");
        return str.toString();
    }

}
