package com.cic.ada.Grafo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.json.JSONObject;

public class Graph {

	private HashMap<String, Vertex> vertices;
	private HashMap<String, List<Edge>> edges;
	private boolean directed;

	public Graph() {
		this.vertices = new HashMap<>();
		this.edges = new HashMap<>();
	}

	public Graph(boolean directed) {
		this.vertices = new HashMap<>();
		this.edges = new HashMap<>();
		this.directed = directed;
	}

	public Graph(HashMap<String, Vertex> vertices, HashMap<String, List<Edge>> edges, boolean directed) {
		this.vertices = vertices;
		this.edges = edges;
		this.directed = directed;
	}

	public void addVertex(Vertex vertex1) {
		vertices.put(vertex1.getName(), vertex1);
		edges.put(vertex1.getName(), new ArrayList<Edge>());
	}

	public void addEdge(String id, Vertex vertex1, Vertex vertex2, JSONObject data) {
		Edge edge = new Edge(id, vertex1.getName(), vertex2.getName(), data);
		this.addEdge(edge);
	}

	public void addEdge(Vertex vertex1, Vertex vertex2, JSONObject data) {
		if (!vertices.containsKey(vertex1.getName())) {
			addVertex(vertex1);
		}

		if (!vertices.containsKey(vertex2.getName())) {
			addVertex(vertex2);
		}

		String id = generateEdgeId(vertex1.getName(), vertex2.getName());
		Edge edge = new Edge(id, vertex1.getName(), vertex2.getName(), data);
		this.addEdge(edge);
	}

	public void addEdge(Edge edge) {
		this.addEdgeHelper(edge);
		if (!directed) {
			// If it is not directed, add the reverse Edge
			this.addEdgeHelper(this.generateReverseEdge(edge));
		}
	}

	public void addEdgeHelper(Edge edge) {
		edges.get(edge.getNode1Name()).add(edge);
	}

	private Edge generateReverseEdge(Edge edge) {
		String vertex1 = edge.getNode2Name();
		String vertex2 = edge.getNode1Name();
		JSONObject data = edge.getData();
		String id = generateEdgeId(vertex1, vertex2);
		return new Edge(id, vertex1, vertex2, data);
	}

	private String generateEdgeId(String vertex1, String vertex2) {
		if (directed)
			return vertex1 + "->" + vertex2;
		else
			return vertex1 + "--" + vertex2;
	}

	public boolean existEdge(Vertex vertex1, Vertex vertex2) {
		List<Edge> edgeList = edges.get(vertex1.getName());
		if (edgeList.isEmpty())
			return false;
		else
			return edgeList.stream().anyMatch(edge -> edge.getNode2Name().equals(vertex2.getName()));
	}

	public int VertexDegree(Vertex vertex) {
		return edges.get(vertex.getName()).size();
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
			Vertex vertex1 = graph.getVertexByName(v1 + "");
			Vertex vertex2 = graph.getVertexByName(v2 + "");
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
					Vertex vertex1 = graph.getVertexByName(i + "");
					Vertex vertex2 = graph.getVertexByName(j + "");
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
		IntStream.range(0, n).forEach(i -> {
			double x = ThreadLocalRandom.current().nextDouble();
			double y = ThreadLocalRandom.current().nextDouble();
			JSONObject info = new JSONObject().put("x", x).put("y", y);
			graph.addVertex(new Vertex(i + "", info));
		});

		// Create an edge if random>=p
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j && !selfRelated) // Same vertex
					continue;

				Vertex vertex1 = graph.getVertexByName(i + "");
				Vertex vertex2 = graph.getVertexByName(j + "");
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
		// By definition, the first D vertices have to be connected
		for (int i = 0; i < D; i++) {
			Vertex vertex1 = graph.getVertexByName(arrayInt.get(i) + "");
			;
			;
			;
			for (int j = i + 1; j < D; j++) {
				Vertex vertex2 = graph.getVertexByName(arrayInt.get(j) + "");
				graph.addEdge(vertex1, vertex2, new JSONObject());
			}
		}

