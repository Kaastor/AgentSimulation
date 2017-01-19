package AgentEvents;

import Agent.Agent;
import Agent.AgentState;
import dissim.simspace.core.BasicSimStateChange;
import dissim.simspace.core.SimControlException;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "parentAgent", callSuper = false)
public class WalkEvent extends BasicSimStateChange<Agent, Object> {

    private Agent parentAgent;

    public WalkEvent(Agent parentAgent, double agentSpeed) throws SimControlException {
        super(parentAgent, agentSpeed);
        this.parentAgent = getSimEntity();
    }

    @Override
    public void transition() {
        parentAgent.setAgentState(AgentState.WALK);
        walkToNextPositionIfSet();
        setNextPositionOnMap();
        parentAgent.getBeliefs().perceptualProcessor();
    }

    private void walkToNextPositionIfSet() {
        if (!noNextPosition() && !(parentAgent.getNextPosition().equals(parentAgent.getPosition()))) {
            parentAgent.setPreviousPosition(parentAgent.getPosition());
            parentAgent.moveToNextPosition();
        }
    }

    private void setNextPositionOnMap() {
        parentAgent.setNextPosition(parentAgent.getDecisionModule().getIntention().getPlan().getNextPosition());
        if(noNextPosition()){
            parentAgent.setAgentState(AgentState.STANDING);
        }
    }

    private boolean noNextPosition(){
        return parentAgent.getNextPosition() == null;
    }
}

