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
import org.jgrapht.GraphPath;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.AStarShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.GraphIterator;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "graphMap")
public class Agent extends BasicSimEntity {

    private int id;
    private double agentSpeed;
    private GraphVertex positionOnMap;
    private GraphVertex nextPositionOnMap;
    private AgentState agentState;
    private int KnowledgeOfArea;

    private GraphMap graphMap;

    private WalkProcess walkProcess;

    GraphPath<GraphVertex, DefaultEdge> agentCurrentPath;
    GraphIterator<GraphVertex, DefaultEdge> searchPath;

    @SneakyThrows
    public Agent(GraphMap graphMap, int id){
        super(SimModel.getInstance().getCommonSimContext());
        this.id = id;
        this.graphMap = graphMap;
        this.agentSpeed = RandomGenerator.getInstance().exponential(0.3);
        this.positionOnMap = null;

        makePathForWholeAreaSearch();

        this.nextPositionOnMap = searchPath.next();
        this.agentState = AgentState.NOP;

        this.walkProcess = new WalkProcess(this);

        //proces myslowy


        walkProcess.start();
    }

    public void collisionCheck(){}

    public void moveToNextPosition(){
        setPositionOnMap(getNextPositionOnMap());
    }

    public void reservePosition(GraphVertex nextPosition){
    }

    private void makePathForWholeAreaSearch() {
        searchPath = graphMap.DepthFirstSearch();
    }


}
