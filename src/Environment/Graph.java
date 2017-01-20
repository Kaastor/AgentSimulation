package Environment;

import Agent.Agent;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.BidirectionalDijkstraShortestPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.GraphIterator;

public interface Graph {
    int SHOPS_NUMBER = 6;
    int EXITS_NUMBER = 3;
    String REGION = "REGION_";
    String SHOP = "SHOP_";
    String SHOP_CENTER = "_CENTER";
    String ENTRANCE = "ENTRANCE";

    void dismissAgent(Agent agent);
    GraphVertex getVertex(WorldCoordinates vertexCoordinates);
    GraphVertex getVertex(GraphVertex vertex);
    GraphVertex getRegionVertex(WorldCoordinates vertexCoordinates);
    GraphVertex getShopPosition(int shopNumber);
    GraphVertex getExitPosition();
    GraphVertex getShopCenterPosition(int shopNumber);
    UndirectedGraph<GraphVertex, DefaultEdge> getGraphCells();
    UndirectedGraph<GraphVertex, DefaultEdge> getGraphRegions();
    GraphIterator<GraphVertex, DefaultEdge> getRegionSearchPath(GraphVertex startPosition);
    GraphIterator<GraphVertex, DefaultEdge> getRandomWalkPath(GraphVertex startPosition);
    DijkstraShortestPath<GraphVertex, DefaultEdge> getShortestPath(
            UndirectedGraph<GraphVertex, DefaultEdge> graph, GraphVertex startPosition, GraphVertex endPosition);
    BidirectionalDijkstraShortestPath<GraphVertex, DefaultEdge> getShortestPathBi(
            UndirectedGraph<GraphVertex, DefaultEdge> graph, GraphVertex startPosition, GraphVertex endPosition);
}
