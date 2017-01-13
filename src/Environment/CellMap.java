package Environment;


import Agent.Agent;
import dissim.simspace.core.BasicSimEntity;
import dissim.simspace.core.SimModel;
import javafx.geometry.Point2D;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(exclude="agentsList", callSuper = false)
public class CellMap extends BasicSimEntity implements Map{

    private int mapScreenHeight;
    private int mapScreenWidth;
    private int mapWorldHeight;
    private int mapWorldWidth;
    private int cellSize;
    @Getter(AccessLevel.PROTECTED) private Cell[][] cells;
    private GraphMap graphMap;
    private ArrayList<Agent> agentsList;
    private ArrayList<WorldCoordinates> doorsList;

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

        initializeMapWithCells();
    }

    private void initializeMapWithCells(){
        for(int i = 0; i < mapWorldWidth; i++){
            for(int j = 0; j < mapWorldHeight ; j++){
                addCell(i, j);
            }
        }
    }

    private void addCell(int worldX, int worldY){
        cells[worldX][worldY] = new Cell(new WorldCoordinates(worldX, worldY), cellSize, CellType.FLOOR);
    }

    public CellType getCellType(int x, int y){
        return cells[x][y].getCellType();
    }

    public void setCellToFloor(int worldX, int worldY){
        cells[worldX][worldY].setCellType(CellType.FLOOR);
    }

    public void setCellToDoor(int worldX, int worldY){
        cells[worldX][worldY].setCellType(CellType.DOOR);
    }

    public void setCellToWall(int worldX, int worldY){
        cells[worldX][worldY].setCellType(CellType.WALL);
    }

    public void addAgent(Agent agent){
        agentsList.add(agent);
    }

    //lista potrzebna?
    public void addDoor(WorldCoordinates doorCoordinates){ doorsList.add(doorCoordinates);}

    private Cell getCell(int worldX, int worldY){
        return cells[worldX][worldY];
    }

    public Point2D getCellScreenCoordinates(int x, int y){
        return getCell(x, y).getScreenCoordinates();
    }

    //logika
    public void setCellReservationStatus(WorldCoordinates cellCoordinates, boolean status){
        cells[cellCoordinates.getX()][cellCoordinates.getY()].setReserved(status);
    }

    public void setCellOccupancyStatus(WorldCoordinates cellCoordinates, boolean status){
        cells[cellCoordinates.getX()][cellCoordinates.getY()].setHasAgent(status);
    }

    public GraphMap createAndGetGraphMap(){
        this.graphMap = new GraphMap(this);
        return graphMap;
    }



}
