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

    private void addWallsInWorldCoordinatesX(WorldCoordinates from, WorldCoordinates to){
        for(int x = from.getX() ; x <= to.getX() ; x++){
            cellMap.addTypeToCell(CellType.WALL, x, from.getY());
        }
    }

    private void addWallsInWorldCoordinatesY(WorldCoordinates from, WorldCoordinates to){
        for(int y = from.getY() ; y <= to.getY() ; y++){
            cellMap.addTypeToCell(CellType.WALL,from.getX(), y);
        }
    }

    private void addWalls(){
        //ściany zewnetrzne, entrance
        addWallsInWorldCoordinatesX(new WorldCoordinates(0, 0), new WorldCoordinates(20, 0));
        addWallsInWorldCoordinatesX(new WorldCoordinates(23, 0), new WorldCoordinates(47, 0));
        addWallsInWorldCoordinatesX(new WorldCoordinates(0, 31), new WorldCoordinates(47, 31));
        //entrance
        addWallsInWorldCoordinatesY(new WorldCoordinates(0, 0), new WorldCoordinates(0, 13));
        addWallsInWorldCoordinatesY(new WorldCoordinates(0, 16), new WorldCoordinates(0, 31));
        //entrance
        addWallsInWorldCoordinatesY(new WorldCoordinates(47, 0), new WorldCoordinates(47, 13));
        addWallsInWorldCoordinatesY(new WorldCoordinates(47, 16), new WorldCoordinates(47, 31));
        //sklep 0.&1.
        addWallsInWorldCoordinatesX(new WorldCoordinates(1, 10), new WorldCoordinates(1, 10));
        addWallsInWorldCoordinatesX(new WorldCoordinates(4, 10), new WorldCoordinates(9, 10));
        addWallsInWorldCoordinatesX(new WorldCoordinates(12, 10), new WorldCoordinates(16, 10));
        addWallsInWorldCoordinatesY(new WorldCoordinates(16, 1), new WorldCoordinates(16, 10));
        addWallsInWorldCoordinatesY(new WorldCoordinates(6, 1), new WorldCoordinates(6, 10));
        //sklep 2.&3.
        addWallsInWorldCoordinatesX(new WorldCoordinates(27, 10), new WorldCoordinates(40, 10));
        addWallsInWorldCoordinatesX(new WorldCoordinates(43, 10), new WorldCoordinates(47, 10));
        addWallsInWorldCoordinatesY(new WorldCoordinates(27, 1), new WorldCoordinates(27, 5));
        addWallsInWorldCoordinatesY(new WorldCoordinates(27, 8), new WorldCoordinates(27, 10));
        addWallsInWorldCoordinatesY(new WorldCoordinates(37, 1), new WorldCoordinates(37, 10));
        //sklep 4. - przed regionem
        addWallsInWorldCoordinatesX(new WorldCoordinates(1, 19), new WorldCoordinates(5, 19));
        addWallsInWorldCoordinatesX(new WorldCoordinates(8, 19), new WorldCoordinates(47, 19));
        addWallsInWorldCoordinatesY(new WorldCoordinates(16, 20), new WorldCoordinates(16, 20));
        addWallsInWorldCoordinatesY(new WorldCoordinates(16, 23), new WorldCoordinates(16, 31));
        addWallsInWorldCoordinatesX(new WorldCoordinates(3, 24), new WorldCoordinates(15, 24));
        //sklep 5. - na koncu regionu 2.
        addWallsInWorldCoordinatesY(new WorldCoordinates(37, 20), new WorldCoordinates(37, 23));
        addWallsInWorldCoordinatesY(new WorldCoordinates(37, 26), new WorldCoordinates(37, 31));
        //fontanna
        addWallsInWorldCoordinatesX(new WorldCoordinates(25, 24), new WorldCoordinates(27, 24));
        addWallsInWorldCoordinatesX(new WorldCoordinates(25, 25), new WorldCoordinates(27, 25));
        addWallsInWorldCoordinatesX(new WorldCoordinates(25, 26), new WorldCoordinates(27, 26));
    }

    private void addDoorX(WorldCoordinates coordinates, CellType... regionTypes){
        cellMap.addTypeToCell(CellType.DOOR, coordinates.getX(), coordinates.getY());
        cellMap.addTypesToCell(coordinates.getX(), coordinates.getY(), regionTypes);
        cellMap.addDoor(new WorldCoordinates(coordinates.getX(), coordinates.getY()));
        cellMap.addTypeToCell(CellType.DOOR,coordinates.getX()+1, coordinates.getY());
        cellMap.addTypesToCell(coordinates.getX()+1, coordinates.getY(), regionTypes);
        cellMap.addDoor(new WorldCoordinates(coordinates.getX()+1, coordinates.getY()));
    }

    private void addDoorY(WorldCoordinates coordinates, CellType... regionTypes){
        cellMap.addTypeToCell(CellType.DOOR,coordinates.getX(), coordinates.getY());
        cellMap.addTypesToCell(coordinates.getX(), coordinates.getY(), regionTypes);
        cellMap.addDoor(new WorldCoordinates(coordinates.getX(), coordinates.getY()));
        cellMap.addTypeToCell(CellType.DOOR,coordinates.getX(), coordinates.getY()+1);
        cellMap.addTypesToCell(coordinates.getX(), coordinates.getY()+1, regionTypes);
        cellMap.addDoor(new WorldCoordinates(coordinates.getX(), coordinates.getY()+1));
    }

    private void addEntranceX(WorldCoordinates coordinates, CellType... regionTypes){
        cellMap.addTypeToCell(CellType.ENTRANCE,coordinates.getX(), coordinates.getY());
        cellMap.addTypesToCell(coordinates.getX(), coordinates.getY(), regionTypes);
        cellMap.addEntrance(new WorldCoordinates(coordinates.getX(), coordinates.getY()));
        cellMap.addTypesToCell(coordinates.getX()+1, coordinates.getY(), regionTypes);
        cellMap.addTypeToCell(CellType.ENTRANCE,coordinates.getX()+1, coordinates.getY());
        cellMap.addEntrance(new WorldCoordinates(coordinates.getX()+1, coordinates.getY()));
    }

    private void addEntranceY(WorldCoordinates coordinates, CellType... regionTypes){
        cellMap.addTypesToCell(coordinates.getX(), coordinates.getY(), regionTypes);
        cellMap.addTypeToCell(CellType.ENTRANCE,coordinates.getX(), coordinates.getY());
        cellMap.addEntrance(new WorldCoordinates(coordinates.getX(), coordinates.getY()));
        cellMap.addTypesToCell(coordinates.getX(), coordinates.getY()+1, regionTypes);
        cellMap.addTypeToCell(CellType.ENTRANCE,coordinates.getX(), coordinates.getY()+1);
        cellMap.addEntrance(new WorldCoordinates(coordinates.getX(), coordinates.getY()+1));
    }

    private void addDoors(){
        addDoorX(new WorldCoordinates(2, 10), CellType.REGION_1, CellType.SHOP_0); //1.
        addDoorX(new WorldCoordinates(10, 10), CellType.REGION_1, CellType.SHOP_1); //2.
        addDoorX(new WorldCoordinates(41, 10), CellType.REGION_1, CellType.SHOP_3); //4.
        addDoorX(new WorldCoordinates(6, 19), CellType.REGION_1, CellType.REGION_2); //przed-region
        addDoorX(new WorldCoordinates(1, 24), CellType.REGION_2, CellType.SHOP_4); //5.
        addDoorY(new WorldCoordinates(27, 6), CellType.REGION_1, CellType.SHOP_2); //3.
        addDoorY(new WorldCoordinates(16, 21), CellType.REGION_2, CellType.REGION_3); //region
        addDoorY(new WorldCoordinates(37, 24), CellType.REGION_3, CellType.SHOP_5); //6.
        addEntranceY(new WorldCoordinates(0, 14), CellType.REGION_1); //wejscie lewe
        addEntranceY(new WorldCoordinates(47, 14), CellType.REGION_1); //wejscie prawe
        addEntranceX(new WorldCoordinates(21, 0), CellType.REGION_1); //wejscie gorne
    }
}
