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
        updateDesiresList();

        if (desires.isEmpty()) {
            System.out.println("Leave CH");
            //TODO lista pusta -> new desire - opusc sklep (w opusc sklep jesli koa = 0 to wychodzi tymi co wszedl,
            //TODO dla koa= 1 szuka najblizszego?
        }

        parentAgent.getDecisionModule().deliberate();
    }

    private void updateDesiresList(){
        updateVisitShopDesires();
    }

    private void updateVisitShopDesires(){
        if(!parentAgent.getBeliefs().getShopsToVisit().isEmpty()) {
            List<Integer> shops = parentAgent.getBeliefs().getShopsToVisit();
            for (Integer shop : shops) {
                desires.add(new VisitShop(parentAgent, shop));
            }
            parentAgent.getBeliefs().getShopsToVisit().clear();
        }
    }

    public void addDesire(Desire desire){
        desires.add(desire);
    }
}
