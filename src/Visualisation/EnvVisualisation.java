package Visualisation;


import Environment.Cell;
import Environment.CellType;
import Environment.GraphNode;
import Environment.Map;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class EnvVisualisation extends Canvas{

    private final GraphicsContext graphicsContext = this.getGraphicsContext2D();
    private Map map;
    private Image agentImage;
    private int cellSize;

    public EnvVisualisation(Map map, int width, int height){
        super(width, height);
        this.map = map;
        this.cellSize = map.getCellSize();
        this.agentImage = new Image("file:resources/agent_smith.gif");
        drawMapOnScreen();
    }

    private void drawMapOnScreen(){
        Cell[][] cellMap = map.getCellMap();

        for(int i =0 ; i < map.getMapWidth()/map.getCellSize() ; i++){
            for(int j = 0 ; j< map.getMapHeight()/map.getCellSize()  ; j++){
                Cell cell = cellMap[i][j];
                GraphNode cellCoordinates = cellMap[i][j].getCoordinates();

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
                if(cell.getCellType() == CellType.AGENT) {
                    drawAgent(cellCoordinates, cellSize);
                }
            }
        }
    }

    private void drawAgent(GraphNode agentPosition, int imageSize){
        graphicsContext.drawImage(agentImage, agentPosition.getX(), agentPosition.getY(), imageSize, imageSize);
    }
}
