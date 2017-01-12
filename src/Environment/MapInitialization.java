package Environment;

import dissim.random.SimGenerator;
import Agent.Agent;

public class MapInitialization {

    private CellMap cellMap;
    private Cell[][] cells;
    private int maxAgentsNumber;

    public MapInitialization(int  mapWidth, int mapHeight, int cellSize, int maxAgentsNumber){
        this.cellMap = new CellMap(mapWidth, mapHeight, cellSize);
        this.cells = cellMap.getCells();
        this.maxAgentsNumber = maxAgentsNumber;
    }

    public Map initialize(){
        for(int i = 0; i < cellMap.getMapWorldWidth() ; i++){
            for(int j = 0; j < cellMap.getMapWorldHeight() ; j++){
                addFloors(i, j);
            }
        }
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
            if(cellMap.getCell(x, y).getCellType() == CellType.FLOOR){
                cellMap.addAgent(new Agent(cellMap, count, new GraphNode(x, y)));
                count++;
            }
        }
    }

    private void addFloors(int i, int j){
        cells[i][j] = new Cell(new GraphNode(i, j), cellMap.getCellSize(), CellType.FLOOR);
    }

    private void addWallsInRealCoordX(GraphNode from, GraphNode to){
        for(int i = from.getX() ; i <= to.getX() ; i++){
            cells[i][from.getY()].setCellType(CellType.WALL);
        }
    }

    private void addWallsInRealCoordY(GraphNode from, GraphNode to){
        for(int i = from.getY() ; i <= to.getY() ; i++){
            cells[from.getX()][i].setCellType(CellType.WALL);
        }
    }

    private void addWalls(){
        //ściany zewnetrzne
        addWallsInRealCoordX(new GraphNode(0, 0), new GraphNode(47, 0));
        addWallsInRealCoordX(new GraphNode(0, 31), new GraphNode(47, 31));
        addWallsInRealCoordY(new GraphNode(0, 0), new GraphNode(0, 31));
        addWallsInRealCoordY(new GraphNode(47, 0), new GraphNode(47, 31));
        //sklep 1.&2.
        addWallsInRealCoordX(new GraphNode(1, 10), new GraphNode(16, 10));
        addWallsInRealCoordY(new GraphNode(16, 1), new GraphNode(16, 10));
        addWallsInRealCoordY(new GraphNode(6, 1), new GraphNode(6, 10));
        //sklep 3.&4.
        addWallsInRealCoordX(new GraphNode(27, 10), new GraphNode(47, 10));
        addWallsInRealCoordY(new GraphNode(27, 1), new GraphNode(27, 10));
        addWallsInRealCoordY(new GraphNode(37, 1), new GraphNode(37, 10));
        //sklep 5. - przed regionem
        addWallsInRealCoordX(new GraphNode(1, 19), new GraphNode(47, 19));
        addWallsInRealCoordY(new GraphNode(16, 20), new GraphNode(16, 31));
        addWallsInRealCoordX(new GraphNode(1, 24), new GraphNode(15, 24));
        //sklep 6. - na koncu regionu 2.
        addWallsInRealCoordY(new GraphNode(37, 20), new GraphNode(37, 31));
        //fontanna
        addWallsInRealCoordX(new GraphNode(25, 24), new GraphNode(27, 24));
        addWallsInRealCoordX(new GraphNode(25, 25), new GraphNode(27, 25));
        addWallsInRealCoordX(new GraphNode(25, 26), new GraphNode(27, 26));
    }

    private void addDoorX(GraphNode coordinates){
        cells[coordinates.getX()][coordinates.getY()].setCellType(CellType.DOOR);
        cellMap.getDoorsList().add(new GraphNode(coordinates.getX(), coordinates.getY()));
        cells[coordinates.getX()+1][coordinates.getY()].setCellType(CellType.DOOR);
        cellMap.getDoorsList().add(new GraphNode(coordinates.getX()+1, coordinates.getY()));
    }

    private void addDoorY(GraphNode coordinates){
        cells[coordinates.getX()][coordinates.getY()].setCellType(CellType.DOOR);
        cellMap.getDoorsList().add(new GraphNode(coordinates.getX(), coordinates.getY()));
        cells[coordinates.getX()][coordinates.getY()+1].setCellType(CellType.DOOR);
        cellMap.getDoorsList().add(new GraphNode(coordinates.getX(), coordinates.getY()+1));
    }

    private void addDoors(){
        addDoorX(new GraphNode(2, 10)); //1.
        addDoorX(new GraphNode(10, 10)); //2.
        addDoorX(new GraphNode(41, 10)); //4.
        addDoorX(new GraphNode(6, 19)); //przed-region
        addDoorX(new GraphNode(1, 24)); //5.
        addDoorY(new GraphNode(27, 6)); //3.
        addDoorY(new GraphNode(16, 21)); //region
        addDoorY(new GraphNode(37, 24)); //6.
        addDoorY(new GraphNode(0, 14)); //wejscie lewe
        addDoorY(new GraphNode(47, 14)); //wejscie prawe
        addDoorX(new GraphNode(21, 0)); //wejscie gorne
    }
}
