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
        System.out.println("DESIREMOD: desires: ");
        for(Desire desire : desires){
            System.out.println(desire);
        }

        parentAgent.getDecisionModule().deliberate();
    }

    private void addVisitShopDesires(){
        List<Integer> shops =  parentAgent.getBeliefs().getShopsToVisit();
        for(Integer shop : shops){
            desires.add(new VisitShop(parentAgent, shop));
        }
        parentAgent.getBeliefs().getShopsToVisit().clear();
    }

    public boolean desiresExist(){
        return desires.size() > 0;
    }
}
