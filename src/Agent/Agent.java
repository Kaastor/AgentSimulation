package Agent;


import AgentEvents.WalkProcess;
import Environment.GraphMap;
import Environment.GraphVertex;
import Environment.WorldCoordinates;

import Environment.RandomGenerator;
import dissim.simspace.core.BasicSimEntity;
import dissim.simspace.core.SimModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.GraphIterator;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "graphMap")
public class Agent extends BasicSimEntity {

    private int id;
    private double agentSpeed;
    private WorldCoordinates positionOnMap;
    private GraphVertex nextPositionOnMap;
    private AgentState agentState;
    private int KnowledgeOfArea;

    private GraphMap graphMap;

    private WalkProcess walkProcess;

    GraphIterator<GraphVertex, DefaultEdge> searchPath;

    @SneakyThrows
    public Agent(GraphMap graphMap, int id){
        super(SimModel.getInstance().getCommonSimContext());
        this.id = id;
        this.graphMap = graphMap;
        this.agentSpeed = RandomGenerator.getInstance().exponential(1.0);
        this.positionOnMap = new WorldCoordinates(0,0);
        this.nextPositionOnMap = null;
        this.agentState = AgentState.NOP;

        this.walkProcess = new WalkProcess(this);
        walkProcess.start();
    }

    public void collisionCheck(){}

    public void moveAgent(GraphVertex to){
        setPositionOnMap(to.getWorldCoordinates());
    }

    public void reservePosition(GraphVertex nextPosition){
        setNextPositionOnMap(nextPosition);
    }

    public void getPathForSearchArea() {
        searchPath = graphMap.DepthFirstSearch();
    }

}
