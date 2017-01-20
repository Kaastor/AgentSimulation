package Environment;


import lombok.Data;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.alg.BidirectionalDijkstraShortestPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;
import org.jgrapht.traverse.RandomWalkIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class GraphMap implements Graph{

    private UndirectedGraph<GraphVertex, DefaultEdge> graphCells;
    private UndirectedGraph<GraphVertex, DefaultEdge> graphRegions;
    private List<GraphVertex> cellVertices;
    private List<GraphVertex> regionsVertices;
    private List<GraphVertex> shopsVertices;
    private List<GraphVertex> shopCentersVertices;
    private Map map;


    GraphMap(Map map){
        this.map = map;
        this.graphCells = new SimpleGraph<>(DefaultEdge.class);
        this.graphRegions = new SimpleGraph<>(DefaultEdge.class);
        this.cellVertices = new ArrayList<>();
        this.regionsVertices = new ArrayList<>();
        this.shopCentersVertices = new ArrayList<>();
        createGraphFromMap();
        this.shopsVertices = createShopList();



        List<GraphVertex> verticesAround = new ArrayList<>();
        Set<DefaultEdge> aroundEdges =  getGraphCells().edgesOf( getVertex(new WorldCoordinates(17, 8)));
        for(DefaultEdge edge : aroundEdges){
            verticesAround.add( getGraphCells().getEdgeSource(edge));
            verticesAround.add( getGraphCells().getEdgeTarget(edge));
        }
        verticesAround = verticesAround.parallelStream()
                .distinct()
                .collect(Collectors.toList());

//        for(GraphVertex vertex : closeVerticesAround){
//            System.out.println(vertex);
//        }

    }

    private void createGraphFromMap(){
        addVerticesToGraphs();
        addEdgesToCellsGraph();
        addEdgesToRegionsGraph();
    }

    private List<GraphVertex> createShopList(){
        List<GraphVertex> shops = new ArrayList<>();
        for(GraphVertex vertex : regionsVertices){
            if(vertex.getTypes().toString().contains(SHOP)) {
                shops.add(vertex);
            }
        }
        return shops;
    }

    private void addVerticesToGraphs() {
        for (int y = 0; y < map.getMapWorldHeight(); y++) {
            for (int x = 0; x < map.getMapWorldWidth(); x++) {
                if (cellIsWalkable(new WorldCoordinates(x,y))) {
                    GraphVertex vertex = new GraphVertex(new WorldCoordinates(x, y));
                    addVertexToRegionsGraph(new WorldCoordinates(x,y), vertex);
                    addVertexToShopCenters(new WorldCoordinates(x,y), vertex);
                    addVertexToCellGraph(vertex);
                }
            }
        }
    }

    private void addVertexToCellGraph(GraphVertex vertex){
        graphCells.addVertex(vertex);
        cellVertices.add(vertex);
    }

    private void addVertexToShopCenters(WorldCoordinates cellCoordinates, GraphVertex vertex){
        if(cellIsShopCenter(cellCoordinates)){
            vertex.setTypes(map.getCellTypes(cellCoordinates.getX(), cellCoordinates.getY()));
            shopCentersVertices.add(vertex);
        }
    }

    private void addVertexToRegionsGraph(WorldCoordinates cellCoordinates, GraphVertex vertex){
        if(cellIsRegion(cellCoordinates)){
            vertex.setTypes(map.getCellTypes(cellCoordinates.getX(), cellCoordinates.getY()));
            graphRegions.addVertex(vertex);
            regionsVertices.add(vertex);
        }
    }

    private void addEdgesToRegionsGraph(){
        for(GraphVertex checkedVertex : graphCells.vertexSet()){
            List<CellType> regionTypes = getVertexRegions(checkedVertex);
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

    private List<CellType> getVertexRegions(GraphVertex vertex){
        List<CellType> types = map.getCellTypes(vertex.getWorldCoordinates().getX(),
                vertex.getWorldCoordinates().getY());
        List<CellType> regionTypes = new ArrayList<>();
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
        boolean cellUpExist = true;
        boolean cellRightExist = true;
        if(cellExistAndIsWalkable(getVertexUpCoordinates(vertexFrom))){
            GraphVertex graphVertexTo = getVertex(getVertexUpCoordinates(vertexFrom));
            graphCells.addEdge(vertexFrom, graphVertexTo);
        }
        else{
            cellUpExist = false;
        }
        if(cellExistAndIsWalkable(getVertexRightCoordinates(vertexFrom))){
            GraphVertex graphVertexTo = getVertex(getVertexRightCoordinates(vertexFrom));
            graphCells.addEdge(vertexFrom, graphVertexTo);
        }
        else{
            cellRightExist = false;
        }
        if(cellUpExist && cellExistAndIsWalkable(getVertexUpRightCoordinates(vertexFrom))){
            GraphVertex graphVertexTo = getVertex(getVertexUpRightCoordinates(vertexFrom));
            graphCells.addEdge(vertexFrom, graphVertexTo);
        }

        if(cellRightExist && cellDownExist(vertexFrom) && cellExistAndIsWalkable(getVertexDownRightCoordinates(vertexFrom))){
            GraphVertex graphVertexTo = getVertex(getVertexDownRightCoordinates(vertexFrom));
            graphCells.addEdge(vertexFrom, graphVertexTo);
        }
    }

    public GraphVertex getVertex(GraphVertex vertex){
        return searchForVertex(vertex.getWorldCoordinates());
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

    public GraphVertex getRegionVertex(WorldCoordinates vertexCoordinates){
        return searchForRegionVertex(vertexCoordinates);
    }

    private GraphVertex searchForRegionVertex(WorldCoordinates vertexCoordinates){
        GraphVertex searchedVertex = null;
        for (GraphVertex vertex : regionsVertices){
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
        List<CellType> cellTypes = map.getCellTypes(cellCoordinates.getX(), cellCoordinates.getY());
        return cellTypes.toString().contains(REGION);
    }

    private boolean cellIsShopCenter(WorldCoordinates cellCoordinates){
        List<CellType> cellTypes = map.getCellTypes(cellCoordinates.getX(), cellCoordinates.getY());
        return cellTypes.toString().contains(SHOP_CENTER);
    }

    private boolean cellIsSpecificRegion(GraphVertex vertex, CellType region){
        List<CellType> cellTypes = map.getCellTypes(vertex.getWorldCoordinates().getX(),
                vertex.getWorldCoordinates().getY());
        return (cellTypes.contains(region));
    }

    private boolean cellExistAndIsWalkable(WorldCoordinates cellCoordinates){
        return (map.cellExist(cellCoordinates) && cellIsWalkable(cellCoordinates));
    }

    private boolean cellDownExist(GraphVertex vertex){
      return  cellExistAndIsWalkable(getVertexDownCoordinates(vertex));
    }

    private WorldCoordinates getVertexDownCoordinates(GraphVertex vertex) {
        WorldCoordinates vertexCoordinates = vertex.getWorldCoordinates();
        return new WorldCoordinates(vertexCoordinates.getX(), vertexCoordinates.getY()+1 );
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

    private WorldCoordinates getVertexDownRightCoordinates(GraphVertex vertex) {
        WorldCoordinates vertexCoordinates = vertex.getWorldCoordinates();
        return new WorldCoordinates(vertexCoordinates.getX()+1, vertexCoordinates.getY()+1 );
    }

    public GraphIterator<GraphVertex, DefaultEdge> getRegionSearchPath(GraphVertex startPosition){
        return new DepthFirstIterator<>(graphRegions, startPosition);
    }

    public GraphIterator<GraphVertex, DefaultEdge> getRandomWalkPath(GraphVertex startPosition){
        return new RandomWalkIterator<>(graphCells, startPosition);
    }

    public DijkstraShortestPath<GraphVertex, DefaultEdge> getShortestPath
            (UndirectedGraph<GraphVertex, DefaultEdge> graph, GraphVertex startPosition, GraphVertex endPosition){
        return new DijkstraShortestPath<>(graph, startPosition, endPosition);
    }

    public BidirectionalDijkstraShortestPath<GraphVertex, DefaultEdge> getShortestPathBi
            (UndirectedGraph<GraphVertex, DefaultEdge> graph, GraphVertex startPosition, GraphVertex endPosition){
        return new BidirectionalDijkstraShortestPath<>(graph, startPosition, endPosition);
    }

    public GraphVertex getShopPosition(int shopNumber){
        GraphVertex shopPosition = null;
        for(GraphVertex shop : shopsVertices){
            if(shop.getTypes().toString().contains(SHOP+shopNumber)){
                shopPosition = shop;
                break;
            }

        }
        return searchForVertex(shopPosition.getWorldCoordinates());
    }

    public GraphVertex getShopCenterPosition(int shopNumber){
        GraphVertex shopCenterPosition = null;
        for(GraphVertex shopCenter : shopCentersVertices){
            if(shopCenter.getTypes().toString().contains(SHOP+shopNumber+SHOP_CENTER)){
                shopCenterPosition = shopCenter;
                break;
            }
        }
        return searchForVertex(shopCenterPosition.getWorldCoordinates());
    }
}
