package Agent;


import Environment.Graph;
import Environment.GraphVertex;
import Environment.RandomGenerator;
import lombok.Data;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.List;


@Data
class Beliefs {
    private int KnowledgeOfArea;
    private UndirectedGraph<GraphVertex, DefaultEdge> graphCells;
    private UndirectedGraph<GraphVertex, DefaultEdge> graphRegions;
    private List<GraphVertex> aroundVertices;
    private boolean collision;
    private List<Integer> shopsToVisit;

    Beliefs(Graph graphMap){
        this.graphCells = graphMap.getGraphCells();
        this.graphRegions = graphMap.getGraphRegions();
        this.aroundVertices = new ArrayList<>();
        this.KnowledgeOfArea = 1;
        this.collision = false;
        initializeBeliefs(graphMap);
    }

    private void initializeBeliefs(Graph graphMap){
        this.shopsToVisit = initializeShopsToVisit(graphMap.getShopsNumber());
    }
    
    private List<Integer> initializeShopsToVisit(int shopNumber){
        List<Integer> shopsToVisit = new ArrayList<>();
        int numberShopsToVisit = RandomGenerator.getInstance().uniformInt(0, shopNumber);
        for(int i = 0; i < numberShopsToVisit; i++){
            int shopToVisit = RandomGenerator.getInstance().uniformInt(0, shopNumber);
            shopsToVisit.add(shopToVisit);
        }
        return shopsToVisit;
    }

    public void perceptualProcessor(){

    }

}
