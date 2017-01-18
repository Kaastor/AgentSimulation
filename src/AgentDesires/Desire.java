package AgentDesires;

import Agent.Agent;
import Environment.RandomGenerator;
import lombok.Data;

@Data
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
        this.aborted = true;//TODO??
    }
}
