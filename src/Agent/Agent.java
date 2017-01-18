package Agent;


import AgentDesires.Desire;
import AgentEvents.WalkProcess;
import Environment.*;

import dissim.simspace.core.BasicSimEntity;
import dissim.simspace.core.SimModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false)
public class Agent extends BasicSimEntity {

    private Beliefs beliefs;
    private Desire intention;

    private int id;
    private double agentSpeed;
    private Vector movingDirection;
    private GraphVertex previousPosition;
    private GraphVertex position;
    private GraphVertex nextPosition;
    private GraphVertex endPosition;
    private AgentState agentState;
    Iterator<GraphVertex> path;

    private WalkProcess walkProcess;

    @SneakyThrows
    public Agent(Graph graphMap, WorldCoordinates startPosition, int id){
        super(SimModel.getInstance().getCommonSimContext());
        this.id = id;
        this.beliefs = new Beliefs(graphMap);
        this.agentState = AgentState.NOP;
        this.agentSpeed = RandomGenerator.getInstance().exponential(1);
        this.movingDirection = new Vector(0,-1);//na pocz ma ustalony juz sciezke wiec moze to ustawic
        setPosition(graphMap.getVertex(new WorldCoordinates(0, 14)));
//        setPosition(graphMap.getVertex(new WorldCoordinates(5, 12)));

        this.previousPosition = graphMap.getVertex(new WorldCoordinates(20, 15));
        this.endPosition = graphMap.getVertex(new WorldCoordinates(17, 10));
        path = beliefs.getGraphMap().getShortestPath(beliefs.getGraphCells(), position, endPosition).getPath().getVertexList().iterator();
        this.nextPosition = path.next(); //if has..
//        this.nextPosition = graphMap.getVertex(new WorldCoordinates(21, 16));





        this.walkProcess = new WalkProcess(this);
        walkProcess.start();
        observeTheEnvironment();
    }

    public void observeTheEnvironment(){
        lookForCollision();
        lookAround();
        updateDirection();
    }

    private void lookForCollision(){
        if(nextPosition.isOccupied() || nextPosition.isReserved()){
            beliefs.setCollision(true);
        }
    }

    private void lookAround(){
        List<GraphVertex> verticesAround = new ArrayList<>();
        Set<DefaultEdge> aroundEdges = beliefs.getGraphCells().edgesOf(beliefs.getGraphMap().getVertex(position));
        for(DefaultEdge edge : aroundEdges){
            verticesAround.add(beliefs.getGraphCells().getEdgeSource(edge));
            verticesAround.add(beliefs.getGraphCells().getEdgeTarget(edge));
        }
        verticesAround = verticesAround.parallelStream()
                .distinct()
                .collect(Collectors.toList());

        for(GraphVertex graphVertex : verticesAround){
            System.out.println(graphVertex);
        }


        beliefs.setVerticesAround(verticesAround);
    }
    private void updateDirection(){
        if(agentState == AgentState.WALK)
            movingDirection.update(position, nextPosition);
    }

    public void moveForward(){
        previousPosition = position;
        GraphVertex forwardVertex = beliefs.getGraphMap().getVertex(position.getWorldCoordinates().getForwardPointCoordinates(movingDirection));
        if(forwardVertex != null)
            position = forwardVertex;
        else
            waitInPlace();
    }

    public void moveRight(){
        movingDirection.rotateRight();
        moveForward();
    }

    public void moveForwardRight(){
        movingDirection.rotateForwardRight();
        moveForward();
    }

    public void moveForwardLeft(){
        movingDirection.rotateForwardLeft();
        moveForward();
    }

    public void moveLeft(){
        movingDirection.rotateLeft();
        moveForward();
    }

    public void moveBackLeft(){
        movingDirection.rotateBackLeft();
        moveForward();
    }

    public void moveBackRight(){
        movingDirection.rotateBackRight();
        moveForward();
    }

    public void moveBack(){
        movingDirection.rotateBack();
        moveForward();
    }

    public void waitInPlace(){

    }

    public void moveToNextPosition(){
        position.free();
        setPosition(getNextPosition());
    }

    public void moveToPreviousPosition(){
        setPosition(getPreviousPosition());
    }

    public void reservePosition(GraphVertex nextPosition){
        nextPosition.reserve();
    }

    private void setPosition(GraphVertex position){
        position.occupy(this);
        this.position = position;
//        System.out.println(position);
    }

}
