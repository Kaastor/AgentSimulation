package AgentDesires;

import Agent.Agent;
import Agent.AgentState;
import lombok.Data;
import lombok.SneakyThrows;
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
        getPlan().createPath();
//        System.out.println("Po scenario");
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

    @Override
    public void finalAction() {
        System.out.println("SetLeaving");
        getParentAgent().setLeaving(true);
        this.terminate();
    }

//    @SneakyThrows
//    private void setNextPositionOnMap() {
//        getParentAgent().setNextPosition(getParentAgent().getDecisionModule().getIntention().getPlan().getNextPosition());
//        if (noNextPosition() && getParentAgent().getAgentState() != AgentState.LEAVING) {
//            getParentAgent().setAgentState(AgentState.STANDING);
//        }
//    }
//
//    private boolean noNextPosition(){
//        return getParentAgent().getNextPosition() == null;
//    }
}
