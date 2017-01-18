package Agent;

import AgentDesires.Desire;
import AgentEvents.WalkEvent;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.ToString;

@Data
@ToString(exclude = "parentAgent")
public class DecisionModule {

    private Agent parentAgent;
    private Desire intention;

    public DecisionModule(Agent parentAgent){
        this.parentAgent = parentAgent;
        this.intention = null;
    }

    public void plan(){
        intention.scenario();
    }

    public void realTimePlanning(){
        intention.getPlan().createPath();
        executePlan();
    }

    @SneakyThrows
    public void executePlan(){
        parentAgent.setWalkEvent(new WalkEvent(parentAgent));
    }
}
