package AgentDesires;


import Agent.Agent;
import Environment.GraphVertex;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DoShopping extends Desire{

    private GraphVertex shopCenterPosition;
    private boolean inShopCenter;

    DoShopping(Agent parentAgent, VisitShop visitShop){
        super(parentAgent, true);
        this.inShopCenter = false;
        setFinalPosition(visitShop.getFinalPosition());
        shopCenterPosition = getAgentBeliefs().getGraphMap().getShopCenterPosition(visitShop.getShopNumber());
    }

    @Override
    public void scenario() {
        setPlan(new Plan(this, getParentAgent().getPreviousRegionPosition()));
        enterShop();
    }

    @Override
    public void realTimePlanning() {
        if(!getParentAgent().getBeliefs().isCollision()){
            getParentAgent().getDecisionModule().executePlan();
        }
        else{
            getParentAgent().avoidCollision();
        }
    }

    @Override
    public void action() {
        if(!inShopCenter) {
            this.inShopCenter = true;
            getPlan().createWanderLocalPath(getParentAgent().getPosition());
        }
        else{
            finalAction();
        }
    }

    private void finalAction() {
        System.out.println(getParentAgent().getId() + " : Finished shopping.");
        this.terminate();
    }

    private void enterShop(){
        getPlan().createPathToPoint(shopCenterPosition);
    }
}
