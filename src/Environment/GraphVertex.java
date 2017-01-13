package Environment;


import Agent.Agent;
import lombok.Data;

@Data
class GraphVertex {

    private int id;
    private WorldCoordinates worldCoordinates;
    private boolean hasAgent;
    private boolean isReserved;
    private Agent agent;

    GraphVertex(int id, WorldCoordinates worldCoordinates){
        this.id = id;
        this.worldCoordinates = worldCoordinates;
    }

    GraphVertex(WorldCoordinates worldCoordinates){
        this.worldCoordinates = worldCoordinates;
    }

//    public boolean equals(GraphVertex graphVertex){
//        return worldCoordinates.equals(graphVertex.getWorldCoordinates());
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphVertex vertex = (GraphVertex) o;
        return worldCoordinates.equals(vertex.worldCoordinates);
    }

//
//    //logika
//    public void setCellReservationStatus(WorldCoordinates cellCoordinates, boolean status){
//        cells[cellCoordinates.getX()][cellCoordinates.getY()].setReserved(status);
//    }
//
//    public void setCellOccupancyStatus(WorldCoordinates cellCoordinates, boolean status){
//        cells[cellCoordinates.getX()][cellCoordinates.getY()].setHasAgent(status);
//    }
}
