package Environment;


import Agent.Agent;
import javafx.geometry.Point2D;
import lombok.Data;

@Data
public class Cell {

    private boolean hasAgent;
    private boolean isReserved;
    private GraphNode worldCoordinates;
    private Point2D screenCoordinates;
    private CellType cellType;
    private int cellSize;

    private Agent agent;

    Cell(GraphNode worldCoordinates, int cellSize, CellType cellType){
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
