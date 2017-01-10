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

    public Agent(){
        super(SimModel.getInstance().getCommonSimContext());
    }
}
