package AgentDesires;

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

    Plan(Desire parentDesire, GraphVertex startVertex){
        this.parentDesire = parentDesire;
        this.graphMap = parentDesire.getParentAgent().getBeliefs().getGraphMap();
        this.startRegionVertex = startVertex;
        this.regionPath = null;
    }

    void createShortestTopPath(GraphVertex endRegionVertex){
        DijkstraShortestPath<GraphVertex, DefaultEdge> graphPath = graphMap.getShortestPath(graphMap.getGraphRegions(), startRegionVertex, endRegionVertex);
        regionPath = graphPath.getPath().getVertexList().iterator();
        setInitialNextPosition();
    }

    void createSearchTopPath(){
        regionPath = graphMap.getRegionSearchPath(startRegionVertex);
    }

    void createWanderLocalPath(){
        GraphVertex startVertex = parentDesire.getParentAgent().getPosition();
        localPath = graphMap.getRandomWalkPath(startVertex);
    }

    public void createPath(){
        GraphVertex startVertex = parentDesire.getParentAgent().getPosition();
        BidirectionalDijkstraShortestPath<GraphVertex, DefaultEdge> graphPath = graphMap.getShortestPathBi(graphMap.getGraphCells(), startVertex, nextRegionVertex);
        localPath = graphPath.getPath().getVertexList().iterator();
        getNextPosition(); //pierwsza jest aktualna poz agenta.
        System.out.println("NEW LOCALPATH" + graphPath.getPath().getVertexList());
    }

    public GraphVertex getNextPosition(){
        if(regionPath != null) {
            return getNextPlannedPosition();
        }
        else{
            return getNextWanderPosition();
        }
    }

    private GraphVertex getNextPlannedPosition(){
        if (localPath.hasNext()) {
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
        if(parentDesire.getAgentBeliefs().getVerticesAround().contains(parentDesire.getFinalPosition())){
            parentDesire.finalAction();
            return null;
        }
        else if(localPath.hasNext()){
            return localPath.next();
        }
        else
            return null;
    }

    private boolean nextPlannedRegionPosition(){
        if(regionPath.hasNext()){
            startRegionVertex = nextRegionVertex;
            nextRegionVertex = regionPath.next();
            return true;
        }
        else{
            parentDesire.finalAction();///WARUNEK STOPU DESIRE
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
