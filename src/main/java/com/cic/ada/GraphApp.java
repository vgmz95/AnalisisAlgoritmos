package com.cic.ada;

import java.io.IOException;
import java.util.stream.Stream;

import com.cic.ada.Grafo.Graph;

/**
 * 
 *
 */
public class GraphApp {
    public static void main(String[] args) {

        
        int few = 40; // Few nodes (<50)
        int many = 100; // Many nodes (<100)
        int lots = 500 ;  //Lots of nodes
        String path="C:\\Users\\victo\\Documents\\grafos";
        String fileExt = ".gv";


        //Erdos-Renyi
        Graph[] erdosRenyiGraphs = { 
            Graph.generateErdosRenyiGraph(few, 100, false, true), 
            Graph.generateErdosRenyiGraph(many, 350, false, true),
            Graph.generateErdosRenyiGraph(lots, 700, false, true),
        };
        Stream.of(erdosRenyiGraphs).forEach(graph->{
            try {
                graph.writeToVizFile(path, "ErdosRenyi-" + graph.getVertices().size()+fileExt);
            } catch (IOException e) {                
                e.printStackTrace();
            }
        });        
        
        //Gilbert
        Graph[] GilbertGraphs = { 
            Graph.generateGilbertGraph(few, 0.3, false, true), 
            Graph.generateGilbertGraph(many, 0.3, false, true),
            Graph.generateGilbertGraph(lots, 0.02, false, true),
        };
        Stream.of(GilbertGraphs).forEach(graph->{
            try {
                graph.writeToVizFile(path, "Gilbert-" + graph.getVertices().size()+fileExt);
            } catch (IOException e) {                
                e.printStackTrace();
            }
        });   

        //Geographical
        Graph[] GeoGraphs = { 
            Graph.generateGeographicGraph(few, 0.5, false, true), 
            Graph.generateGeographicGraph(many, 0.2, false, true),
            Graph.generateGeographicGraph(lots, 0.08, false, true),
        };
        Stream.of(GeoGraphs).forEach(graph->{
            try {
                graph.writeToVizFile(path, "Geograph-" + graph.getVertices().size()+fileExt);
            } catch (IOException e) {                
                e.printStackTrace();
            }
        });   
        
        //Barabasi-Albert graphs
        Graph[] barabasiGraphs = { 
            Graph.generateBarabasiAlbertGraph(few, 5, false, true), 
            Graph.generateBarabasiAlbertGraph(many, 4, false, true),
            Graph.generateBarabasiAlbertGraph(lots, 3, false, true),
        };
        Stream.of(barabasiGraphs).forEach(graph->{
            try {
                graph.writeToVizFile(path, "BarabasiAlbert-" + graph.getVertices().size()+fileExt);
            } catch (IOException e) {                
                e.printStackTrace();
            }
        });

        System.out.println("Graphs generated to: "+path);

    }
}
