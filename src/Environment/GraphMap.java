package Environment;


import lombok.Data;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;
import org.jgrapht.traverse.RandomWalkIterator;

import java.util.ArrayList;
import java.util.Set;

@Data
public class GraphMap implements Graph{

    private final String REGION = "REGION";
    private UndirectedGraph<GraphVertex, DefaultEdge> graphCells;
    private UndirectedGraph<GraphVertex, DefaultEdge> graphRegions;
    private ArrayList<GraphVertex> cellVertices;
    private ArrayList<GraphVertex> regionsVertices;
    private Map map;


    GraphMap(Map map){
        this.map = map;
        this.graphCells = new SimpleGraph<>(DefaultEdge.class);
        this.graphRegions = new SimpleGraph<>(DefaultEdge.class);
        this.cellVertices = new ArrayList<>();
        this.regionsVertices = new ArrayList<>();
        createGraphFromMap();

//        for(GraphVertex vertex : regionsVertices){
//            System.out.println(vertex);
//        }
    }

    private void createGraphFromMap(){
        addVerticesToGraphs();
        addEdgesToCellsGraph();
        addEdgesToRegionsGraph();
    }

    private void addVerticesToGraphs() {
        int count = 0;
        for (int y = 0; y < map.getMapWorldHeight(); y++) {
            for (int x = 0; x < map.getMapWorldWidth(); x++) {
                if (cellIsWalkable(new WorldCoordinates(x,y))) {
                    GraphVertex vertex = new GraphVertex(count, new WorldCoordinates(x, y));
                    addVertexToRegionsGraph(new WorldCoordinates(x,y), vertex);
                    addVertexToCellGraph(vertex);
                    count++;
                }
            }
        }
    }

    private void addVertexToCellGraph(GraphVertex vertex){
        graphCells.addVertex(vertex);
        cellVertices.add(vertex);
    }

    private void addVertexToRegionsGraph(WorldCoordinates cellCoordinates, GraphVertex vertex){
        if(cellIsRegion(cellCoordinates)){
            vertex.setTypes(map.getCellTypes(cellCoordinates.getX(), cellCoordinates.getY()));
            System.out.println(vertex);/////
            graphRegions.addVertex(vertex);
            regionsVertices.add(vertex);
        }
    }

    private void addEdgesToRegionsGraph(){
        for(GraphVertex checkedVertex : graphCells.vertexSet()){
            ArrayList<CellType> regionTypes = getVertexRegions(checkedVertex);
            for(CellType type : regionTypes) {
                Set<GraphVertex> vertexSet = graphCells.vertexSet();
                for (GraphVertex matchedVertex : vertexSet) {
                    if(cellIsSpecificRegion(matchedVertex, type) && !matchedVertex.equals(checkedVertex)) {
                        graphRegions.addEdge(checkedVertex, matchedVertex);
                    }
                }
            }
        }
    }

    private ArrayList<CellType> getVertexRegions(GraphVertex vertex){
        ArrayList<CellType> types = map.getCellTypes(vertex.getWorldCoordinates().getX(),
                vertex.getWorldCoordinates().getY());
        ArrayList<CellType> regionTypes = new ArrayList<>();
        for(CellType cellType : types){
            if(cellType.toString().contains(REGION))
                regionTypes.add(cellType);
        }
        return regionTypes;
    }

    private void addEdgesToCellsGraph(){
        for (GraphVertex vertex : cellVertices){
            addEdgesToVertex(vertex);
        }
    }

    private void addEdgesToVertex(GraphVertex vertexFrom) {
        boolean cellsAroundExist = true;
        if(cellExistAndIsWalkable(getVertexUpCoordinates(vertexFrom))){
            GraphVertex graphVertexTo = getVertex(getVertexUpCoordinates(vertexFrom));
            graphCells.addEdge(vertexFrom, graphVertexTo);
        }
        else{
            cellsAroundExist = false;
        }
        if(cellExistAndIsWalkable(getVertexRightCoordinates(vertexFrom))){
            GraphVertex graphVertexTo = getVertex(getVertexRightCoordinates(vertexFrom));
            graphCells.addEdge(vertexFrom, graphVertexTo);
        }
        else{
            cellsAroundExist = false;
        }
        if(cellsAroundExist && cellExistAndIsWalkable(getVertexUpRightCoordinates(vertexFrom))){
            GraphVertex graphVertexTo = getVertex(getVertexUpRightCoordinates(vertexFrom));
            graphCells.addEdge(vertexFrom, graphVertexTo);
        }
    }

    public GraphVertex getVertex(WorldCoordinates vertexCoordinates){
        return searchForVertex(vertexCoordinates);
    }

    private GraphVertex searchForVertex(WorldCoordinates vertexCoordinates){
        GraphVertex searchedVertex = null;
        for (GraphVertex vertex : cellVertices){
            if(vertex.equals(new GraphVertex(vertexCoordinates)))
                searchedVertex = vertex;
        }
        return searchedVertex;
    }

    private boolean cellIsWalkable(WorldCoordinates cellCoordinates){
        return map.getCellTypes(cellCoordinates.getX(), cellCoordinates.getY()).
                contains(CellType.DOOR) ||
                map.getCellTypes(cellCoordinates.getX(), cellCoordinates.getY()).
                        contains(CellType.ENTRANCE) ||
                map.getCellTypes(cellCoordinates.getX(), cellCoordinates.getY()).
                        contains(CellType.FLOOR);
    }

    private boolean cellIsRegion(WorldCoordinates cellCoordinates){
        ArrayList<CellType> cellTypes = map.getCellTypes(cellCoordinates.getX(), cellCoordinates.getY());
        return (cellTypes.toString().contains(REGION));
    }

    private boolean cellIsSpecificRegion(GraphVertex vertex, CellType region){
        ArrayList<CellType> cellTypes = map.getCellTypes(vertex.getWorldCoordinates().getX(),
                vertex.getWorldCoordinates().getY());
        return (cellTypes.contains(region));
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

    public GraphIterator<GraphVertex, DefaultEdge> getWholeMapSearchPath(GraphVertex startPosition){
        return new DepthFirstIterator<>(graphCells, startPosition);
    }

    public GraphIterator<GraphVertex, DefaultEdge> getRandomWalkPath(GraphVertex startPosition){
        return new RandomWalkIterator<>(graphCells, startPosition);
    }

    public DijkstraShortestPath<GraphVertex, DefaultEdge> getShortestPath(GraphVertex startPosition, GraphVertex endPosition){
        return new DijkstraShortestPath<>(graphCells, startPosition, endPosition);
    }


}
