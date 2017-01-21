package AgentDesires;

import Agent.Agent;
import Environment.Graph;
import Environment.Map;
import Environment.RandomGenerator;
import lombok.Data;
import sun.security.krb5.internal.crypto.Des;


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
            Desire leaveShoppingCenter = new LeaveShoppingCenter(parentAgent);
            addDesire(leaveShoppingCenter);
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

    public boolean addDesire(Desire desire){
        if(!desires.contains(desire)) {
            System.out.println("Added desire: " + desire);
            desires.add(desire);
            return true;
        }
        else return false;
    }

    public void removeDesire(){
        int desireNumber = RandomGenerator.getInstance().uniformInt(0, desires.size());
        System.out.println("RemoveShopDesire");
        if(desires.get(desireNumber).equals(parentAgent.getDecisionModule().getIntention())){
            System.out.println("RemoveIntention " + parentAgent.getDecisionModule().getIntention()); //nie wraca do deliberacji... w ogole wszystko stoi, wALK byl last
            parentAgent.getDecisionModule().getIntention().terminate();
        }
        else{
            System.out.println("RemoveShopDesire : " + desires.get(desireNumber));
            desires.remove(desireNumber);
        }
    }
}
