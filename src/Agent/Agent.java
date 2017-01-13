package Agent;


import AgentEvents.WalkProcess;
import Environment.WorldCoordinates;

import Environment.RandomGenerator;
import dissim.simspace.core.BasicSimEntity;
import dissim.simspace.core.SimModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

@Data
@EqualsAndHashCode(callSuper = false)
public class Agent extends BasicSimEntity {

    private int id;
    private double agentSpeed;
    private WorldCoordinates positionOnMap;
    private WorldCoordinates nextPositionOnMap;
    private AgentState agentState;
    private int KnowledgeOfArea;

    private WalkProcess walkProcess;

    @SneakyThrows
    public Agent(int id, WorldCoordinates positionOnMap){
        super(SimModel.getInstance().getCommonSimContext());
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

    public void moveAgent(WorldCoordinates to){
        setPositionOnMap(to);
    }

    public void reservePosition(WorldCoordinates nextPosition){
        setNextPositionOnMap(nextPosition);
    }

}
