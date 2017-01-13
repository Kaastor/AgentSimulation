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
    private WorldCoordinates currentPositionOnMap;
    private GraphVertex nextPositionOnMap;


    public WalkProcess(Agent parentAgent) throws SimControlException{
        super(parentAgent);
        this.parentAgent = getSimEntity();
        this.currentPositionOnMap = parentAgent.getPositionOnMap();
        parentAgent.getPathForSearchArea();
        this.nextPositionOnMap = parentAgent.getSearchPath().next();


//        while(parentAgent.getSearchPath().hasNext())
//            System.out.println(parentAgent.getSearchPath().next());

    }

    @Override
    public double controlStateTransitions() {
        parentAgent.setAgentState(AgentState.WALK);

        walkToNewPositionIfIsSet();
        System.out.println(parentAgent.getId() + " " + currentPositionOnMap);//pozsie nie zmienia, raz pojawilsie dziwnego exceptiona
        reserveNewPositionOnMap();

        return parentAgent.getAgentSpeed();
    }

    private void walkToNewPositionIfIsSet(){
        if(nextPositionOnMap != null ){
            parentAgent.moveAgent(nextPositionOnMap);
        }
    }

    private void reserveNewPositionOnMap(){
        if(parentAgent.getSearchPath().hasNext())
            parentAgent.reservePosition(parentAgent.getSearchPath().next());
    }
}
