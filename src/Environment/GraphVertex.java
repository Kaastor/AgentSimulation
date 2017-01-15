package Environment;


import Agent.Agent;
import lombok.Data;

@Data
public class GraphVertex {

    private int id;
    private WorldCoordinates worldCoordinates;
    private boolean hasAgent;
    private boolean isReserved;
    private Agent agent;

    GraphVertex(int id, WorldCoordinates worldCoordinates){
        this.id = id;
        this.worldCoordinates = worldCoordinates;
    }

    public GraphVertex(WorldCoordinates worldCoordinates){
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
