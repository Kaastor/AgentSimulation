package Environment;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.GraphIterator;

public interface Graph {

    GraphVertex getVertex(WorldCoordinates vertexCoordinates);
    GraphIterator<GraphVertex, DefaultEdge> getWholeMapSearchPath(GraphVertex startPosition);
    GraphIterator<GraphVertex, DefaultEdge> getRandomWalkPath(GraphVertex startPosition);
    DijkstraShortestPath<GraphVertex, DefaultEdge> getShortestPath(GraphVertex startPosition, GraphVertex endPosition);
}
