package AgentEvents;

import Agent.Agent;
import Agent.AgentState;
import Environment.WorldCoordinates;
import dissim.simspace.core.SimControlException;
import dissim.simspace.process.BasicSimProcess;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "parentAgent", callSuper = false)
public class WalkProcess extends BasicSimProcess<Agent, Object>{

    private Agent parentAgent;
    private WorldCoordinates currentPositionOnMap;
    private WorldCoordinates nextPositionOnMap;


    public WalkProcess(Agent parentAgent) throws SimControlException{
        super(parentAgent);
        this.parentAgent = getSimEntity();
        this.currentPositionOnMap = parentAgent.getPositionOnMap();
        this.nextPositionOnMap = parentAgent.getPositionOnMap();
    }

    @Override
    public double controlStateTransitions() {
        parentAgent.setAgentState(AgentState.WALK);

        walkToNewPositionIfIsSet();

        int nextX = currentPositionOnMap.getX();
        int nextY = currentPositionOnMap.getY();
        nextPositionOnMap.setX(++nextX);
        nextPositionOnMap.setY(++nextY);

        System.out.println(parentAgent.getId() + " " +currentPositionOnMap);
        reserveNewPositionOnMap();

        return parentAgent.getAgentSpeed();
    }

    private void walkToNewPositionIfIsSet(){
        if(currentPositionOnMap.getX() != nextPositionOnMap.getX()
                || currentPositionOnMap.getY() != nextPositionOnMap.getY()){

            parentAgent.moveAgent(nextPositionOnMap);
        }
    }

    private void reserveNewPositionOnMap(){
        parentAgent.reservePosition(nextPositionOnMap);
    }
}
