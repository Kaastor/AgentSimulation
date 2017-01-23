package AgentEvents;


import Agent.Agent;
import AgentDesires.VisitShop;
import Environment.Graph;
import Environment.RandomGenerator;
import dissim.simspace.core.BasicSimStateChange;
import dissim.simspace.core.SimControlException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "parentAgent")
public class PhoneEvent extends BasicSimStateChange<Agent, Object> {

    private Agent parentAgent;
    private int cancelDesire;
    private int addShopVisit;

    public PhoneEvent(Agent agent, double delay) throws SimControlException {
        super(agent, delay);
        this.parentAgent = getSimEntity();
        this.cancelDesire = RandomGenerator.getInstance().uniformInt(0,2);
        this.addShopVisit = RandomGenerator.getInstance().uniformInt(0,2);
//        cancelDesire = 1;
//        addShopVisit = 0;
    }

    @Override
    protected void transition() throws SimControlException {
        cancelShopVisit();
        addShopVisit();
    }

    private void cancelShopVisit(){
        System.out.println("PhoneEvent");
        if(cancelDesire == 1){
            System.out.println(parentAgent.getId() + " Phone event exec. Cancel");
            parentAgent.getDesireModule().removeDesire();
        }
    }

    private void addShopVisit(){
        if(addShopVisit == 1){
            System.out.println(parentAgent.getId() + "Phone event exec. Add");
            int shopNumber = RandomGenerator.getInstance().uniformInt(1, Graph.SHOPS_NUMBER);
            boolean added = parentAgent.getDesireModule().addDesire(new VisitShop(parentAgent, shopNumber));
            if(!added)
                addShopVisit();

        }
    }
}
