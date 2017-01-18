package Environment;


import Agent.Agent;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "agent")
public class GraphVertex {

    private WorldCoordinates worldCoordinates;
    private boolean occupied;
    private boolean reserved;
    private List<CellType> types;
    private Agent agent;

    GraphVertex(WorldCoordinates worldCoordinates){
        this.types = new ArrayList<>();
        this.worldCoordinates = worldCoordinates;
        this.occupied = false;
        this.reserved = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphVertex vertex = (GraphVertex) o;
        return worldCoordinates.equals(vertex.worldCoordinates);
    }

    private void setAgent(Agent agent){
        this.agent = agent;
        this.occupied = true;
    }

    private void removeAgent(){
        this.agent = null;
        this.occupied = false;
    }

    public boolean isFree(){
        return occupied && reserved;
    }

    public void reserve(){
        this.reserved = true;
    }

    private void removeReservation(){
        this.reserved = false;
    }

    public void free(){
        removeAgent();
        removeReservation();
    }

    public void occupy(Agent agent){
        occupied = true;
        setAgent(agent);
        reserve();
    }
}
