package Agent;


import AgentDesires.DesireModule;
import AgentEvents.PhoneEvent;
import AgentEvents.WalkEvent;
import Environment.*;

import dissim.simspace.core.BasicSimEntity;
import dissim.simspace.core.SimModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false, exclude = {"beliefs", "desireModule", "decisionModule"})
public class Agent extends BasicSimEntity {

    private Beliefs beliefs;
    private DesireModule desireModule;
    private DecisionModule decisionModule;

    private int id;
    private double agentSpeed;
    private boolean leaving;
    private Vector movingDirection;
    private GraphVertex previousPosition;
    private GraphVertex previousRegionPosition;
    private GraphVertex position;
    private GraphVertex nextPosition;
    private AgentState agentState;

    private WalkEvent walkEvent;
    private PhoneEvent phoneEvent;

    @SneakyThrows
    public Agent(Graph graphMap, WorldCoordinates startPosition, int id){
        super(SimModel.getInstance().getCommonSimContext());
        this.id = id;
        this.agentState = AgentState.NOP;
//        this.agentSpeed = RandomGenerator.getInstance().exponential(1);
        this.agentSpeed = 0.1;
        this.leaving = false;
        this.movingDirection = new Vector(0,-1);
        this.position = graphMap.getVertex(startPosition);
        this.previousPosition = graphMap.getVertex(startPosition);
        this.previousRegionPosition = graphMap.getVertex(startPosition);
        this.desireModule = new DesireModule(this);
        this.decisionModule = new DecisionModule(this);
        this.beliefs = new Beliefs(this, graphMap);

        this.phoneEvent = new PhoneEvent(this, RandomGenerator.getInstance().exponential(0));
        beliefs.perceptualProcessor();
    }

    void observeEnvironment(){
        lookAround();
        //updateDirection();
    }

    private void lookAround(){
        closeLookAround();
        furtherLookAround();
    }

    private void closeLookAround(){
        List<GraphVertex> closeVerticesAround = new ArrayList<>();
        Set<DefaultEdge> aroundEdges =  beliefs.getGraphCells().edgesOf( beliefs.getGraphMap().getVertex(getPosition()));
        for(DefaultEdge edge : aroundEdges){
            closeVerticesAround.add( beliefs.getGraphCells().getEdgeSource(edge));
            closeVerticesAround.add( beliefs.getGraphCells().getEdgeTarget(edge));
        }
        closeVerticesAround = closeVerticesAround.parallelStream()
                .distinct()
                .collect(Collectors.toList());
        beliefs.setCloseVerticesAround(closeVerticesAround);
    }

    private void furtherLookAround(){
        if(agentState == AgentState.WANDER){
            List<GraphVertex> furtherVerticesAround = new ArrayList<>();
            for(GraphVertex closeVertex : beliefs.getCloseVerticesAround()) {
                Set<DefaultEdge> aroundEdges = beliefs.getGraphCells().edgesOf(closeVertex);
                for (DefaultEdge edge : aroundEdges) {
                    furtherVerticesAround.add(beliefs.getGraphCells().getEdgeSource(edge));
                    furtherVerticesAround.add(beliefs.getGraphCells().getEdgeTarget(edge));
                }
                furtherVerticesAround = furtherVerticesAround.parallelStream()
                        .distinct()
                        .collect(Collectors.toList());
            }
            beliefs.setFurtherVerticesAround(furtherVerticesAround);
        }
    }

    private void updateDirection(){
        if(getAgentState() == AgentState.WALK)
            getMovingDirection().update(getPosition(), getNextPosition());
    }

    boolean lookForCollision() {
        return (getNextPosition().isOccupied() || getNextPosition().isReserved());
    }

    public void moveForward(){
        previousPosition = position;
        GraphVertex forwardVertex = beliefs.getGraphMap().getVertex(position.getWorldCoordinates().getForwardPointCoordinates(movingDirection));
        if(forwardVertex != null && forwardVertex.isFree())
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
    }

    public void leaveShoppingCenter() {
        System.out.println(id + " agent left SC.");
        setAgentState(AgentState.LEFT);
    }

}
