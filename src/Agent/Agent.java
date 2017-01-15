package Agent;


import AgentEvents.WalkProcess;
import Environment.GraphMap;
import Environment.GraphVertex;

import Environment.RandomGenerator;
import Environment.WorldCoordinates;
import dissim.simspace.core.BasicSimEntity;
import dissim.simspace.core.SimModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.GraphIterator;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "graphMap")
public class Agent extends BasicSimEntity {

    private int id;
    private double agentSpeed;
    private GraphVertex previousPosition;
    private GraphVertex position;
    private GraphVertex nextPosition;
    private AgentState agentState;
    private int KnowledgeOfArea;
    private GraphMap graphMap;
    List<GraphVertex> plannedPath;
    GraphIterator<GraphVertex, DefaultEdge> notPlannedPath;

    private WalkProcess walkProcess;

    @SneakyThrows
    public Agent(GraphMap graphMap, WorldCoordinates startPosition, int id){
        super(SimModel.getInstance().getCommonSimContext());
        this.id = id;
        this.graphMap = graphMap;
        this.agentState = AgentState.NOP;
        this.agentSpeed = RandomGenerator.getInstance().exponential(1);
        this.previousPosition = graphMap.getVertex(startPosition);
        this.position = graphMap.getVertex(startPosition);

        createPathForRandomWalk(position);
//        createPathForWholeAreaSearch(position);

        this.nextPosition = notPlannedPath.next();

        this.walkProcess = new WalkProcess(this);
        walkProcess.start();
    }

    public void collisionCheck(){}

    public void moveToNextPosition(){
        setPosition(getNextPosition());
    }

    public void moveToPreviousPosition(){
        setPosition(getPreviousPosition());
    }

    public void reservePosition(GraphVertex nextPosition){
    }

    private void createPathForWholeAreaSearch(GraphVertex startPosition) {
        notPlannedPath = graphMap.getWholeMapSearchPath(startPosition);
    }

    private void createPathForRandomWalk(GraphVertex startPosition) {
        notPlannedPath = graphMap.getRandomWalkPath(startPosition);
    }

    private void createShortestPath(GraphVertex startPosition, GraphVertex endPosition) {
        plannedPath = graphMap.getShortestPath(startPosition, endPosition).getPath().getVertexList();
    }

}
