package AgentDesires;

import Agent.Agent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class VisitShop extends Desire {

    private int shopNumber;

    public VisitShop(Agent agent, int shopNumber){
        super(agent);
        this.shopNumber = shopNumber;
    }

    @Override
    public void scenario() {
        setPlan(new Plan(this, getParentAgent().getPreviousRegionPosition()));

        if(getAgentBeliefs().getKnowledgeOfArea() == 1){
            setFinalPosition(getAgentBeliefs().getGraphMap().getShopPosition(shopNumber));
            getPlan().createShortestTopPath(getFinalPosition());
            getPlan().createPath();
        }
        else{
            setPlan(new Plan(this, getParentAgent().getPosition()));
            getPlan().createSearchTopPath();
            getPlan().createPath();
        }
    }

    @Override
    public void realTimePlanning() {
        if(!getParentAgent().getBeliefs().isCollision()){
            getParentAgent().getDecisionModule().executePlan();
        }
        else {
            getParentAgent().avoidCollision();
        }
    }

    @Override
    public void action() {
        finalAction();
    }

    private void finalAction() {
        this.terminate();
        getParentAgent().setPreviousRegionPosition(getFinalPosition());
        Desire doShopping = new DoShopping(getParentAgent(), this);
        getParentAgent().getDesireModule().addDesire(doShopping);
    }


}
