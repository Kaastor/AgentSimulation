package AgentDesires;

import Agent.Agent;
import Environment.RandomGenerator;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = {"parentAgent", "plan"})
public abstract class Desire {

    private Agent parentAgent;
    private final int priority = RandomGenerator.getInstance().uniformInt(0, 5);
    private Plan plan;
    private boolean aborted;

    public Desire(Agent agent){
        this.parentAgent = agent;
    }

    public abstract void scenario();

    public abstract void finalAction();

    public void terminate(){
        parentAgent.getDecisionModule().setIntention(null);
        parentAgent.getDesireModule().getDesires().remove(this);
        this.aborted = true;//TODO??
    }

    public void interrupt(){
        parentAgent.getDecisionModule().setIntention(null);
    }
}
