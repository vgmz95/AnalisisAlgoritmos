package com.cic.ada.Grafo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        // If directed
        // return from vertex1->vertex2
        if (directed)
            return existEdgeHelper(vertex1, vertex2);

        // if not directed
        // return from vertex1->vertex2 OR vertex2->vertex1
        else
            return existEdgeHelper(vertex1, vertex2) || existEdgeHelper(vertex2, vertex1);
    }

    private boolean existEdgeHelper(Vertex vertex1, Vertex vertex2) {// From Vertex1 to Vertex2
        if (Edges.get(vertex1.getName()).isEmpty()) // Empty list
            return false;
        else
            return Edges.get(vertex1.getName()).stream().anyMatch(edge -> edge.Node2.equals(vertex2));
    }

    public int VertexDegree(Vertex vertex) {
        return Edges.get(vertex.getName()).size();
    }

    // First one
    public static Graph generateErdosRenyiGraph(int n, int m, boolean directed, boolean selfRelated) {
        Graph graph = new Graph(directed);
        // Create n vertices
        IntStream.range(0, n).forEach(i -> graph.addVertex(new Vertex(i + "", new JSONObject())));

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
        IntStream.range(0, n).forEach(i -> graph.addVertex(new Vertex(i + "", new JSONObject())));

        // Create an edge if random>=p
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j && !selfRelated)
                    continue;
                double random = ThreadLocalRandom.current().nextDouble();
                if (random <= p) {
                    Vertex vertex1 = graph.getVertices().get(i + "");
                    Vertex vertex2 = graph.getVertices().get(j + "");
                    if (!graph.existEdge(vertex1, vertex2))
                        graph.addEdge(vertex1, vertex2, new JSONObject());
                }
            }
        }
        return graph;
    }

    // Third one
    public static Graph generateGeographicGraph(int n, double r, boolean directed, boolean selfRelated) {
        Graph graph = new Graph(directed);
        // Create n vertices
        for (int i = 0; i < n; i++) {
            double x = ThreadLocalRandom.current().nextDouble();
            double y = ThreadLocalRandom.current().nextDouble();
            var info = new JSONObject().put("x", x).put("y", y);
            Vertex v = new Vertex(i + "", info);
            graph.addVertex(v);
        }

        // Create an edge if random>=p
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j && !selfRelated) // Same vertex
                    continue;

                Vertex vertex1 = graph.getVertices().get(i + "");
                Vertex vertex2 = graph.getVertices().get(j + "");
                if (!graph.existEdge(vertex1, vertex2)) {
                    // Calculate distance
                    double x_1 = vertex1.getData().getDouble("x"), y_1 = vertex1.getData().getDouble("y");
                    double x_2 = vertex2.getData().getDouble("x"), y_2 = vertex2.getData().getDouble("y");
                    double x_diff = Math.pow(x_2 - x_1, 2.0), y_diff = Math.pow(y_2 - y_1, 2.0);
                    double distance = Math.sqrt(x_diff + y_diff);
                    if (distance <= r) { // Create vertex if distance <= radius
                        graph.addEdge(vertex1, vertex2, new JSONObject());
                    }
                }
            }
        }
        return graph;
    }

    // Fourth one
    public static Graph generateBarabasiAlbertGraph(int n, double d, boolean directed, boolean selfRelated) {
        Graph graph = new Graph(directed);
        // Create n vertices
        IntStream.range(0, n).forEach(i -> graph.addVertex(new Vertex(i + "", new JSONObject())));

        // Create random int array from to [0,n)
        List<Integer> arrayInt = IntStream.range(0, n).boxed().collect(Collectors.toList()); // [0,n)
        Collections.shuffle(arrayInt); // Shuffle the array

        int D = (int) d;
        // Por definicion los primeros D vertices tienen que conectarse
        for (int i = 0; i < D; i++) {
            Vertex vertex1 = graph.getVertices().get(arrayInt.get(i) + "");
            for (int j = i + 1; j < D; j++) {
                Vertex vertex2 = graph.getVertices().get(arrayInt.get(j) + "");
                graph.addEdge(vertex1, vertex2, new JSONObject());
            }
        }

        /*
         * Luego para los vertices restantes checo para los nodos anteriores si puedo
         * conectarlos o no dependiendo de su grado
         */
        for (int i = D; i < n; i++) {
            Vertex vertex1 = graph.getVertices().get(arrayInt.get(i) + "");
            for (int j = 0; j < i; j++) {
                Vertex vertex2 = graph.getVertices().get(arrayInt.get(j) + "");
                int gradoNodo = graph.VertexDegree(vertex2);
                double probabilidad = ThreadLocalRandom.current().nextDouble();
                double p = 1 - (double) (gradoNodo / d);
                if (probabilidad <= p) {
                    graph.addEdge(vertex1, vertex2, new JSONObject());
                }
            }
        }
        return graph;
    }

    // To wiz file
    public void writeToWizFile(String path, String filename) throws IOException {
        Files.write(Paths.get(path, filename), this.toString().getBytes(), StandardOpenOption.CREATE);
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
