package Environment;


import Agent.Agent;
import javafx.geometry.Point2D;
import java.util.ArrayList;

public interface Map {

    CellType getCellType(int worldX, int worldY);
    ArrayList<Agent> getAgentsList();
    Point2D getCellScreenCoordinates(int worldX, int worldY);
    boolean cellExist(WorldCoordinates worldCoordinates);
    int getCellSize();
    int getMapScreenHeight();
    int getMapScreenWidth();
    int getMapWorldHeight();
    int getMapWorldWidth();

}
