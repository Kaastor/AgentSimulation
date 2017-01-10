package Environment;


import Agent.Agent;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(exclude="agents")
public class Map {

    private int mapHeight;
    private int mapWidth;
    private int mapWorldHeight;
    private int mapWorldWidth;
    private int cellSize;
    private Cell[][] cellMap;
    private GraphMap graphMap;
    private ArrayList<Agent> agents;
    private ArrayList<GraphNode> doors;

    public Map(int mapWidth, int mapHeight, int cellSize){
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.cellSize = cellSize;
        this.agents = new ArrayList<>();
        this.doors = new ArrayList<>();
        cellMap = new Cell[mapHeight][mapWidth];
        this.mapWorldHeight = mapHeight/cellSize;
        this.mapWorldWidth = mapWidth/cellSize;
        new MapInitialization(this);
        this.graphMap = new GraphMap(this);
    }

    public void addAgent(Agent agent, int positionX, int positionY){
        cellMap[positionX][positionY].setCellType(CellType.AGENT);
        agents.add(agent);
    }

    public void setCellOccupancyStatus(GraphNode cellCoordinates, boolean status){
        cellMap[cellCoordinates.getX()][cellCoordinates.getY()].setReserved(status);
    }

}
