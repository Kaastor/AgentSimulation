package Environment;


import Agent.Agent;
import dissim.simspace.core.BasicSimEntity;
import dissim.simspace.core.SimModel;
import javafx.geometry.Point2D;
import lombok.*;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "agentsList")
public class CellMap extends BasicSimEntity implements Map{

    public final int regionsNumber = 3;
    private int mapScreenHeight;
    private int mapScreenWidth;
    private int mapWorldHeight;
    private int mapWorldWidth;
    private int cellSize;
    @Setter(AccessLevel.PRIVATE)@Getter(AccessLevel.PROTECTED) private Cell[][] cells;
    private ArrayList<Agent> agentsList;
    private ArrayList<WorldCoordinates> doorsList;
    private ArrayList<WorldCoordinates> entrancesList;

    public CellMap(int mapScreenWidth, int mapScreenHeight, int cellSize){
        super(SimModel.getInstance().getCommonSimContext());
        this.mapScreenHeight = mapScreenHeight;
        this.mapScreenWidth = mapScreenWidth;
        this.cellSize = cellSize;
        this.agentsList = new ArrayList<>();
        this.doorsList = new ArrayList<>();
        this.entrancesList = new ArrayList<>();
        cells = new Cell[mapScreenHeight][mapScreenWidth];
        this.mapWorldHeight = mapScreenHeight /cellSize;
        this.mapWorldWidth = mapScreenWidth/cellSize;

        initializeMapWithCells();
    }

    private void initializeMapWithCells(){
        for(int x = 0; x < mapWorldWidth; x++){
            for(int y = 0; y < mapWorldHeight ; y++){
                addCell(x, y);
            }
        }
    }

    private void addCell(int worldX, int worldY){
        cells[worldX][worldY] = new Cell(new WorldCoordinates(worldX, worldY), cellSize);
    }

    public ArrayList<CellType> getCellTypes(int worldX, int worldY){
        return cells[worldX][worldY].getCellTypeList();
    }

    public void addTypeToCell(CellType type, int worldX, int worldY){
        cells[worldX][worldY].addCellTypeToList(type);
    }

    public void addAgent(Agent agent){
        agentsList.add(agent);
    }

    public void addDoor(WorldCoordinates doorCoordinates){ doorsList.add(doorCoordinates);}

    public void addEntrance(WorldCoordinates entranceCoordinates){
        entrancesList.add(entranceCoordinates);}

    private Cell getCell(int worldX, int worldY){
        return cells[worldX][worldY];
    }

    public boolean cellExist(WorldCoordinates worldCoordinates){
        return worldCoordinates.getX() < mapWorldWidth &&
                worldCoordinates.getY() < mapWorldHeight &&
                worldCoordinates.getX() >= 0 &&
                worldCoordinates.getY() >= 0;
    }

    public Point2D getCellScreenCoordinates(int worldX, int worldY){
        return getCell(worldX, worldY).getScreenCoordinates();
    }

    public GraphMap createAndGetGraphMap(){
        return new GraphMap(this);
    }
}
