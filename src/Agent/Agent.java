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
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.GraphIterator;

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
    GraphPath<GraphVertex, DefaultEdge> plannedPath;
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

        makePathForRandomWalk(position);
//        makePathForWholeAreaSearch(position);

        this.nextPosition = notPlannedPath.next();

        this.walkProcess = new WalkProcess(this);
        walkProcess.start();
    }

    public void collisionCheck(){}

    public void moveToNextPosition(){
        setPosition(getNextPosition());
    }

    public void reservePosition(GraphVertex nextPosition){
    }

    private void makePathForWholeAreaSearch(GraphVertex startPosition) {
        notPlannedPath = graphMap.depthFirstSearch(startPosition);
    }

    private void makePathForRandomWalk(GraphVertex startPosition) {
        notPlannedPath = graphMap.randomWalk(startPosition);
    }


}
