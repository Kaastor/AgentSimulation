package Agent;


import AgentEvents.WalkProcess;
import Environment.GraphNode;

import Environment.CellMap;
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
    private CellMap parentCellMap;

    private WalkProcess walkProcess;

    @SneakyThrows
    public Agent(CellMap parentCellMap, int id, GraphNode positionOnMap){
        super(SimModel.getInstance().getCommonSimContext());
        this.parentCellMap = parentCellMap;
        this.id = id;
        this.agentSpeed = RandomGenerator.getInstance().exponential(3.0);
        this.positionOnMap = positionOnMap;
        this.nextPositionOnMap = positionOnMap;
        this.agentState = AgentState.NOP;

        this.walkProcess = new WalkProcess(this);
        walkProcess.start();
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
