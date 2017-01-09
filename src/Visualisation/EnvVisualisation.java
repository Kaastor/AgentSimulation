package Visualisation;


import Environment.Cell;
import Environment.Map;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class EnvVisualisation extends Canvas{

    private final GraphicsContext graphicsContext = this.getGraphicsContext2D();
    private Map map; //CIRCULAR DEPENDENCY

    public EnvVisualisation(Map map, int width, int height){
        super(width, height);
        this.map = map;
        drawMapOnScreen();
    }

    private void drawMapOnScreen(){
        Cell[][] cellMap = map.getCellMap();
        for(int i =0 ; i < map.getMapWidth()/map.getCellSize() ; i++){
            for(int j = 0 ; j< map.getMapHeight()/map.getCellSize()  ; j++){
                Point2D tile = cellMap[i][j].getCoordinates();
                int cellSize = map.getCellSize();
                graphicsContext.strokeRect(tile.getX(), tile.getY(), cellSize, cellSize);
            }
        }
    }
}
