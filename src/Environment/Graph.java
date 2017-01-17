package Environment;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.GraphIterator;

public interface Graph {

    GraphVertex getVertex(WorldCoordinates vertexCoordinates);
    GraphVertex getRegionVertex(WorldCoordinates vertexCoordinates);
    int getShopsNumber();
    UndirectedGraph<GraphVertex, DefaultEdge> getGraphCells();
    UndirectedGraph<GraphVertex, DefaultEdge> getGraphRegions();
    GraphIterator<GraphVertex, DefaultEdge> getWholeMapSearchPath(GraphVertex startPosition);
    GraphIterator<GraphVertex, DefaultEdge> getRandomWalkPath(GraphVertex startPosition);
    DijkstraShortestPath<GraphVertex, DefaultEdge> getShortestPath(GraphVertex startPosition, GraphVertex endPosition);
}
