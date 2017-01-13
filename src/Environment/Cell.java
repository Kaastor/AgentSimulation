package Environment;


import Agent.Agent;
import javafx.geometry.Point2D;
import lombok.Data;

@Data
public class Cell {
    //czesc wizualizacyjna
    private WorldCoordinates worldCoordinates;
    private Point2D screenCoordinates;
    private CellType cellType;
    private int cellSize;

    //czesc logiczna
    private boolean hasAgent;
    private boolean isReserved;
    private Agent agent;

    Cell(WorldCoordinates worldCoordinates, int cellSize, CellType cellType){
        this.hasAgent = false;
        this.isReserved = false;
        this.cellSize = cellSize;
        this.worldCoordinates = worldCoordinates;
        this.screenCoordinates = new Point2D(
                worldCoordinates.getX()*cellSize,
                worldCoordinates.getY()*cellSize);
        this.cellType = cellType;
    }
}
