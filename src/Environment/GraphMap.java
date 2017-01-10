package Environment;


import lombok.Data;

import java.util.ArrayList;

@Data
public class GraphMap {

    private ArrayList<GraphNode> graphNodesList;
    private Cell[][] cellMap;

    public GraphMap(Map map){
        this.cellMap = map.getCellMap();
        this.graphNodesList = new ArrayList<>();
        initializeGraphMap(map);
    }

    private void initializeGraphMap(Map map){
        for(int i =0 ; i < map.getMapWorldWidth() ; i++) {
            for (int j = 0; j < map.getMapWorldHeight(); j++) {
                if(isCellFloorOrDoorType(cellMap[i][j]))
                    graphNodesList.add(cellMap[i][j].getWorldCoordinates());
            }
        }
    }

    private boolean isCellFloorOrDoorType(Cell cell){
        return  (cell.getCellType() == CellType.FLOOR || cell.getCellType() == CellType.DOOR );
    }
}
