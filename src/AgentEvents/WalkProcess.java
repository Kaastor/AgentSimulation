package AgentEvents;

import Agent.Agent;
import Agent.AgentState;
import Environment.GraphNode;
import dissim.simspace.core.SimControlException;
import dissim.simspace.process.BasicSimProcess;
import lombok.Data;

@Data
public class WalkProcess extends BasicSimProcess<Agent, Object>{

    private Agent parentAgent;
    private GraphNode currentPositionOnMap;
    private GraphNode nextPositionOnMap;


    public WalkProcess(Agent parentAgent) throws SimControlException{
        super(parentAgent);
        this.parentAgent = getSimEntity();
        this.currentPositionOnMap = parentAgent.getPositionOnMap();
        this.nextPositionOnMap = parentAgent.getPositionOnMap();
    }

    @Override
    public double controlStateTransitions() {
        parentAgent.setAgentState(AgentState.WALK);
        walkToNewPositionIfIsSet();
        reserveNewPositionOnMap();
        return 0;
    }

    private void walkToNewPositionIfIsSet(){
        if(currentPositionOnMap.getX() != nextPositionOnMap.getX()
                || currentPositionOnMap.getY() != nextPositionOnMap.getY()){
            parentAgent.moveAgent(parentAgent, currentPositionOnMap, nextPositionOnMap);
        }
    }

    private void reserveNewPositionOnMap(){
        //parentAgent.collisionCheck();
        //if free
        //then reserve
        //else wait
    }
}
