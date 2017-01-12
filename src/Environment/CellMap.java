package Environment;


import Agent.Agent;
import dissim.simspace.core.BasicSimEntity;
import dissim.simspace.core.SimModel;
import javafx.geometry.Point2D;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(exclude="agentsList", callSuper = false)
public class CellMap extends BasicSimEntity implements Map{

    private int mapScreenHeight;
    private int mapScreenWidth;
    private int mapWorldHeight;
    private int mapWorldWidth;
    private int cellSize;
    private Cell[][] cells;
    private GraphMap graphMap;
    private ArrayList<Agent> agentsList;
    private ArrayList<GraphNode> doorsList;

    public CellMap(int mapScreenWidth, int mapScreenHeight, int cellSize){
        super(SimModel.getInstance().getCommonSimContext());
        this.mapScreenHeight = mapScreenHeight;
        this.mapScreenWidth = mapScreenWidth;
        this.cellSize = cellSize;
        this.agentsList = new ArrayList<>();
        this.doorsList = new ArrayList<>();
        cells = new Cell[mapScreenHeight][mapScreenWidth];
        this.mapWorldHeight = mapScreenHeight /cellSize;
        this.mapWorldWidth = mapScreenWidth/cellSize;
    }

    public void addAgent(Agent agent){
        agentsList.add(agent);
    }

    public void setCellReservationStatus(GraphNode cellCoordinates, boolean status){
        cells[cellCoordinates.getX()][cellCoordinates.getY()].setReserved(status);
    }

    public void setCellOccupancyStatus(GraphNode cellCoordinates, boolean status){
        cells[cellCoordinates.getX()][cellCoordinates.getY()].setHasAgent(status);
    }

    public Cell getCell(int x, int y){
        return cells[x][y];
    }

    public GraphMap createAndGetGraphMap(){
        this.graphMap = new GraphMap(this);
        return graphMap;
    }

    public Point2D conversionToScreenCoordinates(GraphNode coordinates){
        return new Point2D(coordinates.getX()*cellSize, coordinates.getY()*cellSize);
    }

}
