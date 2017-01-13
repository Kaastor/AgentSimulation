package Environment;


import lombok.Data;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

@Data
public class GraphMap {

    private UndirectedGraph<GraphNode, DefaultEdge> graph;
    private Map map;

    public GraphMap(Map map){
        this.map = map;
        this.graph = new SimpleGraph<>(DefaultEdge.class);
        createGraphFromMap();
    }

    private void createGraphFromMap(){
        addNodesToGraph();
    }

    private void addNodesToGraph() {
        for (int x = 0; x < map.getMapWorldWidth(); x++) {
            for (int y = 0; y < map.getMapWorldHeight(); y++) {
                if(cellIsWalkable(x, y)){
                    graph.addVertex(new GraphNode(new WorldCoordinates(x, y)));
                }
            }
        }
    }

    private boolean cellIsWalkable(int worldX, int worldY){
        return map.getCellType(worldX, worldY) == (CellType.FLOOR)
                || map.getCellType(worldX, worldY) == (CellType.DOOR);
    }
}
