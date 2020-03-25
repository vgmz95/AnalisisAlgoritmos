package com.cic.ada;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.cic.ada.Grafo.Edge;
import com.cic.ada.Grafo.Graph;

import org.junit.Test;

public class Project3Test {

	@Test
	public void testRandomEdges(){
		Graph graph = Graph.generateBarabasiAlbertGraph(30, 12, false, false);
		graph.randomEdgeValues(0, 20);

		// Print edges with weights
		Set<Entry<String, List<Edge>>> entrySet = graph.getEdges().entrySet();
		for (Entry<String, List<Edge>> entry : entrySet){
			for(Edge edge: entry.getValue()){
				System.out.println(edge.getId() + " peso:"+ edge.getData().getDouble("weight"));
			}		
			
		}
	}


}