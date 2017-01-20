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
        enterShop();
    }

    @Override
    public void realTimePlanning() {
        if(inShopCenter)
            getPlan().createWanderLocalPath();
        else
            enterShop();
    }

    @Override
    public void action() {
        if(!inShopCenter) {
            this.inShopCenter = true;
            getPlan().createWanderLocalPath();
        }
        else{
            finalAction();
        }
    }

    @Override
    public void finalAction() {
        System.out.println(getParentAgent().getId() + " :Zakończył zakupy w sklepie.");
        this.terminate();
    }

    private void enterShop(){
        getPlan().createPathToPoint(shopCenterPosition);
    }
}
