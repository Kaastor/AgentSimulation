package AgentDesires;

import Agent.Agent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
class VisitShop extends Desire {

    private int shopNumber;

    VisitShop(Agent agent, int shopNumber){
        super(agent);
        this.shopNumber = shopNumber;
    }

    @Override
    public void scenario() {
        setPlan(new Plan(this, getParentAgent().getPreviousRegionPosition()));

        if(getAgentBeliefs().getKnowledgeOfArea() == 1){
            System.out.print("scenario: ");

            setFinalPosition(getAgentBeliefs().getGraphMap().getShopPosition(shopNumber));
            getPlan().createShortestTopPath(getFinalPosition());
        }
        else{
            setPlan(new Plan(this, getParentAgent().getPosition()));
            getPlan().createSearchTopPath();
        }
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
        this.terminate();
        getParentAgent().setPreviousRegionPosition(getFinalPosition());
        Desire doShopping = new DoShopping(getParentAgent(), this);
        getParentAgent().getDesireModule().addDesire(doShopping);
    }
}
