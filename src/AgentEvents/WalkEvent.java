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

    public WalkEvent(Agent parentAgent, double agentSpeed) throws SimControlException {
        super(parentAgent, agentSpeed);
        this.parentAgent = getSimEntity();
    }

    @Override
    public void transition() {
        parentAgent.setAgentState(AgentState.WALK);

        walkToNextPositionIfSet();
        setNextPositionOnMap();
//        System.out.println("WALKING TO..position NOW" + parentAgent.getPosition());
//        System.out.println("WALKING TO..position NEXT" + parentAgent.getNextPosition());

        parentAgent.getBeliefs().perceptualProcessor();
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
        parentAgent.setNextPosition(parentAgent.getDecisionModule().getIntention().getPlan().getNextPosition());
    }

    private boolean noNextPosition(){
        return parentAgent.getNextPosition() == null;
    }
}

