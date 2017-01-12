package Environment;


import lombok.Data;

import java.util.ArrayList;

@Data
public class GraphMap {

    private ArrayList<GraphNode> graphNodesList;
    private Cell[][] cellMap;

    public GraphMap(CellMap cellMap){
        this.cellMap = cellMap.getCells();
        this.graphNodesList = new ArrayList<>();
        initializeGraphMap(cellMap);
    }

    private void initializeGraphMap(CellMap cellMap){
        for(int i = 0; i < cellMap.getMapWorldWidth() ; i++) {
            for (int j = 0; j < cellMap.getMapWorldHeight(); j++) {
                if(isCellFloorOrDoorType(this.cellMap[i][j]))
                    graphNodesList.add(this.cellMap[i][j].getWorldCoordinates());
            }
        }
    }

    private boolean isCellFloorOrDoorType(Cell cell){
        return  (cell.getCellType() == CellType.FLOOR || cell.getCellType() == CellType.DOOR );
    }
}
