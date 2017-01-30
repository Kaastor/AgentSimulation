package AgentDesires;

import Agent.AgentState;
import Environment.Graph;
import Environment.GraphVertex;
import Environment.RandomGenerator;
import com.sun.istack.internal.Nullable;
import lombok.Data;
import org.jgrapht.alg.BidirectionalDijkstraShortestPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;

import java.util.Iterator;

@Data
public class Plan {

    private Desire parentDesire;
    private Iterator<GraphVertex> regionPath;
    private Iterator<GraphVertex> localPath;
    private Graph graphMap;
    private GraphVertex nextRegionVertex;
    private GraphVertex startRegionVertex;
    private boolean randomPath;
    private int randomPathSteps;
    private final int randomPathStepsImpatience = RandomGenerator.getInstance().uniformInt(450, 900);
    private int plannedPathSteps;
    private final int plannedPathStepsImpatience = RandomGenerator.getInstance().uniformInt(900, 1800);

    Plan(Desire parentDesire, GraphVertex startVertex){
        this.parentDesire = parentDesire;
        this.graphMap = parentDesire.getParentAgent().getBeliefs().getGraphMap();
        this.startRegionVertex = startVertex;
        this.regionPath = null;
        this.randomPath = false;
        this.randomPathSteps = 0;
        this.plannedPathSteps = 0;
    }

    void createShortestTopPath(GraphVertex endRegionVertex){
        DijkstraShortestPath<GraphVertex, DefaultEdge> graphPath = graphMap.getShortestPath(graphMap.getGraphRegions(), startRegionVertex, endRegionVertex);
        regionPath = graphPath.getPath().getVertexList().iterator();
        getInitialNextRegionPosition();
    }

    void createSearchTopPath(){
        regionPath = graphMap.getRegionSearchPath(startRegionVertex);
    }

    public void createWanderLocalPath(GraphVertex startVertex){
        parentDesire.getParentAgent().setAgentState(AgentState.WANDER);
        localPath = graphMap.getRandomWalkPath(startVertex);
        this.randomPath = true;
    }

    void createPath(){
        GraphVertex startVertex = parentDesire.getParentAgent().getPosition();
        createShortestPath(startVertex, nextRegionVertex);
    }

    void createPathToPoint(GraphVertex endVertex){
        GraphVertex startVertex = parentDesire.getParentAgent().getPosition();
        createShortestPath(startVertex, endVertex);
    }

    private void createShortestPath(GraphVertex startVertex, GraphVertex endVertex){
        BidirectionalDijkstraShortestPath<GraphVertex, DefaultEdge> graphPath = graphMap.getShortestPathBi(graphMap.getGraphCells(), startVertex, endVertex);
        localPath = graphPath.getPath().getVertexList().iterator();
        getInitialNextPosition();
    }

    public void updatePath(GraphVertex vertex){
        if(nextRegionVertex != null)
            createShortestPath(vertex, nextRegionVertex);
        else
            createPathToPoint(vertex);
    }

    public GraphVertex getNextPosition(){
        if(!randomPath) {
            plannedPathSteps++;
            if(plannedPathSteps < plannedPathStepsImpatience)
                return getNextPlannedPosition();
            else{
                parentDesire.terminate();
                return null;
            }

        }
        else{
            randomPathSteps++;
            if(randomPathSteps < randomPathStepsImpatience)
                return getNextWanderPosition();
            else{
                parentDesire.action();
                return null;
            }
        }
    }

    private GraphVertex getNextPlannedPosition(){
        if (localPath.hasNext()) {
            parentDesire.getParentAgent().setAgentState(AgentState.WALK);
            return localPath.next();
        } else {
            if (nextPlannedRegionPosition()) {
                createPath();
                return getNextPosition();
            }
            else{
                parentDesire.action();
                return null;
            }
        }
    }

    @Nullable
    private GraphVertex getNextWanderPosition(){
        if(parentDesire.getAgentBeliefs().getFurtherVerticesAround().contains(parentDesire.getFinalPosition())){
            parentDesire.action();
            return null;
        }
        else if(localPath.hasNext()){
            parentDesire.getParentAgent().setAgentState(AgentState.WANDER);
            return localPath.next();
        }
        else
            return null;
    }

    private boolean nextPlannedRegionPosition(){
        if(regionPath != null && regionPath.hasNext()){
            startRegionVertex = nextRegionVertex;
            nextRegionVertex = regionPath.next();
            return true;
        }
        else{
            return false;
        }
    }

    private void getInitialNextPosition(){
        getNextPosition();
    }

    private void getInitialNextRegionPosition(){
        nextPlannedRegionPosition();
        nextPlannedRegionPosition();
    }

    void dropPlan(){
        localPath = null;
        regionPath = null;
    }


}
