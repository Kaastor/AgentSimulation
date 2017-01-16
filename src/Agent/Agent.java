package Agent;


import AgentEvents.WalkProcess;
import Environment.*;

import dissim.simspace.core.BasicSimEntity;
import dissim.simspace.core.SimModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import java.util.Iterator;

@Data
@EqualsAndHashCode(callSuper = false)
public class Agent extends BasicSimEntity {

    private int id;
    private double agentSpeed;
    private GraphVertex previousPosition;
    private GraphVertex position;
    private GraphVertex nextPosition;

    private GraphVertex endPosition;

    private AgentState agentState;
    private int KnowledgeOfArea;
    private Graph graphMap;
    Iterator<GraphVertex> path;

    private WalkProcess walkProcess;

    @SneakyThrows
    public Agent(Graph graphMap, WorldCoordinates startPosition, int id){
        super(SimModel.getInstance().getCommonSimContext());
        this.id = id;
        this.graphMap = graphMap;
        this.agentState = AgentState.NOP;
        this.agentSpeed = RandomGenerator.getInstance().exponential(0.3);
        this.previousPosition = graphMap.getVertex(startPosition);

        this.position = graphMap.getVertex(startPosition);
        this.endPosition = graphMap.getVertex(new WorldCoordinates(6, 30));

//        createPathForRandomWalk(position);
        createPathForWholeAreaSearch(position);

//        createShortestPath(position, endPosition);

//        System.out.println(graphMap.getVertex(new WorldCoordinates(6, 25)));
//          createShortestPath(position, new GraphVertex(new WorldCoordinates(15, 10)));
        this.nextPosition = path.next();
//        this.nextPosition = plannedPath.next();

        this.walkProcess = new WalkProcess(this);
        walkProcess.start();
    }

    public void moveStraight(){}
    public void moveRight(){}
    public void moveLeft(){}
    public void moveBack(){}

    public void lookForCollision(){}

    public void moveToNextPosition(){
        setPosition(getNextPosition());
    }

    public void moveToPreviousPosition(){
        setPosition(getPreviousPosition());
    }

    public void reservePosition(GraphVertex nextPosition){
    }

    private void createPathForWholeAreaSearch(GraphVertex startPosition) {
        path = graphMap.getWholeMapSearchPath(startPosition);
    }

    private void createPathForRandomWalk(GraphVertex startPosition) {
        path = graphMap.getRandomWalkPath(startPosition);
    }

    private void createShortestPath(GraphVertex startPosition, GraphVertex endPosition) {
        path = graphMap.getShortestPath(startPosition, endPosition).getPath().getVertexList().iterator();
    }



}
