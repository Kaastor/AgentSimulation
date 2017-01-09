package Environment;


import Agent.Agent;
import javafx.geometry.Point2D;
import lombok.Data;

@Data
public class Cell {

    private boolean hasAgent;
    private boolean isReserved;
    private Point2D coordinates;
    private CellType cellType;

    private Map mapParent;
    private Agent agent;

    Cell(Map mapParent, Point2D coordinates, CellType cellType){
        this.hasAgent = false;
        this.isReserved = false;
        this.mapParent = mapParent;
        this.agent = agent;
        this.coordinates = coordinates;
        this.cellType = cellType;
    }
}
