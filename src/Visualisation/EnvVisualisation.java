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
    private CellMap cellMap; //INTERFEJS DO MAPY TU
    private Image agentImage;
    private int cellSize;

    public EnvVisualisation(CellMap cellMap, int width, int height){
        super(width, height);
        this.cellMap = cellMap;
        this.cellSize = cellMap.getCellSize();
        this.agentImage = new Image("file:resources/agentSmith.png");
    }

    public void drawMapOnScreen(){
        Cell[][] cells = this.cellMap.getCellMap();
        ArrayList<Agent> agentArrayList = this.cellMap.getAgents();

        for(int i = 0; i < this.cellMap.getMapWidth()/ this.cellMap.getCellSize() ; i++){
            for(int j = 0; j< this.cellMap.getMapHeight()/ this.cellMap.getCellSize()  ; j++){
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
        graphicsContext.setFill(Color.BLACK);
        for(Agent agent : agentArrayList){
//            System.out.println(agentArrayList.get(0).getPositionOnMap());
            drawAgent(agent.getPositionOnMap());
        }
    }

    private void drawAgent(GraphNode agentPosition){
        Point2D agentScreenPosition = cellMap.conversionToScreenCoordinates(agentPosition);
        graphicsContext.drawImage(agentImage, agentScreenPosition.getX(), agentScreenPosition.getY(), cellSize*0.95, cellSize*0.95);
    }


    public void clear(){
        graphicsContext.clearRect(0, 0, getWidth(), getHeight());
    }

}
