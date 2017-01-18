package AgentDesires;

import Agent.Agent;
import Agent.Beliefs;
import Environment.GraphVertex;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "agentBeliefs")
public class VisitShop extends Desire {

    private Beliefs agentBeliefs;
    private int shopNumber;
    private GraphVertex shopPosition;

    public VisitShop(Agent agent, int shopNumber){
        super(agent);
        this.agentBeliefs = agent.getBeliefs();
        this.shopNumber = shopNumber;
        scenario();
    }

    @Override
    public void scenario() {
        if(agentBeliefs.getKnowledgeOfArea() == 1){
            setPlan(new Plan(this, getParentAgent().getPosition()));
            this.shopPosition = agentBeliefs.getGraphMap().getShopPosition(shopNumber);
            getPlan().createShortestTopPath(shopPosition);
        }
        else{
            setPlan(new Plan(this, getParentAgent().getPosition()));
            getPlan().createSearchTopPath();
        }
    }

    @Override
    public void finalAction() {
        //TODO new process - blakanie po sklepi x - dopoki random  path nie natrafi na drzwi
        this.terminate();
    }
}
