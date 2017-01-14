package AgentEvents;

import Agent.Agent;
import Agent.AgentState;
import Environment.GraphVertex;
import dissim.simspace.core.SimControlException;
import dissim.simspace.process.BasicSimProcess;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "parentAgent", callSuper = false)
public class WalkProcess extends BasicSimProcess<Agent, Object>{

    private Agent parentAgent;

    public WalkProcess(Agent parentAgent) throws SimControlException{
        super(parentAgent);
        this.parentAgent = getSimEntity();
    }

    @Override
    public double controlStateTransitions() {
        parentAgent.setAgentState(AgentState.WALK);
        walkToNextPositionIfSet();
        setNextPositionOnMap();
        return parentAgent.getAgentSpeed();
    }

    private void walkToNextPositionIfSet(){
        if(parentAgent.getNextPosition() != null &&
                !(parentAgent.getNextPosition().equals(parentAgent.getPosition()))){
            parentAgent.moveToNextPosition();
        }
    }

    private void setNextPositionOnMap(){
        if(parentAgent.getNotPlannedPath().hasNext()) {
            GraphVertex next = parentAgent.getNotPlannedPath().next();
            parentAgent.setNextPosition(next);
        }
    }
}
