package Agent;


import AgentEvents.WalkProcess;
import Environment.*;

import dissim.simspace.core.BasicSimEntity;
import dissim.simspace.core.SimModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;

import java.awt.*;
import java.util.Iterator;

@Data
@EqualsAndHashCode(callSuper = false)
public class Agent extends BasicSimEntity {

    private Beliefs beliefs;

    private int id;
    private double agentSpeed;
    private Vector movingDirection;
    private GraphVertex previousPosition;
    private GraphVertex position;
    private GraphVertex nextPosition;
    private GraphVertex endPosition;
    private AgentState agentState;
    private Graph graphMap;
    Iterator<GraphVertex> path;

    private WalkProcess walkProcess;

    @SneakyThrows
    public Agent(Graph graphMap, WorldCoordinates startPosition, int id){
        super(SimModel.getInstance().getCommonSimContext());
        this.id = id;
        this.graphMap = graphMap;
        this.beliefs = new Beliefs(graphMap);
        this.agentState = AgentState.NOP;
        this.agentSpeed = RandomGenerator.getInstance().exponential(1);
        this.movingDirection = new Vector(0,-1);
        this.position = graphMap.getVertex(new WorldCoordinates(20, 15));
        this.previousPosition = graphMap.getVertex(new WorldCoordinates(20, 15));
//        this.position = graphMap.getVertex(startPosition);

        this.endPosition = graphMap.getVertex(new WorldCoordinates(37, 24));

        this.walkProcess = new WalkProcess(this);
        walkProcess.start();
    }


    public void observeTheEnvironment(){
        lookForCollision();
        lookAround();
        updateDirection();
    }


    private void lookForCollision(){}
    private void lookAround(){}
    private void updateDirection(){}

    public void moveForward(){
        previousPosition = position;
        GraphVertex forwardVertex = graphMap.getVertex(position.getWorldCoordinates().getForwardPointCoordinates(movingDirection));
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
        setPosition(getNextPosition());
    }

    public void moveToPreviousPosition(){
        setPosition(getPreviousPosition());
    }

    public void reservePosition(GraphVertex nextPosition){
    }
}
