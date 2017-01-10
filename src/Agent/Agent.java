package Agent;


import Environment.GraphNode;

import Environment.Map;
import dissim.simspace.core.BasicSimEntity;
import dissim.simspace.core.SimModel;
import lombok.Data;

@Data
public class Agent extends BasicSimEntity{

    private int id;
    private GraphNode positionOnMap;
    private AgentState agentState;
    private int KnowledgeOfArea;
    private Map parentMap;

    public Agent(Map parentMap, int id, GraphNode positionOnMap){
        super(SimModel.getInstance().getCommonSimContext());
        this.parentMap = parentMap;
        this.id = id;
        this.positionOnMap = positionOnMap;
        this.agentState = AgentState.NOP;
    }

    public void collisionCheck(){

    }

    public void moveAgent(Agent agent, GraphNode from, GraphNode to){
        parentMap.moveAgent(this, from, to);
    }

}
