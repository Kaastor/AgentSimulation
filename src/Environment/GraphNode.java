package Environment;


import Agent.Agent;
import lombok.Data;

@Data
class GraphNode {

    private WorldCoordinates worldCoordinates;
    private boolean hasAgent;
    private boolean isReserved;
    private Agent agent;

    GraphNode(WorldCoordinates worldCoordinates){
        this.worldCoordinates = worldCoordinates;
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
