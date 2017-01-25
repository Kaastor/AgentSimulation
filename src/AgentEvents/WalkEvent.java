package AgentEvents;

import Agent.Agent;
import Agent.AgentState;
import dissim.simspace.core.BasicSimStateChange;
import dissim.simspace.core.SimControlException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

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
        System.out.println(parentAgent.getId() + " Walking: " + parentAgent.getPreviousPosition() + " " + parentAgent.getPosition() + " " + parentAgent.getNextPosition());
        if(parentAgent.getDecisionModule().intentionNotTerminated()){
            walkToNextPositionIfSet();
            setNextPositionOnMap();
        }
        System.out.println( "654");
        parentAgent.getBeliefs().perceptualProcessor();
    }

    private void walkToNextPositionIfSet() {
        if (!noNextPosition() && !(parentAgent.getNextPosition().equals(parentAgent.getPosition()))) {
            parentAgent.setPreviousPosition(parentAgent.getPosition());
            parentAgent.moveToNextPosition();
        }
    }

    @SneakyThrows
    private void setNextPositionOnMap() {
        parentAgent.reserveNextPosition(parentAgent.getDecisionModule().getIntention().getPlan().getNextPosition());
        System.out.println( "1");
        if (noNextPosition() && getParentAgent().getAgentState() != AgentState.LEAVING) {
            parentAgent.setAgentState(AgentState.STANDING);
        }
        System.out.println( "2");
    }

    private boolean noNextPosition(){
        return parentAgent.getNextPosition() == null;
    }
}

