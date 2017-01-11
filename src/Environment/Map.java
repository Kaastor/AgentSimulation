package Environment;


import Agent.Agent;
import dissim.broker.IEvent;
import dissim.broker.IEventPublisher;
import dissim.broker.IEventSubscriber;
import dissim.simspace.core.BasicSimEntity;
import dissim.simspace.core.SimModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(exclude="agents", callSuper = false)
public class Map extends BasicSimEntity implements IEventSubscriber{

    private int mapHeight;
    private int mapWidth;
    private int mapWorldHeight;
    private int mapWorldWidth;
    private int cellSize;
    private Cell[][] cellMap;
    private GraphMap graphMap;
    private ArrayList<Agent> agents;
    private ArrayList<GraphNode> doors;
    private boolean dirtyFlag;

    public Map(int mapWidth, int mapHeight, int cellSize){
        super(SimModel.getInstance().getCommonSimContext());
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.cellSize = cellSize;
        this.agents = new ArrayList<>();
        this.doors = new ArrayList<>();
        cellMap = new Cell[mapHeight][mapWidth];
        this.mapWorldHeight = mapHeight/cellSize;
        this.mapWorldWidth = mapWidth/cellSize;
        //
        this.getContextInstance().getContextEventBroker().subscribe(Agent.class, this);
        this.dirtyFlag = false;
    }

    public void addAgent(Agent agent){
        cellMap[agent.getPositionOnMap().getX()][agent.getPositionOnMap().getY()]
                .setCellType(CellType.AGENT);
        agents.add(agent);
    }

    public void moveAgent(Agent agent){
        setCellOccupancyStatus(agent.getPositionOnMap(), false);
        agents.get(agent.getId()).setPositionOnMap(agent.getNextPositionOnMap());
        setCellOccupancyStatus(agent.getNextPositionOnMap(), true);
        setDirtyFlag(true);

    }

    public void setCellReservationStatus(GraphNode cellCoordinates, boolean status){
        cellMap[cellCoordinates.getX()][cellCoordinates.getY()].setReserved(status);
    }

    public void setCellOccupancyStatus(GraphNode cellCoordinates, boolean status){
        cellMap[cellCoordinates.getX()][cellCoordinates.getY()].setHasAgent(status);
    }

    public Cell getCell(int x, int y){
        return cellMap[x][y];
    }

    public GraphMap createAndGetGraphMap(){
        this.graphMap = new GraphMap(this);
        return graphMap;
    }


    @Override
    public void reflect(IEvent iEvent, IEventPublisher iEventPublisher) {
        moveAgent((Agent) iEventPublisher);
        System.out.println("event");
    }

    @Override
    public void reflect(IEvent iEvent) {

    }
}
