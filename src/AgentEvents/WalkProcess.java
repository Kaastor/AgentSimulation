package AgentEvents;

import Agent.Agent;
import Agent.AgentState;
import Environment.GraphVertex;
import Environment.WorldCoordinates;
import dissim.simspace.core.SimControlException;
import dissim.simspace.process.BasicSimProcess;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "parentAgent", callSuper = false)
public class WalkProcess extends BasicSimProcess<Agent, Object>{

    private Agent parentAgent;

    public WalkProcess(Agent parentAgent) throws SimControlException{
        super(parentAgent);
        this.parentAgent = getSimEntity();

//        while(parentAgent.getSearchPath().hasNext())
//            System.out.println(parentAgent.getSearchPath().next());

    }

    @Override
    public double controlStateTransitions() {
        parentAgent.setAgentState(AgentState.WALK);

//        System.out.println("before move " + parentAgent.getPositionOnMap());
//        System.out.println("before move " + parentAgent.getNextPositionOnMap());
        walkToNextPositionIfSet();
//        System.out.println(parentAgent.getId() + " " + parentAgent.getPositionOnMap());//pozsie nie zmienia, raz pojawilsie dziwnego exceptiona
        setNextPositionOnMap();

        return parentAgent.getAgentSpeed();
    }

    private void walkToNextPositionIfSet(){
        if(parentAgent.getNextPositionOnMap() != null &&
                !(parentAgent.getNextPositionOnMap().equals(parentAgent.getPositionOnMap()))){ //equals cos niszczy...
//            System.out.println("move");
            parentAgent.moveToNextPosition();
        }
    }

    private void setNextPositionOnMap(){
        if(parentAgent.getSearchPath().hasNext()) {
            GraphVertex next = parentAgent.getSearchPath().next();
//            System.out.println("setNext " + next);
            parentAgent.setNextPositionOnMap(next);
//            System.out.println("Next " + parentAgent.getNextPositionOnMap());
        }
    }
}
