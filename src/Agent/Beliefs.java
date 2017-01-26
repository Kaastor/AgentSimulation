package Agent;


import AgentDesires.DesireModule;
import Environment.Graph;
import Environment.GraphVertex;
import Environment.RandomGenerator;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"parentAgent", "decisionModule", "desireModule"})
public class Beliefs {

    private Agent parentAgent;
    private DecisionModule decisionModule;
    private DesireModule desireModule;

    private int KnowledgeOfArea;
    private Graph graphMap;
    private UndirectedGraph<GraphVertex, DefaultEdge> graphCells;
    private UndirectedGraph<GraphVertex, DefaultEdge> graphRegions;
    private List<GraphVertex> closeVerticesAround;
    private List<GraphVertex> furtherVerticesAround;
    private boolean collision;
    private List<Integer> shopsToVisit;

    Beliefs(Agent parentAgent, Graph graphMap){
        this.parentAgent = parentAgent;
        this.decisionModule = parentAgent.getDecisionModule();
        this.desireModule = parentAgent.getDesireModule();
        this.graphMap = graphMap;
        this.graphCells = graphMap.getGraphCells();
        this.graphRegions = graphMap.getGraphRegions();
        this.closeVerticesAround = new ArrayList<>();
        this.furtherVerticesAround = new ArrayList<>();
        this.KnowledgeOfArea = 1;
        this.collision = false;
        initializeBeliefs();
    }

    private void initializeBeliefs(){
        this.shopsToVisit = initializeShopsToVisit();
    }

    @SneakyThrows
    public void perceptualProcessor() {
        parentAgent.observeEnvironment();
        if (!parentAgent.isLeaving()) {
            if (decisionModule.getIntention() == null) {
                desireModule.cognitiveProcessor();
            } else if (collision) {
                parentAgent.setAgentState(AgentState.COLLISION);

                decisionModule.realTimePlanning();
            }
            else {
                decisionModule.realTimePlanning();
            }
        } else {
            getParentAgent().leaveShoppingCenter();
        }
    }

    private List<Integer> initializeShopsToVisit(){
        shopsToVisit = new ArrayList<>();
        int numberShopsToVisit = RandomGenerator.getInstance().uniformInt(1, Graph.SHOPS_NUMBER);
        for(int i = 0; i < numberShopsToVisit; i++){
            int shopToVisit = RandomGenerator.getInstance().uniformInt(0, Graph.SHOPS_NUMBER);
            if(!shopsToVisit.contains(shopToVisit))
                shopsToVisit.add(shopToVisit);
        }
        return shopsToVisit;
    }
}