		// After that, connect using formula p=1-deg(v)/d
		for (int i = D; i < n; i++) {
			Vertex vertex1 = graph.getVertexByName(arrayInt.get(i) + "");
			for (int j = 0; j < i; j++) {
				Vertex vertex2 = graph.getVertexByName(arrayInt.get(j) + "");
				int nodeDegree = graph.VertexDegree(vertex2);
				double random = ThreadLocalRandom.current().nextDouble();
				double p = 1 - (double) (nodeDegree / d);
				if (random <= p) {
					graph.addEdge(vertex1, vertex2, new JSONObject());
				}
			}
		}
		return graph;
	}

	public Graph BFS(Vertex source) {
		Graph g = new Graph(false);

		vertices.values().forEach(vertex -> {// Distance == layer
			vertex.getData().put("discovered", false).put("distance", Integer.MAX_VALUE);
		});

		Vertex s = getVertexByName(source.getName());
		s.getData().put("discovered", true);
		s.getData().put("distance", 0);

		Queue<Vertex> q = new LinkedList<>();
		q.add(s);

		while (!q.isEmpty()) {
			Vertex u = q.poll();
			List<Edge> adjList = edges.get(u.getName());
			adjList.forEach(edge -> {
				Vertex v = getVertexByName(edge.getNode2Name());
				if (!v.getData().getBoolean("discovered")) {
					v.getData().put("discovered", true).put("distance", u.getData().getInt("distance") + 1);
					q.add(v);
					g.addEdge(u, v, new JSONObject());
				}
			});
		}

		return g;
	}

	public Graph DFS_R(Vertex source) {
		Graph g = new Graph(false);
		vertices.values().forEach(vertex -> {// Distance == layer
			vertex.getData().put("visited", false);
		});

		Vertex s = getVertexByName(source.getName());
		DFS_R_Helper(s, g);
		return g;
	}

	public void DFS_R_Helper(Vertex u, Graph g) {
		u.getData().put("visited", true);
		List<Edge> adjList = edges.get(u.getName());
		adjList.forEach(edge -> {
			Vertex v = getVertexByName(edge.getNode2Name());
			if (!v.getData().getBoolean("visited")) {
				g.addEdge(u, v, new JSONObject());
				DFS_R_Helper(v, g);
			}
		});
	}

	public Graph DFS_I(Vertex source) {
		Graph g = new Graph(false);
		Stack<Vertex> stack = new Stack<>();
		vertices.values().forEach(vertex -> {
			vertex.getData().put("visited", false);
		});

		Vertex s = getVertexByName(source.getName());
		s.getData().put("visited", true);
		stack.push(s);

		while (!stack.empty()) {
			Vertex u = stack.peek();
			stack.pop();
			List<Edge> adjList = edges.get(u.getName());
			adjList.forEach(edge -> {
				Vertex v = getVertexByName(edge.getNode2Name());
				if (!v.getData().getBoolean("visited")) {
					v.getData().put("visited", true);
					g.addEdge(u, v, new JSONObject());
					stack.push(v);
				}
			});
		}
		return g;
	}

	// Get vertex with max out degre
	public String getVertexNameWithMaxOutDegree() {
		Entry<String, List<Edge>> entry = this.getEdges().entrySet().stream().max((first, second) -> {
			if (first.getValue().size() > second.getValue().size())
				return 1;
			else if (first.getValue().size() < second.getValue().size())
				return -1;
			return 0;
		}).get();

		return entry.getKey();
	}

	// To file
	public void writeToFile(String path, String filename) throws IOException {
		Files.write(Paths.get(path, filename), this.toString().getBytes());
	}

	// Getters and setters
	public HashMap<String, Vertex> getVertices() {
		return vertices;
	}

	public Vertex getVertexByName(String name) {
		return vertices.get(name);
	}

	public void setVertices(HashMap<String, Vertex> vertices) {
		this.vertices = vertices;
	}

	public HashMap<String, List<Edge>> getEdges() {
		return edges;
	}

	public void setEdges(HashMap<String, List<Edge>> edges) {
		this.edges = edges;
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
		this.edges.values().forEach((edgeList) -> edgeList.forEach((edge) -> str.append(edge.getId()).append(";\n")));

		// { rank=same; A1 A2 A3 }
		Map<Integer, List<Vertex>> layers = this.vertices.values().stream()
				.collect(Collectors.groupingBy(Vertex::getLayer));
		layers.values().forEach(list -> {
			str.append("{ rank = same; ");
			list.forEach(element -> {
				str.append(element.getName() + "; ");
			});
			str.append("}\n");
		});

		str.append("}");
		return str.toString();
	}

}
