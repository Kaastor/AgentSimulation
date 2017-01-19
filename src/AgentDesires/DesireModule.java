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

    public void cognitiveProcessor() {
        addVisitShopDesires();
        System.out.println("DesireModule: desires: ");
        for (Desire desire : desires) {
            System.out.println(desire);
        }
        if (!desiresExist()) {
            System.out.println("Leave CH");
            //TODO lista pusta -> new desire - opusc sklep (w opusc sklep jesli koa = 0 to wychodzi tymi co wszedl,
            //dla koa= 1 szuka najblizszego?
        }

        parentAgent.getDecisionModule().deliberate();
    }

    private void addVisitShopDesires(){
        if(parentAgent.getBeliefs().shopsExist()) {
            List<Integer> shops = parentAgent.getBeliefs().getShopsToVisit();
            for (Integer shop : shops) {
                desires.add(new VisitShop(parentAgent, shop));
            }
            parentAgent.getBeliefs().getShopsToVisit().clear();
        }
    }

    public boolean desiresExist(){
        return desires.size() > 0;
    }
}
