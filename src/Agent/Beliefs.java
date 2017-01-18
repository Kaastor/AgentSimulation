package Agent;


import AgentDesires.DesireModule;
import Environment.Graph;
import Environment.GraphVertex;
import Environment.RandomGenerator;
import lombok.Data;
import lombok.ToString;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private List<GraphVertex> verticesAround;
    private boolean collision;
    private List<Integer> shopsToVisit;

    Beliefs(Agent parentAgent, Graph graphMap){
        this.parentAgent = parentAgent;
        this.decisionModule = parentAgent.getDecisionModule();
        this.desireModule = parentAgent.getDesireModule();
        this.graphMap = graphMap;
        this.graphCells = graphMap.getGraphCells();
        this.graphRegions = graphMap.getGraphRegions();
        this.verticesAround = new ArrayList<>();
        this.KnowledgeOfArea = RandomGenerator.getInstance().uniformInt(0, 1);
        this.collision = false;
        initializeBeliefs(graphMap);
    }

    private void initializeBeliefs(Graph graphMap){
        this.shopsToVisit = initializeShopsToVisit(graphMap.getShopsNumber());
    }

    public void perceptualProcessor(){
        updateDirection();
        lookAround();
        lookForCollision();

        if(decisionModule.getIntention() == null){
            desireModule.cognitiveProcessor();
        }
        else if(collision){
            //TODO collision ->realtimeplanner- ustawienie nowego planu + nextPosition, jak metoda wroci do WalkProcess, bedzie juz miała nowe pole.
        }
        else{
            decisionModule.executePlan();
        }
    }

    private void lookForCollision(){
        if(parentAgent.getAgentState() == AgentState.WALK) {
            if (parentAgent.getNextPosition().isOccupied() || parentAgent.getNextPosition().isReserved()) {
                setCollision(true);
                parentAgent.setAgentState(AgentState.COLLISION);
            }
        }
    }

    private void lookAround(){
        List<GraphVertex> verticesAround = new ArrayList<>();
        Set<DefaultEdge> aroundEdges = getGraphCells().edgesOf(getGraphMap().getVertex(parentAgent.getPosition()));
        for(DefaultEdge edge : aroundEdges){
            verticesAround.add(getGraphCells().getEdgeSource(edge));
            verticesAround.add(getGraphCells().getEdgeTarget(edge));
        }
        verticesAround = verticesAround.parallelStream()
                .distinct()
                .collect(Collectors.toList());
        setVerticesAround(verticesAround);
    }

    private void updateDirection(){
        if(parentAgent.getAgentState() == AgentState.WALK)
            parentAgent.getMovingDirection().update(parentAgent.getPosition(), parentAgent.getNextPosition());
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
}
