package Visualisation;

import Environment.*;
import dissim.random.SimGenerator;
import Agent.Agent;

public class MapInitialization {

    private CellMap cellMap;
    private int maxAgentsNumber;

    public MapInitialization(int  mapWidth, int mapHeight, int cellSize, int maxAgentsNumber){
        this.cellMap = new CellMap(mapWidth, mapHeight, cellSize);
        this.maxAgentsNumber = maxAgentsNumber;
    }

    public Map initialize(){
        addWalls();
        addDoors();
        addAgents(RandomGenerator.getInstance().uniformInt(maxAgentsNumber));
        return cellMap;
    }

    private void addAgents(int agentNumber){
        SimGenerator generator = RandomGenerator.getInstance();
        int count = 0, x, y;
        while(count != agentNumber){
            x = generator.uniformInt(0, cellMap.getMapWorldWidth());
            y = generator.uniformInt(0, cellMap.getMapWorldHeight());
            if(cellMap.getCellType(x,y) == CellType.FLOOR){
                cellMap.addAgent(new Agent(count, new WorldCoordinates(x, y)));
                count++;
            }
        }
    }

    private void addWallsInRealCoordX(WorldCoordinates from, WorldCoordinates to){
        for(int i = from.getX() ; i <= to.getX() ; i++){
            cellMap.setCellToWall(i, from.getY());
        }
    }

    private void addWallsInRealCoordY(WorldCoordinates from, WorldCoordinates to){
        for(int i = from.getY() ; i <= to.getY() ; i++){
            cellMap.setCellToWall(from.getX(), i);
        }
    }

    private void addWalls(){
        //ściany zewnetrzne
        addWallsInRealCoordX(new WorldCoordinates(0, 0), new WorldCoordinates(47, 0));
        addWallsInRealCoordX(new WorldCoordinates(0, 31), new WorldCoordinates(47, 31));
        addWallsInRealCoordY(new WorldCoordinates(0, 0), new WorldCoordinates(0, 31));
        addWallsInRealCoordY(new WorldCoordinates(47, 0), new WorldCoordinates(47, 31));
        //sklep 1.&2.
        addWallsInRealCoordX(new WorldCoordinates(1, 10), new WorldCoordinates(16, 10));
        addWallsInRealCoordY(new WorldCoordinates(16, 1), new WorldCoordinates(16, 10));
        addWallsInRealCoordY(new WorldCoordinates(6, 1), new WorldCoordinates(6, 10));
        //sklep 3.&4.
        addWallsInRealCoordX(new WorldCoordinates(27, 10), new WorldCoordinates(47, 10));
        addWallsInRealCoordY(new WorldCoordinates(27, 1), new WorldCoordinates(27, 10));
        addWallsInRealCoordY(new WorldCoordinates(37, 1), new WorldCoordinates(37, 10));
        //sklep 5. - przed regionem
        addWallsInRealCoordX(new WorldCoordinates(1, 19), new WorldCoordinates(47, 19));
        addWallsInRealCoordY(new WorldCoordinates(16, 20), new WorldCoordinates(16, 31));
        addWallsInRealCoordX(new WorldCoordinates(1, 24), new WorldCoordinates(15, 24));
        //sklep 6. - na koncu regionu 2.
        addWallsInRealCoordY(new WorldCoordinates(37, 20), new WorldCoordinates(37, 31));
        //fontanna
        addWallsInRealCoordX(new WorldCoordinates(25, 24), new WorldCoordinates(27, 24));
        addWallsInRealCoordX(new WorldCoordinates(25, 25), new WorldCoordinates(27, 25));
        addWallsInRealCoordX(new WorldCoordinates(25, 26), new WorldCoordinates(27, 26));
    }

    private void addDoorX(WorldCoordinates coordinates){
        cellMap.setCellToDoor(coordinates.getX(), coordinates.getY());
        cellMap.addDoor(new WorldCoordinates(coordinates.getX(), coordinates.getY()));
        cellMap.setCellToDoor(coordinates.getX()+1, coordinates.getY());
        cellMap.addDoor(new WorldCoordinates(coordinates.getX()+1, coordinates.getY()));
    }

    private void addDoorY(WorldCoordinates coordinates){
        cellMap.setCellToDoor(coordinates.getX(), coordinates.getY());
        cellMap.addDoor(new WorldCoordinates(coordinates.getX(), coordinates.getY()));
        cellMap.setCellToDoor(coordinates.getX(), coordinates.getY()+1);
        cellMap.addDoor(new WorldCoordinates(coordinates.getX(), coordinates.getY()+1));
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
        addDoorY(new WorldCoordinates(0, 14)); //wejscie lewe
        addDoorY(new WorldCoordinates(47, 14)); //wejscie prawe
        addDoorX(new WorldCoordinates(21, 0)); //wejscie gorne
    }
}
