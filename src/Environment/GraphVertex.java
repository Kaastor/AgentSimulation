package Environment;


import Agent.Agent;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GraphVertex {

    private int id;
    private WorldCoordinates worldCoordinates;
    private boolean hasAgent;
    private boolean isReserved;
    private List<CellType> types;
    private Agent agent;

    GraphVertex(int id, WorldCoordinates worldCoordinates){
        //this.id = id; //do usunięcia?
        this.types = new ArrayList<>();
        this.worldCoordinates = worldCoordinates;
    }

    GraphVertex(WorldCoordinates worldCoordinates){
        this.worldCoordinates = worldCoordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphVertex vertex = (GraphVertex) o;
        return worldCoordinates.equals(vertex.worldCoordinates);
    }
}
