package Agent;


import Environment.Graph;
import Environment.GraphVertex;
import lombok.Data;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

@Data
class Beliefs {
    private int KnowledgeOfArea;
    private UndirectedGraph<GraphVertex, DefaultEdge> graphCells;
    private UndirectedGraph<GraphVertex, DefaultEdge> graphRegions;

    Beliefs(Graph graphMap){
        this.graphCells = graphMap.getGraphCells();
        this.graphRegions = graphMap.getGraphRegions();
        this.KnowledgeOfArea = 1;
    }

}
