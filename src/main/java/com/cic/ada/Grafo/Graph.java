package com.cic.ada.Grafo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Graph {

	private final Map<String, Vertex> vertices;
	private final Map<String, List<Edge>> edges;
	private final boolean directed;

	private static final String DISTANCE = "distance";
	private static final String DISCOVERED = "discovered";
	public static final String VISITED = "visited";
	public static final String WEIGHT = "weight";
	private static final String NIL = "nil";
	private static final String PARENT = "parent";

	public Graph(boolean directed) {
		this.vertices = new HashMap<>();
		this.edges = new HashMap<>();
		this.directed = directed;
	}

	// Copy constructor
	public Graph(Graph graph) {
		this(graph.getVertices(), graph.getEdges(), graph.isDirected());
	}

	public Graph(Map<String, Vertex> vertices, Map<String, List<Edge>> edges, boolean directed) {
		this.vertices = vertices.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> new Vertex(e.getValue())));
		List<Edge> allEdges = edges
				.values().stream().flatMap(Collection::stream).map(e -> new Edge(e.getId(),
						this.vertices.get(e.getNode1Name()), this.vertices.get(e.getNode2Name()), e.getProperties()))
				.collect(Collectors.toList());
		this.edges = allEdges.stream().collect(Collectors.groupingBy(Edge::getNode1Name));
		this.directed = directed;
	}

	public Graph(Map<String, Vertex> vertices, boolean directed) {
		this.vertices = vertices.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> new Vertex(e.getValue())));
		this.edges = new HashMap<>();
		for (Vertex vertex : this.vertices.values()) {
			this.edges.put(vertex.getName(), new ArrayList<Edge>());
		}
		this.directed = directed;
	}

	public void addVertex(Vertex vertex1) {
		vertices.put(vertex1.getName(), vertex1);
		edges.put(vertex1.getName(), new ArrayList<Edge>());
	}

	public void addEdge(String id, Vertex vertex1, Vertex vertex2, Map<String, Object> properties) {
		Edge edge = new Edge(id, vertex1, vertex2, properties);
		this.addEdge(edge);
	}

	public void addEdge(Vertex vertex1, Vertex vertex2, Map<String, Object> properties) {
		if (!vertices.containsKey(vertex1.getName())) {
			addVertex(vertex1);
		}

		if (!vertices.containsKey(vertex2.getName())) {
			addVertex(vertex2);
		}

		String id = generateEdgeId(vertex1, vertex2);
		Edge edge = new Edge(id, vertex1, vertex2, properties);
		this.addEdge(edge);
	}

	public void addEdge(Vertex vertex1, Vertex vertex2) {
		this.addEdge(vertex1, vertex2, new HashMap<String, Object>());
	}

	// Real adding
	public void addEdge(Edge edge) {
		this.addEdgeHelper(edge);
		if (!directed) {
			// If it is not directed, add the reverse Edge
			this.addEdgeHelper(this.generateReverseEdge(edge));
		}
	}

	private void addEdgeHelper(Edge edge) {
		edges.get(edge.getNode1().getName()).add(edge);
	}

	private Edge generateReverseEdge(Edge edge) {
		String id = generateEdgeId(edge.getNode2(), edge.getNode1());
		return new Edge(id, edge.getNode2(), edge.getNode1(), edge.getProperties());
	}

	private String generateEdgeId(Vertex vertex1, Vertex vertex2) {
		if (directed)
			return vertex1.getName() + "->" + vertex2.getName();
		else
			return vertex1.getName() + "--" + vertex2.getName();
	}

	public boolean existEdge(Vertex vertex1, Vertex vertex2) {
		List<Edge> adjList = edges.get(vertex1.getName());
		if (adjList.isEmpty())
			return false;
		else
			return adjList.stream().anyMatch(edge -> edge.getNode2().equals(vertex2));
	}

	public int vertexDegree(Vertex vertex) {
		return edges.get(vertex.getName()).size();
	}

	// First one
	public static Graph generateErdosRenyiGraph(int n, int m, boolean directed, boolean selfRelated) {
		Graph graph = new Graph(directed);
		// Create n vertices
		IntStream.range(0, n).forEach(i -> graph.addVertex(new Vertex(i + "")));

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
				graph.addEdge(vertex1, vertex2);
			}
		}
		return graph;
	}

	// Second one
	public static Graph generateGilbertGraph(int n, double p, boolean directed, boolean selfRelated) {
		Graph graph = new Graph(directed);
		// Create n vertices
		IntStream.range(0, n).forEach(i -> graph.addVertex(new Vertex(i + "")));

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
						graph.addEdge(vertex1, vertex2);
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
			Double x = ThreadLocalRandom.current().nextDouble();
			Double y = ThreadLocalRandom.current().nextDouble();
			Vertex vertex = new Vertex(i + "");
			vertex.setProperty("x", x);
			vertex.setProperty("y", y);
			graph.addVertex(vertex);
		});

		// Create an edge if random>=p
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j && !selfRelated) // Same vertex
					continue;

				Vertex vertex1 = graph.getVertices().get(i + "");
				Vertex vertex2 = graph.getVertices().get(j + "");
				if (!graph.existEdge(vertex1, vertex2)) {
					// Calculate distance
					Double x_1 = (Double) vertex1.getProperty("x"), y_1 = (Double) vertex1.getProperty("y");
					Double x_2 = (Double) vertex2.getProperty("x"), y_2 = (Double) vertex2.getProperty("y");
					Double x_diff = Math.pow(x_2 - x_1, 2.0d), y_diff = Math.pow(y_2 - y_1, 2.0d);
					Double distance = Math.sqrt(x_diff + y_diff);
					if (distance <= r) { // Create vertex if distance <= radius
						graph.addEdge(vertex1, vertex2);
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
		IntStream.range(0, n).forEach(i -> graph.addVertex(new Vertex(i + "")));

		// Create random int array from to [0,n)
		List<Integer> arrayInt = IntStream.range(0, n).boxed().collect(Collectors.toList()); // [0,n)
		Collections.shuffle(arrayInt); // Shuffle the array

		int D = (int) d;
		// By definition, the first D vertices have to be connected
		for (int i = 0; i < D; i++) {
			Vertex vertex1 = graph.getVertices().get(arrayInt.get(i) + "");
			for (int j = i + 1; j < D; j++) {
				Vertex vertex2 = graph.getVertices().get(arrayInt.get(j) + "");
				graph.addEdge(vertex1, vertex2);
			}
		}

		// After that, connect using formula p=1-deg(v)/d
		for (int i = D; i < n; i++) {
			Vertex vertex1 = graph.getVertices().get(arrayInt.get(i) + "");
			for (int j = 0; j < i; j++) {
				Vertex vertex2 = graph.getVertices().get(arrayInt.get(j) + "");
				int nodeDegree = graph.vertexDegree(vertex2);
				double random = ThreadLocalRandom.current().nextDouble();
				double p = 1 - (double) (nodeDegree / d);
				if (random <= p) {
					graph.addEdge(vertex1, vertex2);
				}
			}
		}
		return graph;
	}

	public Graph BFS(Vertex s) {
		Graph g = new Graph(false);

		vertices.values().forEach(vertex -> {// distance == layer
			vertex.setProperty(DISCOVERED, Boolean.FALSE);
			vertex.setProperty(DISTANCE, Integer.MAX_VALUE);
		});

		s.setProperty(DISCOVERED, true);
		s.setProperty(DISTANCE, Integer.valueOf(0));

		Queue<Vertex> q = new LinkedList<>();
		q.add(s);

		while (!q.isEmpty()) {
			Vertex u = q.poll();
			List<Edge> adjList = edges.get(u.getName());
			adjList.forEach(edge -> {
				Vertex v = edge.getNode2();
				boolean discovered = ((Boolean) v.getProperty(DISCOVERED)).booleanValue();
				if (!discovered) {
					v.setProperty(DISCOVERED, Boolean.TRUE);
					v.setProperty(DISTANCE, Integer.valueOf((Integer) u.getProperty(DISTANCE) + 1));
					q.add(v);
					g.addEdge(u, v, edge.getProperties());
				}
			});
		}
		// Add vertices to the graph map that were not discovered
		vertices.values().forEach(vertex -> g.getVertices().putIfAbsent(vertex.getName(), vertex));

		return g;
	}

	public Graph DFS_R(Vertex s) {
		Graph g = new Graph(false);
		vertices.values().forEach(vertex -> {
			vertex.setProperty(VISITED, Boolean.FALSE);
		});
		DFS_R_Helper(s, g);

		// Add vertices to the graph map that were not discovered
		vertices.values().forEach(vertex -> g.getVertices().putIfAbsent(vertex.getName(), vertex));
		return g;
	}

	public void DFS_R_Helper(Vertex u, Graph g) {
		u.setProperty(VISITED, Boolean.TRUE);
		List<Edge> adjList = edges.get(u.getName());
		adjList.forEach(edge -> {
			Vertex v = edge.getNode2();
			boolean visited = ((Boolean) v.getProperty(VISITED)).booleanValue();
			if (!visited) {
				g.addEdge(u, v, edge.getProperties());
				DFS_R_Helper(v, g);
			}
		});
	}

	// Changed: clone
	public Graph DFS_I(Vertex s) {
		Graph resGraph = new Graph(this.getVertices(), this.isDirected());
		Vertex source = resGraph.getVertices().get(s.getName());
		Stack<Vertex> stack = new Stack<>();
		resGraph.getVertices().values().forEach(vertex -> vertex.setProperty(VISITED, Boolean.FALSE));

		stack.push(source);
		source.setProperty(VISITED, Boolean.TRUE);

		while (!stack.empty()) {
			Vertex u = stack.pop();
			List<Edge> adjList = this.getEdges().get(u.getName());
			for (Edge edge : adjList) {
				Vertex v = resGraph.getVertices().get(edge.getNode2Name());
				boolean visited = ((Boolean) v.getProperty(VISITED)).booleanValue();
				if (!visited) {
					v.setProperty(VISITED, Boolean.TRUE);
					resGraph.addEdge(u, v, edge.getProperties());
					stack.push(v);
				}
			}
		}
		return resGraph;
	}

	public void randomEdgeValues(float min, float max) {
		for (Entry<String, List<Edge>> entry : getEdges().entrySet()) {
			List<Edge> adjList1 = entry.getValue();
			for (Edge edge1 : adjList1) {
				if (!edge1.getProperties().containsKey(WEIGHT)) {
					Double random = ThreadLocalRandom.current().nextDouble(min, max);
					float weight = random.floatValue();
					edge1.setProperty(WEIGHT, Float.valueOf(weight)); // n1->n2
					if (!directed) { // n2->n1
						List<Edge> adjList2 = getEdges().get(edge1.getNode2().getName());
						for (Edge edge2 : adjList2) {
							if ((edge2.getNode2().getName()).equals(edge1.getNode1().getName())) {
								edge2.setProperty(WEIGHT, Float.valueOf(weight));
								break;
							}
						}
					}
				}

			}
		}
	}

	public Graph dijkstra(Vertex source) {
		// *** CLRS implementation *** //
		initializeSingleSource(vertices, source);
		Set<Vertex> s = new HashSet<>();
		// Priority queue keyed by distance
		Queue<Vertex> q = new PriorityQueue<>(vertices.size(), new VertexDistanceComparator());
		q.addAll(vertices.values());

		while (!q.isEmpty()) {
			Vertex u = q.poll();
			s.add(u);
			List<Edge> adjList = edges.get(u.getName());
			for (Edge e : adjList) {
				Vertex v = e.getNode2();
				Float weight = (Float) e.getProperty(WEIGHT);
				relax(u, v, weight, q);
			}
		}

		// New graph reconstruction
		Graph g = new Graph(directed);
		reconstructDijkstra(s, g, true);
		return g;
	}

	private float reconstructDijkstra(Set<Vertex> s, Graph g, boolean formatNodes) {
		float cost = 0.0f;
		// Add vertices and edges
		for (Vertex vertex : s) {
			// Skip if source
			if (((String) vertex.getProperty(PARENT)).equals(NIL))
				continue;

			Vertex node1 = vertices.get((String) vertex.getProperty(PARENT));
			Vertex node2 = vertex;
			String node1Id = (formatNodes ? generateVertexIdDijkstra(node1) : node1.getName());
			String node2Id = (formatNodes ? generateVertexIdDijkstra(node2) : node2.getName());

			Vertex auxNode1 = new Vertex(node1Id, node1.getProperties());
			Vertex auxNode2 = new Vertex(node2Id, node2.getProperties());
			List<Edge> adjList2 = getEdges().get(node1.getName());
			Map<String, Object> vertexData = new HashMap<String, Object>();
			for (Edge edge2 : adjList2) {
				if ((edge2.getNode2()).equals(node2)) {
					vertexData = edge2.getProperties();
					break;
				}
			}
			g.addEdge(auxNode1, auxNode2, vertexData);
			cost += (Float) vertexData.get(WEIGHT);
		}
		return cost;
	}

	private void initializeSingleSource(Map<String, Vertex> vertices, Vertex source) {
		for (Vertex vertex : vertices.values()) {
			vertex.setProperty(DISTANCE, Float.valueOf(Float.MAX_VALUE));
			vertex.setProperty(PARENT, NIL);
		}
		vertices.get(source.getName()).setProperty(DISTANCE, Float.valueOf(0.0f));
	}

	private void relax(Vertex u, Vertex v, float weight, Queue<Vertex> q) {
		if ((Float) v.getProperty(DISTANCE) > (Float) u.getProperty(DISTANCE) + weight) {
			v.setProperty(DISTANCE, Float.valueOf((Float) u.getProperty(DISTANCE) + weight));
			v.setProperty(PARENT, u.getName());
			q.offer(v);
		}
	}

	private String generateVertexIdDijkstra(Vertex vertex) {
		Float distance = (Float) vertex.getProperty(DISTANCE);
		return String.format("Nodo_%s(%.2f)", vertex.getName(), distance);
	}

	// Get vertex with max out degre
	public Vertex getVertexNameWithMaxOutDegree() {
		Entry<String, List<Edge>> entry = this.getEdges().entrySet().stream().max((first, second) -> {
			if (first.getValue().size() > second.getValue().size())
				return 1;
			else if (first.getValue().size() < second.getValue().size())
				return -1;
			return 0;
		}).get();

		return this.getVertices().get(entry.getKey());
	}

	// CLRS pag 631
	public Graph Kruskal_D() {
		// Result
		Graph a = new Graph(this.directed);
		float mstCost = 0.0f;
		// Make set
		for (Vertex v : this.vertices.values()) {
			makeSet(v);
		}
		// Sort all edges
		List<Edge> allEdges = this.getEdges().values().stream().flatMap(Collection::stream)
				.collect(Collectors.toList());
		Collections.sort(allEdges, new EdgeWeightComparator());

		for (Edge edge : allEdges) {
			Vertex u = edge.getNode1(), v = edge.getNode2();
			String uSet = findSet(u), vSet = findSet(v);
			if (!uSet.equals(vSet)) {
				a.addEdge(u, v, edge.getProperties());
				mstCost += (Float) edge.getProperty(WEIGHT);
				union(uSet, vSet);
			}
		}
		clearSet();
		System.out.printf("MST Kruskal_D cost: %.2f\n", mstCost);
		return a;
	}

	// <union-find alg.>
	private void union(String uSet, String vSet) {
		this.vertices.get(uSet).setProperty(PARENT, vSet);
	}

	private void makeSet(Vertex v) {
		v.setProperty(PARENT, v.getName());
	}

	private String findSet(Vertex node) {
		String parent = (String) node.getProperty(PARENT);
		if (parent.equals(node.getName()))
			return node.getName();
		else
			return findSet(this.vertices.get(parent));
	}

	private void clearSet() {
		this.vertices.values().stream().forEach(v -> v.getProperties().remove(PARENT));
	}
	// </union-find alg.>

	public Graph Kruskal_I() {
		// Result
		Graph a = new Graph(this.getVertices(), this.getEdges(), this.isDirected());
		float mstCost = 0.0f;
		// Sort all edges
		List<Edge> allEdges = a.getEdges().values().stream().flatMap(Collection::stream).collect(Collectors.toList());
		Collections.sort(allEdges, Collections.reverseOrder(new EdgeWeightComparator()));
		// We need to keep track of reversed edges when graph is undirected
		Set<String> seenEdges = new HashSet<>();
		// Iterate
		for (Edge edge : allEdges) {
			Vertex u = edge.getNode1(), v = edge.getNode2();
			if (!alreadySeen(seenEdges, edge)) {
				a.deleteEdge(u, v);
				if (!a.isConnected()) {
					a.addEdge(u, v, edge.getProperties());
					mstCost += (Float) edge.getProperty(WEIGHT);
					seenEdges.add(edge.getId());
				}
			}
		}

		System.out.printf("MST Kruskal_I cost: %.2f\n", mstCost);
		return a;
	}

	public boolean isConnected() {
		Graph dfs = this.DFS_I(this.getVertexNameWithMaxOutDegree());
		if (dfs.getEdges().size() == 0)
			return false;
		return dfs.getVertices().values().stream().allMatch(v -> ((Boolean) v.getProperty(VISITED)).booleanValue());
	}

	public void deleteEdge(Vertex u, Vertex v) {
		List<Edge> adjList = this.edges.get(u.getName());
		adjList.removeIf(edge -> edge.getId().equals(generateEdgeId(u, v)));
		if (!this.directed) { // delete reverse vertex
			adjList = this.edges.get(v.getName());
			adjList.removeIf(edge -> edge.getId().equals(generateEdgeId(v, u)));
		}
	}

	private boolean alreadySeen(Set<String> alreadySeenEdges, Edge edge) {
		return alreadySeenEdges.contains(generateEdgeId(edge.getNode2(), edge.getNode1()));
	}

	public Graph Prim() {
		// *** based on dijkstra implementation *** //
		Vertex source = getVertexNameWithMaxOutDegree();
		float mstCost = 0.0f;
		initializeSingleSource(vertices, source);

		// Priority queue keyed by distance
		Queue<Vertex> q = new PriorityQueue<>(vertices.size(), new VertexDistanceComparator());
		Set<Vertex> inMST = new HashSet<>(vertices.size());
		q.addAll(vertices.values());

		while (!q.isEmpty()) {
			Vertex u = q.poll();
			inMST.add(u);
			List<Edge> adjList = edges.get(u.getName());
			for (Edge e : adjList) {
				Vertex v = e.getNode2();
				Float weight = (Float) e.getProperty(WEIGHT);
				if (weight < (Float) v.getProperty(DISTANCE)) {
					// Update key of V
					v.setProperty(DISTANCE, weight);
					// Assing parent
					v.setProperty(PARENT, u.getName());
					// Insert into pq
					q.offer(v);
				}
			}
		}
		// New graph reconstruction
		Graph g = new Graph(directed);
		mstCost = reconstructDijkstra(inMST, g, false);
		System.out.printf("MST Prim      cost: %.2f\n", mstCost);
		return g;
	}

	public Graph Prim2() {
		Vertex source = getVertexNameWithMaxOutDegree();
		// *** CLRS implementation *** //
		initializeSingleSource(vertices, source);
		Set<Vertex> s = new HashSet<>();
		// Priority queue keyed by distance
		Queue<Vertex> q = new PriorityQueue<>(vertices.size(), new VertexDistanceComparator());
		q.addAll(vertices.values());

		while (!q.isEmpty()) {
			Vertex u = q.poll();
			s.add(u);
			List<Edge> adjList = edges.get(u.getName());
			for (Edge e : adjList) {
				Vertex v = vertices.get(e.getNode2Name());
				Float weight = (Float) e.getProperty(WEIGHT);
				if (q.contains(v) && weight < (Float) v.getProperty(DISTANCE)) {
					v.setProperty(DISTANCE, Float.valueOf(weight));
					v.setProperty(PARENT, u.getName());
					q.offer(v);
				}
			}
		}

		// New graph reconstruction
		Graph g = new Graph(directed);
		float mstCost = reconstructDijkstra(s, g, true);
		System.out.printf("MST Prim      cost: %.2f\n", mstCost);
		return g;
	}

	public Graph Prim3() {
		Map<String, Boolean> mstset = new HashMap<>();
		Map<String, String> parent = new HashMap<>();
		float mstCost = 0.0f;
		for (String vertexName : this.getVertices().keySet()) {
			mstset.put(vertexName, false);
			parent.put(vertexName, NIL);
			this.getVertices().get(vertexName).setProperty("KEY", Float.POSITIVE_INFINITY);
		}

		Vertex source = this.getVertices().entrySet().iterator().next().getValue();
		source.setProperty("KEY", Float.valueOf(0.0f));

		TreeSet<Vertex> queue = new TreeSet<>((node1, node2) -> {
			Float node1Key = (Float) node1.getProperty("KEY");
			Float node2Key = (Float) node2.getProperty("KEY");
			return Float.compare(node1Key, node2Key);
		});

		for (Vertex vertex : this.getVertices().values()) {
			queue.add(vertex);
		}

		while (!queue.isEmpty()) {
			Vertex node1 = queue.pollFirst();
			mstset.put(node1.getName(), true);
			for (Edge edge : this.getEdges().get(node1.getName())) {
				Vertex node2 = edge.getNode2();
				if (mstset.get(node2.getName()) == false) {
					if ((Float) node2.getProperty("KEY") > (Float) edge.getProperty(WEIGHT)) {
						queue.remove(node2);
						node2.setProperty("KEY", (Float) edge.getProperty(WEIGHT));
						queue.add(node2);
						parent.put(node2.getName(), node1.getName());
					}
				}
			}
		}

		Graph result = new Graph(this.getVertices(), this.isDirected());
		// Rebuild graph
		for (Entry<String, String> parentChild : parent.entrySet()) {
			Vertex childVertex = result.getVertices().get(parentChild.getKey());
			Vertex parentVertex = result.getVertices().get(parentChild.getValue());
			if (parentVertex != null) {
				List<Edge> adjList = this.getEdges().get(parentVertex.getName());
				Edge edge = adjList.stream().filter(e -> generateEdgeId(parentVertex, childVertex).equals(e.getId())).findFirst().get();
				result.addEdge(childVertex, parentVertex, edge.getProperties());
				mstCost += (Float) edge.getProperty(WEIGHT);
			}
			//

		}
		System.out.printf("MST Prim      cost: %.2f\n", mstCost);
		return result;
	}

	// To file
	public void writeToFile(String path, String filename) throws IOException {
		Files.write(Paths.get(path, filename), this.toString().getBytes());
	}

	// Getters and setters
	public Map<String, Vertex> getVertices() {
		return vertices;
	}

	public Map<String, List<Edge>> getEdges() {
		return edges;
	}

	public boolean isDirected() {
		return directed;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		// Directed or undirected
		if (directed)
			str.append("digraph G{\n");
		else
			str.append("graph G{\n");

		//
		List<Edge> allEdges = new ArrayList<>();
		List<Edge> auxEdges = new ArrayList<>();
		Collection<List<Edge>> edgeList = edges.values();
		for (List<Edge> adjLists : edgeList) {
			for (Edge edge : adjLists) {
				allEdges.add(edge);
			}
		}
		// Omit reverse edges in undirected graphs
		if (!directed) {
			for (Edge edge : allEdges) {
				Edge reverseEdge = generateReverseEdge(edge);
				if (!auxEdges.contains(reverseEdge)) {
					auxEdges.add(edge);
				}
			}
		} else {
			auxEdges = allEdges;
		}

		// Edges to string
		for (Edge edge : auxEdges) {
			str.append(edge.getId());
			if (edge.getProperties().containsKey(WEIGHT)) {
				str.append(String.format(" [label=%.2f]", (Float) edge.getProperty(WEIGHT)));
			}
			str.append(";\n");
		}

		str.append("}");
		return str.toString();
	}

	private class EdgeWeightComparator implements Comparator<Edge> {
		@Override
		public int compare(Edge first, Edge second) {
			Float firstF = (Float) first.getProperty(WEIGHT), secondF = (Float) second.getProperty(WEIGHT);
			return Float.compare(firstF, secondF);
		}
	}

	private class VertexDistanceComparator implements Comparator<Vertex> {
		@Override
		public int compare(Vertex first, Vertex second) {
			Float firstF = (Float) first.getProperty(DISTANCE), secondF = (Float) second.getProperty(DISTANCE);
			return Float.compare(firstF, secondF);
		}
	}

}
