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
import lombok.ToString;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false, exclude = {"beliefs", "desireModule", "decisionModule"})
@ToString(exclude = {"beliefs", "desireModule", "decisionModule", "walkEvent", "phoneEvent"})
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
        this.agentSpeed = 0.2;
        this.leaving = false;
        this.movingDirection = new Vector(0,-1);
        this.position = graphMap.getVertex(startPosition);
        this.previousPosition = graphMap.getVertex(startPosition);
        this.previousRegionPosition = graphMap.getVertex(startPosition);
        this.desireModule = new DesireModule(this);
        this.decisionModule = new DecisionModule(this);
        this.beliefs = new Beliefs(this, graphMap);

        //this.phoneEvent = new PhoneEvent(this, RandomGenerator.getInstance().uniformInt(1, 10));
        beliefs.perceptualProcessor();
    }

    void observeEnvironment(){
        lookAround();
        updateDirection();
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
        if(agentState == AgentState.WALK)
            movingDirection.update(position, nextPosition);
    }

    public void avoidCollision(){
        List<GraphVertex> verticesAround = getBeliefs().getCloseVerticesAround();
        GraphVertex nextFreeVertex = null;
        for(GraphVertex vertex : verticesAround){
            if(!vertex.isOccupiedOrReserved()){
                nextFreeVertex = vertex;
                break;
            }
        }
        if(nextFreeVertex != null){

            if(decisionModule.getIntention().getPlan().isRandomPath()){
                nextFreeVertex = position;
                decisionModule.getIntention().getPlan().createWanderLocalPath(nextFreeVertex);
            }
            else {
                System.out.println(  id+"1?");
                reserveNextPosition(nextFreeVertex);
                System.out.println( id+"2? " + agentState + " " + decisionModule.getIntention().getPlan().isRandomPath());
                decisionModule.getIntention().getPlan().updatePath(nextFreeVertex);
                System.out.println(  id+"3?");
            }
        }
        else{
            nextFreeVertex = getPosition(); //stoi w miejscu
            setNextPosition(nextFreeVertex);
            decisionModule.getIntention().getPlan().updatePath(nextFreeVertex);
        }
        System.out.println( "przedexec");
        getDecisionModule().executePlan();
    }

    private boolean lookForCollision(GraphVertex nextPosition) {
        boolean collision = false;
        if(nextPosition != null){
            if(nextPosition.isOccupiedOrReserved()) {
                collision = true;
            }
        }
        beliefs.setCollision(collision);
        return collision;
    }

    public void moveToNextPosition(){
        position.free();
        setPosition(getNextPosition());
    }

    private void waitInPlace(){
        nextPosition = position;
    }

    public void reserveNextPosition(GraphVertex nextPosition){
        System.out.println( getId() + "Collision position = " + nextPosition);
        if(nextPosition != null){
            if(!lookForCollision(nextPosition)){
                nextPosition.reserve();
                setNextPosition(nextPosition);
            }
        }
    }

    private void setPosition(GraphVertex position){
        position.occupy(this);
        this.position = position;
    }



    @SneakyThrows
    void leaveShoppingCenter() {
        //phoneEvent.terminate();
        position.free();
        System.out.println(id + " agent left SC.");
        setAgentState(AgentState.LEFT);
    }

}
