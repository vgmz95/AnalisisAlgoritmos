package com.cic.ada.Grafo;

import java.util.HashMap;
import java.util.Map;

public class Vertex {
	private String name;
	private final Map<String, Object> properties;

	public Vertex(String name) {
		this.name = name;
		this.properties = new HashMap<String, Object>();
	}

	public Vertex(String name, Map<String, Object> properties) {
		this.name = name;
		this.properties = new HashMap<>(properties);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Vertex other = (Vertex) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
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
	public String toString() {
		StringBuilder strProperties = new StringBuilder();
		properties.forEach((key, value) -> strProperties.append(key + ":" + value + "\n"));
		return "Vertex [name=" + name + ", properties=" + strProperties.toString() + "]";
	}

}