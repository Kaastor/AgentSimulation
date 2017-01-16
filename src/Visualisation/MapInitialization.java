package Visualisation;

import Environment.*;
import Agent.Agent;

public class MapInitialization {

    private final int maxAgentsNumber;
    private final CellMap cellMap;
    private GraphMap graphMap;

    public MapInitialization(int  mapWidth, int mapHeight, int cellSize, int maxAgentsNumber){
        this.cellMap = new CellMap(mapWidth, mapHeight, cellSize);
        this.maxAgentsNumber = maxAgentsNumber;
    }

    public Map initialize(){
        addFloors();
        addWalls();
        addDoors();
        this.graphMap = cellMap.createAndGetGraphMap();
//        addAgents(RandomGenerator.getInstance().uniformInt(maxAgentsNumber));
        addAgents(1);
        return cellMap;
    }

    private void addFloors(){
        for(int x = 0; x < cellMap.getMapWorldWidth(); x++){
            for(int y = 0; y < cellMap.getMapWorldHeight() ; y++){
                cellMap.addTypeToCell(CellType.FLOOR, x, y);
            }
        }
    }
    private void addAgents(int agentNumber){
        int count = 0;
        while(count != agentNumber){
            int entranceNumber = RandomGenerator.getInstance().uniformInt(0, cellMap.getEntrancesList().size());
            cellMap.addAgent(new Agent(graphMap, cellMap.getEntrancesList().get(entranceNumber), count));
            count++;
            }
    }

    private void addWallsInRealCoordinatesX(WorldCoordinates from, WorldCoordinates to){
        for(int x = from.getX() ; x <= to.getX() ; x++){
            cellMap.addTypeToCell(CellType.WALL, x, from.getY());
        }
    }

    private void addWallsInRealCoordinatesY(WorldCoordinates from, WorldCoordinates to){
        for(int y = from.getY() ; y <= to.getY() ; y++){
            cellMap.addTypeToCell(CellType.WALL,from.getX(), y);
        }
    }

    private void addWalls(){
        //ściany zewnetrzne
        addWallsInRealCoordinatesX(new WorldCoordinates(0, 0), new WorldCoordinates(47, 0));
        addWallsInRealCoordinatesX(new WorldCoordinates(0, 31), new WorldCoordinates(47, 31));
        addWallsInRealCoordinatesY(new WorldCoordinates(0, 0), new WorldCoordinates(0, 31));
        addWallsInRealCoordinatesY(new WorldCoordinates(47, 0), new WorldCoordinates(47, 31));
        //sklep 1.&2.
        addWallsInRealCoordinatesX(new WorldCoordinates(1, 10), new WorldCoordinates(16, 10));
        addWallsInRealCoordinatesY(new WorldCoordinates(16, 1), new WorldCoordinates(16, 10));
        addWallsInRealCoordinatesY(new WorldCoordinates(6, 1), new WorldCoordinates(6, 10));
        //sklep 3.&4.
        addWallsInRealCoordinatesX(new WorldCoordinates(27, 10), new WorldCoordinates(47, 10));
        addWallsInRealCoordinatesY(new WorldCoordinates(27, 1), new WorldCoordinates(27, 10));
        addWallsInRealCoordinatesY(new WorldCoordinates(37, 1), new WorldCoordinates(37, 10));
        //sklep 5. - przed regionem
        addWallsInRealCoordinatesX(new WorldCoordinates(1, 19), new WorldCoordinates(47, 19));
        addWallsInRealCoordinatesY(new WorldCoordinates(16, 20), new WorldCoordinates(16, 31));
        addWallsInRealCoordinatesX(new WorldCoordinates(1, 24), new WorldCoordinates(15, 24));
        //sklep 6. - na koncu regionu 2.
        addWallsInRealCoordinatesY(new WorldCoordinates(37, 20), new WorldCoordinates(37, 31));
        //fontanna
        addWallsInRealCoordinatesX(new WorldCoordinates(25, 24), new WorldCoordinates(27, 24));
        addWallsInRealCoordinatesX(new WorldCoordinates(25, 25), new WorldCoordinates(27, 25));
        addWallsInRealCoordinatesX(new WorldCoordinates(25, 26), new WorldCoordinates(27, 26));
    }

    private void addDoorX(WorldCoordinates coordinates){
        cellMap.addTypeToCell(CellType.DOOR, coordinates.getX(), coordinates.getY());
        cellMap.addDoor(new WorldCoordinates(coordinates.getX(), coordinates.getY()));
        cellMap.addTypeToCell(CellType.DOOR,coordinates.getX()+1, coordinates.getY());
        cellMap.addDoor(new WorldCoordinates(coordinates.getX()+1, coordinates.getY()));
    }

    private void addDoorY(WorldCoordinates coordinates){
        cellMap.addTypeToCell(CellType.DOOR,coordinates.getX(), coordinates.getY());
        cellMap.addDoor(new WorldCoordinates(coordinates.getX(), coordinates.getY()));
        cellMap.addTypeToCell(CellType.DOOR,coordinates.getX(), coordinates.getY()+1);
        cellMap.addDoor(new WorldCoordinates(coordinates.getX(), coordinates.getY()+1));
    }

    private void addEntranceX(WorldCoordinates coordinates){
        cellMap.addTypeToCell(CellType.ENTRANCE,coordinates.getX(), coordinates.getY());
        cellMap.addEntrance(new WorldCoordinates(coordinates.getX(), coordinates.getY()));
        cellMap.addTypeToCell(CellType.ENTRANCE,coordinates.getX()+1, coordinates.getY());
        cellMap.addEntrance(new WorldCoordinates(coordinates.getX()+1, coordinates.getY()));
    }

    private void addEntranceY(WorldCoordinates coordinates){
        cellMap.addTypeToCell(CellType.ENTRANCE,coordinates.getX(), coordinates.getY());
        cellMap.addEntrance(new WorldCoordinates(coordinates.getX(), coordinates.getY()));
        cellMap.addTypeToCell(CellType.ENTRANCE,coordinates.getX(), coordinates.getY()+1);
        cellMap.addEntrance(new WorldCoordinates(coordinates.getX(), coordinates.getY()+1));
    }

    private void addDoors(){
        addDoorX(new WorldCoordinates(2, 10)); //1.
        addDoorX(new WorldCoordinates(10, 10)); //2.
        addDoorX(new WorldCoordinates(41, 10)); //4.
        addDoorX(new WorldCoordinates(6, 19)); //przed-region
        addDoorX(new WorldCoordinates(1, 24)); //5.
        addDoorY(new WorldCoordinates(27, 6)); //3.
        addDoorY(new WorldCoordinates(16, 21)); //region
        addDoorY(new WorldCoordinates(37, 24)); //6.
        addEntranceY(new WorldCoordinates(0, 14)); //wejscie lewe
        addEntranceY(new WorldCoordinates(47, 14)); //wejscie prawe
        addEntranceX(new WorldCoordinates(21, 0)); //wejscie gorne
    }
}
