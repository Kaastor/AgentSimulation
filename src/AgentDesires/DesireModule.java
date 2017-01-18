package AgentDesires;

import Agent.Agent;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Data
public class DesireModule {

    private Agent parentAgent;
    private List<Desire> desires;

    public DesireModule(Agent parentAgent){
        this.parentAgent = parentAgent;
        this.desires = new ArrayList<>();
    }

    public void cognitiveProcessor(){
        addVisitShopDesires();
    }

    private void addVisitShopDesires(){
        List<Integer> shops =  parentAgent.getBeliefs().getShopsToVisit();
        for(Integer shop : shops){
            desires.add(new VisitShop(parentAgent, shop));
            parentAgent.getBeliefs().getShopsToVisit().remove(shop);
        }
    }
}
