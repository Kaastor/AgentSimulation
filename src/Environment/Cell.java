package Environment;

import javafx.geometry.Point2D;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
class Cell {
    private WorldCoordinates worldCoordinates;
    private Point2D screenCoordinates;
    private List<CellType> cellTypeList;
    private int cellSize;

    Cell(WorldCoordinates worldCoordinates, int cellSize){
        this.cellSize = cellSize;
        this.cellTypeList = new ArrayList<>();
        this.worldCoordinates = worldCoordinates;
        this.screenCoordinates = new Point2D(
                worldCoordinates.getX()*cellSize,
                worldCoordinates.getY()*cellSize);
    }

    void addCellTypeToList(CellType type){
        if(type == CellType.WALL)
            cellTypeList.clear();
        cellTypeList.add(type);
    }
}
