package AgentDesires;

import Agent.Agent;
import Agent.Beliefs;
import Environment.GraphVertex;
import Environment.RandomGenerator;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = {"parentAgent", "plan", "agentBeliefs"})
public abstract class Desire {

    private final int maxPriority = 5;
    private final int highPriority = 0;
    private Agent parentAgent;
    private Beliefs agentBeliefs;
    private int priority = RandomGenerator.getInstance().uniformInt(1, maxPriority);
    private Plan plan;
    private GraphVertex finalPosition;
    private boolean aborted;

    Desire(Agent agent){
        this.parentAgent = agent;
        this.agentBeliefs = agent.getBeliefs();
    }

    Desire(Agent agent, boolean highPriorityIndicator){
        this.parentAgent = agent;
        this.agentBeliefs = agent.getBeliefs();
        if(highPriorityIndicator)
            this.priority = this.highPriority;
    }

    public abstract void scenario();

    public abstract void realTimePlanning();

    public abstract void finalAction();

    void terminate(){
        parentAgent.getDecisionModule().setIntention(null);
        parentAgent.getDesireModule().getDesires().remove(this);
        this.aborted = true;//TODO??
    }

    public void interrupt(){
        parentAgent.getDecisionModule().setIntention(null);
    }
}
