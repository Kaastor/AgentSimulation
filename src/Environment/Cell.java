package Environment;


import Agent.Agent;
import lombok.Data;

//powoduje stackoverfloww
@Data
public class Cell {

    private boolean hasAgent;
    private boolean isReserved;
    private GraphNode worldCoordinates;
    private CellType cellType;

    private Map mapParent;
    private Agent agent;

    Cell(Map mapParent, GraphNode worldCoordinates, CellType cellType){
        this.hasAgent = false;
        this.isReserved = false;
        this.mapParent = mapParent;
        this.worldCoordinates = worldCoordinates;
        this.cellType = cellType;
    }
}
