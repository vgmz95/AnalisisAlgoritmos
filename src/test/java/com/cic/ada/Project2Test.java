package com.cic.ada;

import java.io.IOException;
import com.cic.ada.Grafo.Graph;
import com.cic.ada.Grafo.Vertex;
import org.junit.Test;

/**
 * Unit test for graph library.
 */
public class Project2Test {

       /* Write to file tests */

       int few = 30; // Few nodes (<50)
       int many = 100; // Many nodes (<100)
       int lots = 500; // Lots of nodes
       String path = "C:\\Users\\victo\\Documents\\grafos";
       String fileExt = ".gv";

    @Test
    public void DFS_BFS_Test() throws IOException {
        ErdosRenyiBFSTest();
        //GilbertFileTest
        //GeographicFileTest
        //BarabasiAlbertFileTest

    }

    @Test
    public void ErdosRenyiBFSTest() throws IOException {
        Graph[] erdosRenyiGraphs = {Graph.generateErdosRenyiGraph(few, 100, false, false),
                Graph.generateErdosRenyiGraph(many, 200, false, false),
                Graph.generateErdosRenyiGraph(lots, 300, false, false),};
        for (Graph graph : erdosRenyiGraphs) {
            Vertex source = graph.getVertexByName(graph.getVertexNameWithMaxOutDegree());
            graph.writeToFile(path, "1_ErdosRenyi-" + graph.getVertices().size() + fileExt);
            graph.BFS(source).writeToFile(path,
                    "1_ErdosRenyi-BFS-" + graph.getVertices().size() + fileExt);
            graph.DFS_I(source).writeToFile(path,
                    "1_ErdosRenyi-DFS_I-" + graph.getVertices().size() + fileExt);
            graph.DFS_R(source).writeToFile(path,
                    "1_ErdosRenyi-DFS_R-" + graph.getVertices().size() + fileExt);
        }
    }


}
