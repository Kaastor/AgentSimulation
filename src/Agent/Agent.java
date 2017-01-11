package Agent;


import AgentEvents.WalkProcess;
import Environment.GraphNode;

import Environment.Map;
import Environment.RandomGenerator;
import dissim.broker.IEventPublisher;
import dissim.simspace.core.BasicSimEntity;
import dissim.simspace.core.SimModel;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public class Agent extends BasicSimEntity {

    private int id;
    private double agentSpeed;
    private GraphNode positionOnMap;
    private GraphNode nextPositionOnMap;
    private AgentState agentState;
    private int KnowledgeOfArea;
    private Map parentMap;

    private WalkProcess walkProcess;

    @SneakyThrows
    public Agent(Map parentMap, int id, GraphNode positionOnMap){
        super(SimModel.getInstance().getCommonSimContext());
        this.parentMap = parentMap;
        this.id = id;
        this.agentSpeed = RandomGenerator.getInstance().exponential(3.0);
        this.positionOnMap = positionOnMap;
        this.nextPositionOnMap = positionOnMap;
        this.agentState = AgentState.NOP;

        this.walkProcess = new WalkProcess(this);
    }

    public void collisionCheck(){

    }

    public void moveAgent(GraphNode to){
        setPositionOnMap(to);
    }

    public void reservePosition(GraphNode nextPosition){
        setNextPositionOnMap(nextPosition);
    }

}
