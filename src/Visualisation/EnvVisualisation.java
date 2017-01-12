package Visualisation;


import Agent.Agent;
import Environment.*;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lombok.Data;

import java.util.ArrayList;

@Data
public class EnvVisualisation extends Canvas {

    private final GraphicsContext graphicsContext = this.getGraphicsContext2D();
    private Map map;
    private Image agentImage;
    private Cell[][] cells;
    private int cellSize;
    private int mapScreenHeight;
    private int mapScreenWidth;
    private int mapWorldHeight;
    private int mapWorldWidth;
    private ArrayList<Agent> agentArrayList;

    public EnvVisualisation(Map map, int width, int height){
        super(width, height);
        this.map = map;
        this.cellSize = map.getCellSize();
        this.cells = map.getCells();
        this.agentArrayList = map.getAgentsList();
        this.mapWorldHeight = map.getMapWorldHeight();
        this.mapWorldWidth = map.getMapWorldWidth();
        this.mapScreenHeight = map.getMapScreenHeight();
        this.mapScreenWidth = map.getMapScreenWidth();
        this.agentImage = new Image("file:resources/agentSmith.png");
    }

    public void drawMapOnScreen(){
        for(int i = 0; i < mapWorldWidth ; i++){
            for(int j = 0; j< mapWorldHeight  ; j++){
                Cell cell = cells[i][j];
                Point2D screenCoordinates = cells[i][j].getScreenCoordinates();

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
            }
        }
        for(Agent agent : agentArrayList){
            drawAgent(agent.getPositionOnMap());
        }
    }

    private void drawAgent(GraphNode agentPosition){
        Point2D agentScreenPosition = map.conversionToScreenCoordinates(agentPosition);
        graphicsContext.drawImage(agentImage, agentScreenPosition.getX(), agentScreenPosition.getY(), cellSize*0.95, cellSize*0.95);
    }

    public void clearMapOnScreen(){
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, mapScreenWidth, mapScreenHeight);
    }
}
