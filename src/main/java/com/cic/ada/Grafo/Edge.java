package com.cic.ada.Grafo;

import org.json.JSONObject;

public class Edge {
    private String id;
    private String node1Name, node2Name;
    private JSONObject data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNode1Name() {
        return node1Name;
    }

    public void setNode1Name(String node1Name) {
        this.node1Name = node1Name;
    }

    public String getNode2Name() {
        return node2Name;
    }

    public void setNode2Name(String node2) {
        this.node2Name = node2;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public Edge(String id, String node1Name, String node2Name, JSONObject data) {
        this.id = id;
        this.node1Name = node1Name;
        this.node2Name = node2Name;
        this.data = data;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((node1Name == null) ? 0 : node1Name.hashCode());
        result = prime * result + ((node2Name == null) ? 0 : node2Name.hashCode());
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
        if (node1Name == null) {
            if (other.node1Name != null)
                return false;
        } else if (!node1Name.equals(other.node1Name))
            return false;
        if (node2Name == null) {
            if (other.node2Name != null)
                return false;
        } else if (!node2Name.equals(other.node2Name))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }  

    
}