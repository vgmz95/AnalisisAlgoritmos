package com.cic.ada.Grafo;

import org.json.JSONObject;

class Edge {
    String id;
    Vertex Node1, Node2;
    JSONObject data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vertex getNode1() {
        return Node1;
    }

    public void setNode1(Vertex node1) {
        Node1 = node1;
    }

    public Vertex getNode2() {
        return Node2;
    }

    public void setNode2(Vertex node2) {
        Node2 = node2;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public Edge(String id, Vertex node1, Vertex node2, JSONObject data) {
        this.id = id;
        Node1 = node1;
        Node2 = node2;
        this.data = data;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Node1 == null) ? 0 : Node1.hashCode());
        result = prime * result + ((Node2 == null) ? 0 : Node2.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        if (Node1 == null) {
            if (other.Node1 != null)
                return false;
        } else if (!Node1.equals(other.Node1))
            return false;
        if (Node2 == null) {
            if (other.Node2 != null)
                return false;
        } else if (!Node2.equals(other.Node2))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
}