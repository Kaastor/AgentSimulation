package AgentDesires;

import Agent.Agent;
import Agent.AgentState;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class LeaveShoppingCenter extends Desire{

    LeaveShoppingCenter(Agent agent){
        super(agent);
        setFinalPosition(agent.getBeliefs().getGraphMap().getExitPosition());
    }

    @Override
    public void scenario() {
        setPlan(new Plan(this, getParentAgent().getPreviousRegionPosition())); // to moze byc w Desire
        getPlan().createShortestTopPath(getFinalPosition());
        System.out.println("Po scenario");
    }

    @Override
    public void realTimePlanning() {
        System.out.println("RT planning");
        getPlan().createPath();
    }

    @Override
    public void action() {
        if(getParentAgent().getPosition().equals(getFinalPosition()))
            finalAction();
    }

    @Override
    public void finalAction() {
        getParentAgent().setLeaving(true);
        this.terminate();
    }
}
