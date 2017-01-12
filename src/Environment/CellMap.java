package Environment;


import Agent.Agent;
import dissim.simspace.core.SimModel;
import javafx.geometry.Point2D;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(exclude="agents", callSuper = false)
public class CellMap {

    private int mapHeight;
    private int mapWidth;
    private int mapWorldHeight;
    private int mapWorldWidth;
    private int cellSize;
    private Cell[][] cellMap;
    private GraphMap graphMap;
    private ArrayList<Agent> agents;
    private ArrayList<GraphNode> doors;

    public CellMap(int mapWidth, int mapHeight, int cellSize){

        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.cellSize = cellSize;
        this.agents = new ArrayList<>();
        this.doors = new ArrayList<>();
        cellMap = new Cell[mapHeight][mapWidth];
        this.mapWorldHeight = mapHeight/cellSize;
        this.mapWorldWidth = mapWidth/cellSize;
    }

    public void addAgent(Agent agent){
        agents.add(agent);
    }

    public void setCellReservationStatus(GraphNode cellCoordinates, boolean status){
        cellMap[cellCoordinates.getX()][cellCoordinates.getY()].setReserved(status);
    }

    public void setCellOccupancyStatus(GraphNode cellCoordinates, boolean status){
        cellMap[cellCoordinates.getX()][cellCoordinates.getY()].setHasAgent(status);
    }

    public Cell getCell(int x, int y){
        return cellMap[x][y];
    }

    public GraphMap createAndGetGraphMap(){
        this.graphMap = new GraphMap(this);
        return graphMap;
    }

    public Point2D conversionToScreenCoordinates(GraphNode coordinates){
        return new Point2D(coordinates.getX()*cellSize, coordinates.getY()*cellSize);
    }

}
