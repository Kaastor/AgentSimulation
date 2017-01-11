package Visualisation;


import Agent.Agent;
import Environment.*;
import dissim.broker.IEvent;
import dissim.broker.IEventPublisher;
import dissim.broker.IEventSubscriber;
import dissim.simspace.core.SimModel;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lombok.Data;

import java.util.ArrayList;

@Data
public class EnvVisualisation extends Canvas implements IEventSubscriber{

    private final GraphicsContext graphicsContext = this.getGraphicsContext2D();
    private Map map;
    private Image agentImage;
    private int cellSize;

    public EnvVisualisation(Map map, int width, int height){
        super(width, height);
        this.map = map;
        this.cellSize = map.getCellSize();
        this.agentImage = new Image("file:resources/agentSmith.png");
        SimModel.getInstance().getCommonSimContext().getContextEventBroker().subscribe(Map.class, this);
    }

    public void drawMapOnScreen(){
        Cell[][] cellMap = map.getCellMap();
        ArrayList<Agent> agentArrayList = map.getAgents();

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
            }
        }
        graphicsContext.setFill(Color.BLACK);
        for(Agent agent : agentArrayList){
            drawAgent(agent.getPositionOnMap());
        }
    }

    private void drawAgent(GraphNode agentPosition){
        Point2D agentScreenPosition = conversionToScreenCoordinates(agentPosition);
        graphicsContext.drawImage(agentImage, agentScreenPosition.getX(), agentScreenPosition.getY(), cellSize, cellSize);
    }

    private Point2D conversionToScreenCoordinates(GraphNode coordinates){
        return new Point2D(coordinates.getX()*cellSize, coordinates.getY()*cellSize);
    }

    public void clear(){
        graphicsContext.clearRect(0, 0, getWidth(), getHeight());
    }


    @Override
    public void reflect(IEvent iEvent, IEventPublisher iEventPublisher) {  }

    @Override
    public void reflect(IEvent iEvent) { clear(); }
}
