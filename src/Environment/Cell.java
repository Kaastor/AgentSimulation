package Environment;


import Agent.Agent;
import lombok.Data;

@Data
public class Cell {

    private boolean hasAgent;
    private boolean isReserved;
    private GraphNode coordinates;
    private CellType cellType;

    private Map mapParent;
    private Agent agent;

    Cell(Map mapParent, GraphNode coordinates, CellType cellType){
        this.hasAgent = false;
        this.isReserved = false;
        this.mapParent = mapParent;
        this.coordinates = coordinates;
        this.cellType = cellType;
    }
}
