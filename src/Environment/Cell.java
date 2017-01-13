package Environment;

import javafx.geometry.Point2D;
import lombok.Data;

@Data
class Cell {
    private WorldCoordinates worldCoordinates;
    private Point2D screenCoordinates;
    private CellType cellType;
    private int cellSize;

    Cell(WorldCoordinates worldCoordinates, int cellSize){
        this.cellSize = cellSize;
        this.worldCoordinates = worldCoordinates;
        this.screenCoordinates = new Point2D(
                worldCoordinates.getX()*cellSize,
                worldCoordinates.getY()*cellSize);
    }
}
