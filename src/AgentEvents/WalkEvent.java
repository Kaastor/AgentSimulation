package AgentEvents;

import Agent.Agent;
import Agent.AgentState;
import Environment.GraphVertex;
import dissim.simspace.core.BasicSimStateChange;
import dissim.simspace.core.SimControlException;
import dissim.simspace.process.BasicSimProcess;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "parentAgent", callSuper = false)
public class WalkEvent extends BasicSimStateChange<Agent, Object> {

    private Agent parentAgent;

    public WalkEvent(Agent parentAgent) throws SimControlException {
        super(parentAgent);
        this.parentAgent = getSimEntity();
    }

    @Override
    public void transition() {
        parentAgent.setAgentState(AgentState.WALK);
        walkToNextPositionIfSet();
        setNextPositionOnMap();
        parentAgent.observeTheEnvironment();
    }

    private void walkToNextPositionIfSet() {
        if (!noNextPosition() && !(parentAgent.getNextPosition().equals(parentAgent.getPosition()))) {
            parentAgent.setPreviousPosition(parentAgent.getPosition());
            parentAgent.moveToNextPosition();
        }
        if(noNextPosition()){
            parentAgent.setAgentState(AgentState.STANDING);
        }
    }

    private void setNextPositionOnMap() {
        parentAgent.setNextPosition(parentAgent.getIntention().getPlan().getNextPosition());
    }

    private boolean noNextPosition(){
        return parentAgent.getNextPosition() == null;
    }
}

