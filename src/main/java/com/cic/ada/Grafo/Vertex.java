package com.cic.ada.Grafo;


import org.json.JSONObject;

public class Vertex{
    private String name;
    private JSONObject data;

    public Vertex(String name) {
        this.name = name;
        this.data = new JSONObject();
    }

    public Vertex(String name, JSONObject data) {
        this.name = name;
        this.data = data;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
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

    //Layer for BFS
    public int getLayer(){
        return this.getData().optInt("distance", -1);
    }   
    

}