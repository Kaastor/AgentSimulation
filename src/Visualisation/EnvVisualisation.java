package Visualisation;


import Agent.Agent;
import Environment.*;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = false)
public class EnvVisualisation extends Canvas {

    private final GraphicsContext graphicsContext = this.getGraphicsContext2D();
    private Map map;
    private Image agentImage;
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
        this.agentArrayList = map.getAgentsList();
        this.mapWorldHeight = map.getMapWorldHeight();
        this.mapWorldWidth = map.getMapWorldWidth();
        this.mapScreenHeight = map.getMapScreenHeight();
        this.mapScreenWidth = map.getMapScreenWidth();
        this.agentImage = new Image("file:resources/agentSmith.png");
    }

    void drawMapOnScreen(){
        for(int x = 0; x < mapWorldWidth ; x++){
            for(int y = 0; y< mapWorldHeight  ; y++){

                CellType cellType = map.getCellType(x, y);
                Point2D screenCoordinates = map.getCellScreenCoordinates(x, y);

                if(cellType == CellType.FLOOR) {
                    graphicsContext.strokeRect(screenCoordinates.getX(), screenCoordinates.getY(), cellSize, cellSize);
                }
                if(cellType== CellType.WALL) {
                    graphicsContext.setFill(Color.BLACK);
                    graphicsContext.fillRect(screenCoordinates.getX(), screenCoordinates.getY(), cellSize, cellSize);
                }
                if(cellType == CellType.DOOR) {
                    graphicsContext.setFill(Color.GREENYELLOW);
                    graphicsContext.fillRect(screenCoordinates.getX(), screenCoordinates.getY(), cellSize, cellSize);
                }
            }
        }
        for(Agent agent : agentArrayList){
            if(agent.getPositionOnMap() != null)
                drawAgent(agent.getPositionOnMap().getWorldCoordinates());
        }
    }

    private void drawAgent(WorldCoordinates agentWorldCoordinates){
        Point2D agentScreenPosition = map.getCellScreenCoordinates(agentWorldCoordinates.getX(), agentWorldCoordinates.getY());
        graphicsContext.drawImage(agentImage, agentScreenPosition.getX(), agentScreenPosition.getY(), cellSize*0.95, cellSize*0.95);
    }

    void clearMapOnScreen(){
        graphicsContext.setFill(Color.WHITE);
        graphicsContext.fillRect(0, 0, mapScreenWidth, mapScreenHeight);
    }
}
