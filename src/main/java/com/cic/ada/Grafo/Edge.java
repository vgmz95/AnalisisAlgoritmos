package com.cic.ada.Grafo;

import java.util.HashMap;
import java.util.Map;

public class Edge {
	private String id;
	private Vertex node1, node2;
	private final Map<String, Object> properties;

	public Edge(String id, Vertex node1, Vertex node2, Map<String, Object> properties) {
		this.id = id;
		this.node1 = node1;
		this.node2 = node2;
		this.properties = new HashMap<>(properties);
	}

	public Edge(String id, Vertex node1, Vertex node2) {
		this.id = id;
		this.node1 = node1;
		this.node2 = node2;
		this.properties = new HashMap<String, Object>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Vertex getNode1() {
		return node1;
	}

	public void setNode1(Vertex node1) {
		this.node1 = node1;
	}

	public Vertex getNode2() {
		return node2;
	}

	public void setNode2(Vertex node2) {
		this.node2 = node2;
	}

	public void setProperty(String key, Object value) {
		this.properties.put(key, value);
	}

	public Object getProperty(String key) {
		return this.properties.get(key);
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((node1 == null) ? 0 : node1.hashCode());
		result = prime * result + ((node2 == null) ? 0 : node2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (node1 == null) {
			if (other.node1 != null)
				return false;
		} else if (!node1.equals(other.node1))
			return false;
		if (node2 == null) {
			if (other.node2 != null)
				return false;
		} else if (!node2.equals(other.node2))
			return false;
		return true;
	}

}
