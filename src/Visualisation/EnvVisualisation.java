package Visualisation;


import Environment.Cell;
import Environment.CellType;
import Environment.GraphNode;
import Environment.Map;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class EnvVisualisation extends Canvas{

    private final GraphicsContext graphicsContext = this.getGraphicsContext2D();
    private Map map;

    public EnvVisualisation(Map map, int width, int height){
        super(width, height);
        this.map = map;
        drawMapOnScreen();
    }

    private void drawMapOnScreen(){
        Cell[][] cellMap = map.getCellMap();
        for(int i =0 ; i < map.getMapWidth()/map.getCellSize() ; i++){
            for(int j = 0 ; j< map.getMapHeight()/map.getCellSize()  ; j++){
                Cell cell = cellMap[i][j];
                GraphNode cellCoordinates = cellMap[i][j].getCoordinates();
                int cellSize = map.getCellSize();

                if(cell.getCellType() == CellType.FLOOR) {
                    graphicsContext.strokeRect(cellCoordinates.getX(), cellCoordinates.getY(), cellSize, cellSize);
                }
                if(cell.getCellType() == CellType.WALL) {
                    graphicsContext.setFill(Color.BLACK);
                    graphicsContext.fillRect(cellCoordinates.getX(), cellCoordinates.getY(), cellSize, cellSize);
                }
                if(cell.getCellType() == CellType.DOOR) {
                    graphicsContext.setFill(Color.GREENYELLOW);
                    graphicsContext.fillRect(cellCoordinates.getX(), cellCoordinates.getY(), cellSize, cellSize);
                }
            }
        }
    }
}
