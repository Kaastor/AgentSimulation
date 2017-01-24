package AgentDesires;

import Agent.Agent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
    public void realTimePlanning() {
        if(!getParentAgent().getBeliefs().isCollision()){
            if(getParentAgent().getDesireModule().noOtherDesires()) {
                getParentAgent().getDecisionModule().executePlan();
            }
            else {
                this.terminate();
                getParentAgent().getBeliefs().perceptualProcessor();
            }
        }
        else{
//            getPlan().createPath();
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
