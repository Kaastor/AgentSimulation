package AgentDesires;

import Agent.Agent;
import lombok.Data;

@Data
public abstract class Desire {

    private Agent parentAgent;
    private int priority;
    private Plan plan;
    private boolean aborted;

    public Desire(Agent agent){
        this.parentAgent = agent;
    }

    public abstract void scenario();

    public abstract void finalAction();

    public void terminate(){
        this.aborted = true;
    }
}
