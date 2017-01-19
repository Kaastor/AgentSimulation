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

    public Plan(Desire parentDesire, GraphVertex startVertex){
        this.parentDesire = parentDesire;
        this.graphMap = parentDesire.getParentAgent().getBeliefs().getGraphMap();
        this.startRegionVertex = startVertex;
    }

    public void createShortestTopPath(GraphVertex endRegionVertex){
        DijkstraShortestPath<GraphVertex, DefaultEdge> graphPath = graphMap.getShortestPath(graphMap.getGraphRegions(), startRegionVertex, endRegionVertex);
        regionPath = graphPath.getPath().getVertexList().iterator();
        setInitialNextPosition();
    }

    public void createSearchTopPath(){
        regionPath = graphMap.getRegionSearchPath(startRegionVertex);
    }

    public void createPath(){
        BidirectionalDijkstraShortestPath<GraphVertex, DefaultEdge> graphPath = graphMap.getShortestPathBi(graphMap.getGraphCells(), startRegionVertex, nextRegionVertex);
        localPath = graphPath.getPath().getVertexList().iterator();
        getNextPosition(); //pierwsza jest aktualna poz agenta.
        System.out.println("NEW LOCALPATH" + graphPath.getPath().getVertexList());
    }

    public GraphVertex getNextPosition(){
        if(localPath.hasNext()){
            return localPath.next();
        }
        else {
            if(nextRegionPosition()){
                createPath();
                return getNextPosition();
            }
            return null;
        }
    }

    private boolean nextRegionPosition(){
        if(regionPath.hasNext()){
            startRegionVertex = nextRegionVertex;
            nextRegionVertex = regionPath.next();
            return true;
        }
        else{
            parentDesire.finalAction();
            return false;
        }
    }

    private void setInitialNextPosition(){
        nextRegionPosition();
        nextRegionPosition();
    }

    public void dropPlan(){
        localPath = null;
        regionPath = null;
    }

}
