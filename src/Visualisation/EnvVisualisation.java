package Visualisation;


import Environment.Cell;
import Environment.CellType;
import Environment.Map;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lombok.Data;

@Data
public class EnvVisualisation extends Canvas{

    private final GraphicsContext graphicsContext = this.getGraphicsContext2D();
    private Map map;
    private Image agentImage;
    private int cellSize;


    public EnvVisualisation(Map map, int width, int height){
        super(width, height);
        this.map = map;
        this.cellSize = map.getCellSize();
        this.agentImage = new Image("file:resources/agentSmith.png");

        drawMapOnScreen();
    }

    public void drawMapOnScreen(){
        Cell[][] cellMap = map.getCellMap();

        for(int i =0 ; i < map.getMapWidth()/map.getCellSize() ; i++){
            for(int j = 0 ; j< map.getMapHeight()/map.getCellSize()  ; j++){
                Cell cell = cellMap[i][j];
                Point2D screenCoordinates = cellMap[i][j].getScreenCoordinates();

                if(cell.getCellType() == CellType.FLOOR) {
                    graphicsContext.strokeRect(screenCoordinates.getX(), screenCoordinates.getY(), cellSize, cellSize);
                }
                if(cell.getCellType() == CellType.WALL) {
                    graphicsContext.setFill(Color.BLACK);
                    graphicsContext.fillRect(screenCoordinates.getX(), screenCoordinates.getY(), cellSize, cellSize);
                }
                if(cell.getCellType() == CellType.DOOR) {
                    graphicsContext.setFill(Color.GREENYELLOW);
                    graphicsContext.fillRect(screenCoordinates.getX(), screenCoordinates.getY(), cellSize, cellSize);
                }
                if(cell.getCellType() == CellType.AGENT) {
                    graphicsContext.setFill(Color.BLACK);
                    drawAgent(screenCoordinates);
                }
            }
        }
    }

    private void drawAgent(Point2D agentPosition){
        graphicsContext.drawImage(agentImage, agentPosition.getX(), agentPosition.getY(), cellSize, cellSize);
    }
}
