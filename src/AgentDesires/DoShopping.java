package AgentDesires;


import Agent.Agent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DoShopping extends Desire{


    DoShopping(Agent parentAgent, VisitShop visitShop){
        super(parentAgent, true);
        setFinalPosition(visitShop.getFinalPosition());
    }

    @Override
    public void scenario() {
        enterShop();
        setPlan(new Plan(this, getParentAgent().getPosition()));
    }

    @Override
    public void realTimePlanning() {
        getPlan().createWanderLocalPath();
    }

    @Override
    public void finalAction() {
        System.out.println("DoShopping final action.");
        this.terminate();
    }

    private void enterShop(){
        for( int numberOfSteps = 0; numberOfSteps < 2 ; numberOfSteps++)
            getParentAgent().moveForward();
    }
}
