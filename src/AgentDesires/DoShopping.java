package AgentDesires;


import Agent.Agent;

public class DoShopping extends Desire{


    DoShopping(Agent parentAgent, VisitShop visitShop){
        super(parentAgent);
        setFinalPosition(visitShop.getFinalPosition());
    }

    @Override
    public void scenario() {
        setPlan(new Plan(this, getParentAgent().getPosition()));
        getPlan().createWanderLocalPath();
    }

    @Override
    public void realTimePlanning() {
        getPlan().createWanderLocalPath();
    }

    @Override
    public void finalAction() {
        System.out.println("DoShopping final action.");
    }
}
