package AgentDesires;

import Agent.Agent;
import Agent.AgentState;
import Environment.GraphVertex;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LeaveShoppingCenter extends Desire{

    LeaveShoppingCenter(Agent agent){
        super(agent);
        setFinalPosition(agent.getBeliefs().getGraphMap().getExitPosition());
    }

    @Override
    public void scenario() {
        setPlan(new Plan(this, getParentAgent().getPreviousRegionPosition()));
        getPlan().createShortestTopPath(getFinalPosition());
        getPlan().createPath();
    }

    @Override
    @SneakyThrows
    public void realTimePlanning() {
        if(getParentAgent().getAgentState() != AgentState.COLLISION){
            if(getParentAgent().getDesireModule().noOtherDesires()) {
                getParentAgent().getDecisionModule().executePlan();
            }
            else {
                this.terminate();
                getParentAgent().getBeliefs().perceptualProcessor();
            }
        }
        else {
            getParentAgent().avoidCollision();
        }
    }

    @Override
    public void action() {
        if(getParentAgent().getPosition().equals(getFinalPosition()))
            finalAction();
    }

    private void finalAction() {
        getParentAgent().setLeaving(true);
        this.terminate();
    }
}
