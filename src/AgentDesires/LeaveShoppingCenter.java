package AgentDesires;

import Agent.Agent;
import Agent.AgentState;

public class LeaveShoppingCenter extends Desire{

    public LeaveShoppingCenter(Agent agent){
        super(agent);
        setFinalPosition(agent.getBeliefs().getGraphMap().getExitPosition());
    }

    @Override
    public void scenario() {
        setPlan(new Plan(this, getParentAgent().getPreviousRegionPosition()));
        getPlan().createShortestTopPath(getFinalPosition());
    }

    @Override
    public void realTimePlanning() {
        getPlan().createPath();
    }

    @Override
    public void action() {
        finalAction();
    }

    @Override
    public void finalAction() {
        getParentAgent().setAgentState(AgentState.LEAVING);
        this.terminate();
        getParentAgent().leaveShoppingCenter();
    }
}
