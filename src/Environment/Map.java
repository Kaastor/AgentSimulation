package Environment;


import Agent.Agent;
import javafx.geometry.Point2D;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Map {

    private int mapHeight;
    private int mapWidth;
    private int cellSize;

    private Cell[][] cellMap;
    private ArrayList<Agent> agents;

    public Map(int mapWidth, int mapHeight, int cellSize){
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.cellSize = cellSize;
        this.agents = new ArrayList<>();
        cellMap = new Cell[mapHeight][mapWidth];

        initializeMap();
    }

    private void initializeMap(){
        for(int i = 0 ; i < mapWidth/cellSize ; i++){
            for(int j = 0 ; j < mapHeight/cellSize ; j++){
                cellMap[i][j] = new Cell(this, new Point2D(i*cellSize, j*cellSize), CellType.FLOOR);
            }
        }
    }

}
