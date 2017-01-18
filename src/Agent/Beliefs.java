package Agent;


import Environment.Graph;
import Environment.GraphVertex;
import Environment.RandomGenerator;
import lombok.Data;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


@Data
public class Beliefs {

    private Agent parentAgent;
    private int KnowledgeOfArea;
    private Graph graphMap;
    private UndirectedGraph<GraphVertex, DefaultEdge> graphCells;
    private UndirectedGraph<GraphVertex, DefaultEdge> graphRegions;
    private List<GraphVertex> verticesAround;
    private boolean collision;
    private Stack<Integer> shopsToVisit;

    Beliefs(Agent parentAgent, Graph graphMap){
        this.parentAgent = parentAgent;
        this.graphMap = graphMap;
        this.graphCells = graphMap.getGraphCells();
        this.graphRegions = graphMap.getGraphRegions();
        this.verticesAround = new ArrayList<>();
        this.KnowledgeOfArea = 1;
        this.collision = false;
        initializeBeliefs(graphMap);
    }

    private void initializeBeliefs(Graph graphMap){
        this.shopsToVisit = initializeShopsToVisit(graphMap.getShopsNumber());
    }
    
    private Stack<Integer> initializeShopsToVisit(int shopNumber){
        Stack<Integer> shopsToVisit = new Stack<>();
        int numberShopsToVisit = RandomGenerator.getInstance().uniformInt(0, shopNumber);
        for(int i = 0; i < numberShopsToVisit; i++){
            int shopToVisit = RandomGenerator.getInstance().uniformInt(0, shopNumber);
            shopsToVisit.add(shopToVisit);
        }
        return shopsToVisit;
    }

    public void perceptualProcessor(){
        if(collision){

        }
    }

    public void cognitiveProcessor(){

    }

}
