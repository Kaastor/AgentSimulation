package Agent;

import AgentEvents.WalkEvent;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public class DecisionModule {

    private Agent parentAgent;

    public DecisionModule(Agent parentAgent){
        this.parentAgent = parentAgent;
    }

    public void plan(){
        parentAgent.getIntention().scenario();
    }

    public void realTimePlanning(){
        parentAgent.getIntention().getPlan().createPath();
        executePlan();
    }

    @SneakyThrows
    public void executePlan(){
        parentAgent.setWalkEvent(new WalkEvent(parentAgent));
    }
}
