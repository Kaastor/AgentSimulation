package Environment;


import lombok.Data;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import java.util.ArrayList;

@Data
public class GraphMap {

    private UndirectedGraph<GraphVertex, DefaultEdge> graph;
    private ArrayList<GraphVertex> vertices;
    private Map map;


    public GraphMap(Map map){
        this.map = map;
        this.graph = new SimpleGraph<>(DefaultEdge.class);
        vertices = new ArrayList<>();
        createGraphFromMap();
    }

    private void createGraphFromMap(){
        addVerticesToGraph();
        addEdgesToGraph();
    }

    private void addVerticesToGraph() {
        int count = 0;
        for (int y = 0; y < map.getMapWorldHeight(); y++) {
            for (int x = 0; x < map.getMapWorldWidth(); x++) {
                if (cellIsWalkable(new WorldCoordinates(x,y))) {
                    GraphVertex vertex = new GraphVertex(count, new WorldCoordinates(x, y));
                    graph.addVertex(vertex);
                    vertices.add(vertex);
                    count++;
                }
            }
        }
    }

    private void addEdgesToGraph(){
        for (GraphVertex vertex : vertices){
            addEdgesToVertex(vertex);
        }
    }

    private void addEdgesToVertex(GraphVertex vertexFrom) {
        boolean cellsAroundExist = true;
        if(cellExistAndIsWalkable(getVertexUpCoordinates(vertexFrom))){
            GraphVertex graphVertexTo = getVertex(getVertexUpCoordinates(vertexFrom));
            graph.addEdge(vertexFrom, graphVertexTo);
        }
        else{
            cellsAroundExist = false;
        }
        if(cellExistAndIsWalkable(getVertexRightCoordinates(vertexFrom))){
            GraphVertex graphVertexTo = getVertex(getVertexRightCoordinates(vertexFrom));
            graph.addEdge(vertexFrom, graphVertexTo);
        }
        else{
            cellsAroundExist = false;
        }
        if(cellsAroundExist && cellExistAndIsWalkable(getVertexUpRightCoordinates(vertexFrom))){
            GraphVertex graphVertexTo = getVertex(getVertexUpRightCoordinates(vertexFrom));
            graph.addEdge(vertexFrom, graphVertexTo);
        }
    }

    public GraphVertex getVertex(WorldCoordinates vertexCoordinates){
        return searchForVertex(vertexCoordinates);
    }

    private GraphVertex searchForVertex(WorldCoordinates vertexCoordinates){
        GraphVertex searchedVertex = null;
        for (GraphVertex vertex : vertices){
            if(vertex.equals(new GraphVertex(vertexCoordinates)))
                searchedVertex = vertex;
        }
        return searchedVertex;
    }

    private boolean cellIsWalkable(WorldCoordinates cellCoordinates){
        return map.getCellType(cellCoordinates.getX(), cellCoordinates.getY()) == (CellType.FLOOR)
                || map.getCellType(cellCoordinates.getX(), cellCoordinates.getY()) == (CellType.DOOR);
    }

    private boolean cellExistAndIsWalkable(WorldCoordinates cellCoordinates){
        return (map.cellExist(cellCoordinates) && cellIsWalkable(cellCoordinates));
    }

    private WorldCoordinates getVertexUpCoordinates(GraphVertex vertex) {
        WorldCoordinates vertexCoordinates = vertex.getWorldCoordinates();
        return new WorldCoordinates(vertexCoordinates.getX(), vertexCoordinates.getY()-1 );
    }

    private WorldCoordinates getVertexRightCoordinates(GraphVertex vertex) {
        WorldCoordinates vertexCoordinates = vertex.getWorldCoordinates();
        return new WorldCoordinates(vertexCoordinates.getX()+1, vertexCoordinates.getY() );
    }

    private WorldCoordinates getVertexUpRightCoordinates(GraphVertex vertex) {
        WorldCoordinates vertexCoordinates = vertex.getWorldCoordinates();
        return new WorldCoordinates(vertexCoordinates.getX()+1, vertexCoordinates.getY()-1 );
    }

    public GraphIterator<GraphVertex, DefaultEdge> DepthFirstSearch(){
        return new DepthFirstIterator<>(graph);
    }
}
