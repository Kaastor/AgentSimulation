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
    private ArrayList<Agent> agentsArrayList;
    private ArrayList<WorldCoordinates> doorsArrayList;
    private ArrayList<WorldCoordinates> entrancesArrayList;

    public EnvVisualisation(Map map, int width, int height){
        super(width, height);
        this.map = map;
        this.cellSize = map.getCellSize();
        this.agentsArrayList = map.getAgentsList();
        this.doorsArrayList = map.getDoorsList();
        this.entrancesArrayList = map.getEntrancesList();
        this.mapWorldHeight = map.getMapWorldHeight();
        this.mapWorldWidth = map.getMapWorldWidth();
        this.mapScreenHeight = map.getMapScreenHeight();
        this.mapScreenWidth = map.getMapScreenWidth();
        this.agentImage = new Image("file:resources/agentSmith.png");
        drawMapOnScreen();
    }

    private void drawMapOnScreen(){
        for(int x = 0; x < mapWorldWidth ; x++){
            for(int y = 0; y< mapWorldHeight  ; y++){

                CellType cellType = map.getCellType(x, y);
                Point2D screenCoordinates = map.getCellScreenCoordinates(x, y);

                if(cellType == CellType.FLOOR) {
                    graphicsContext.setFill(Color.WHITE);
                    graphicsContext.fillRect(screenCoordinates.getX(), screenCoordinates.getY(), cellSize, cellSize);
                }
                if(cellType== CellType.WALL) {
                    graphicsContext.setFill(Color.BLACK);
                    graphicsContext.fillRect(screenCoordinates.getX(), screenCoordinates.getY(), cellSize, cellSize);
                }
                if(cellType == CellType.DOOR) {
                    graphicsContext.setFill(Color.GREENYELLOW);
                    graphicsContext.fillRect(screenCoordinates.getX(), screenCoordinates.getY(), cellSize, cellSize);
                }
                if(cellType == CellType.ENTRANCE) {
                    graphicsContext.setFill(Color.ORANGERED);
                    graphicsContext.fillRect(screenCoordinates.getX(), screenCoordinates.getY(), cellSize, cellSize);
                }
            }
        }
    }

    void renderMap(){
        clearAgentsOnScreen();
        drawDoorsOnScreen();
        drawEntrancesOnScreen();
        drawAgentsOnScreen();
    }

    private void drawAgentsOnScreen(){
        for(Agent agent : agentsArrayList){
            if(agent.getPosition() != null)
                drawAgent(agent.getPosition().getWorldCoordinates());
        }
    }

    private void drawAgent(WorldCoordinates agentWorldCoordinates){
        Point2D agentScreenPosition = map.getCellScreenCoordinates(agentWorldCoordinates.getX(), agentWorldCoordinates.getY());
        graphicsContext.drawImage(agentImage, agentScreenPosition.getX(), agentScreenPosition.getY(), cellSize*0.95, cellSize*0.95);
    }

    private void drawDoorsOnScreen(){
        for(WorldCoordinates door : doorsArrayList){
            Point2D screenCoordinates = map.getCellScreenCoordinates(door.getX(), door.getY());
            graphicsContext.setFill(Color.GREENYELLOW);
            graphicsContext.fillRect(screenCoordinates.getX(), screenCoordinates.getY(), cellSize, cellSize);
        }
    }

    private void drawEntrancesOnScreen(){
        for(WorldCoordinates entrance : entrancesArrayList){
            Point2D screenCoordinates = map.getCellScreenCoordinates(entrance.getX(), entrance.getY());
            graphicsContext.setFill(Color.ORANGERED);
            graphicsContext.fillRect(screenCoordinates.getX(), screenCoordinates.getY(), cellSize, cellSize);
        }
    }

    private void clearAgentsOnScreen(){
        for(Agent agent : agentsArrayList){
            if(agent.getPreviousPosition() != null) {
                Point2D agentScreenPosition = map.getCellScreenCoordinates(agent.getPreviousPosition().getWorldCoordinates().getX(),
                        agent.getPreviousPosition().getWorldCoordinates().getY());
                graphicsContext.setFill(Color.WHITE);
                graphicsContext.fillRect(agentScreenPosition.getX(),
                        agentScreenPosition.getY(), cellSize, cellSize);

            }
        }
    }
}
