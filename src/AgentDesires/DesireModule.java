package AgentDesires;

import Agent.Agent;
import Environment.RandomGenerator;
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
            System.out.println(parentAgent.getId() + " Added desire: " + desire);
            desires.add(desire);
            return true;
        }
        else return false;
    }

    public void removeDesire(){
        int desireNumber = RandomGenerator.getInstance().uniformInt(0, desires.size());
        if(desires.get(desireNumber).equals(parentAgent.getDecisionModule().getIntention())){
            System.out.println(parentAgent.getId() + " RemoveIntention " + parentAgent.getDecisionModule().getIntention());
            parentAgent.getDecisionModule().terminateIntention();
        }
        else{
            System.out.println(parentAgent.getId() + " RemoveShopDesire : " + desires.get(desireNumber));
            desires.remove(desireNumber);
        }
    }

    boolean noOtherDesires(){
        return desires.size() == 1;
    }
}
