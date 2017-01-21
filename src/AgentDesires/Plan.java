package AgentDesires;

import Agent.AgentState;
import Environment.Graph;
import Environment.GraphVertex;
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

    Plan(Desire parentDesire, GraphVertex startVertex){
        this.parentDesire = parentDesire;
        this.graphMap = parentDesire.getParentAgent().getBeliefs().getGraphMap();
        this.startRegionVertex = startVertex;
        this.regionPath = null;
        this.randomPath = false;
    }

    void createShortestTopPath(GraphVertex endRegionVertex){
        DijkstraShortestPath<GraphVertex, DefaultEdge> graphPath = graphMap.getShortestPath(graphMap.getGraphRegions(), startRegionVertex, endRegionVertex);
        regionPath = graphPath.getPath().getVertexList().iterator();
        //System.out.println("NEW REGIONPATH" + graphPath.getPath().getVertexList());
        setInitialNextPosition();
    }

    void createSearchTopPath(){
        regionPath = graphMap.getRegionSearchPath(startRegionVertex);
    }

    void createWanderLocalPath(){
        parentDesire.getParentAgent().setAgentState(AgentState.WANDER);
        GraphVertex startVertex = parentDesire.getParentAgent().getPosition();
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
        getNextPosition(); //pierwsza jest aktualna poz agenta.
        //System.out.println("NEW LOCALPATH" + graphPath.getPath().getVertexList());
    }


    public GraphVertex getNextPosition(){
        if(!randomPath) {
            return getNextPlannedPosition();
        }
        else{
            return getNextWanderPosition();
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
            else
                return null;
        }
    }

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
            parentDesire.action();///WARUNEK STOPU DESIRE
            return false;
        }
    }

    private void setInitialNextPosition(){
        nextPlannedRegionPosition();
        nextPlannedRegionPosition();
    }

    public void dropPlan(){
        localPath = null;
        regionPath = null;
    }

}
