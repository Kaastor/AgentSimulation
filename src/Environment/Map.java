package Environment;


import Agent.Agent;
import javafx.geometry.Point2D;
import java.util.ArrayList;

public interface Map {

    Cell[][] getCells();
    Point2D conversionToScreenCoordinates(GraphNode graphNode);
    int getCellSize();
    int getMapScreenHeight();
    int getMapScreenWidth();
    int getMapWorldHeight();
    int getMapWorldWidth();
    ArrayList<Agent> getAgentsList();

}
