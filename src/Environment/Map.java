package Environment;


import Agent.Agent;
import javafx.geometry.Point2D;
import java.util.ArrayList;

public interface Map {

    GraphMap createAndGetGraphMap();
    ArrayList<CellType> getCellTypes(int worldX, int worldY);
    ArrayList<Agent> getAgentsList();
    ArrayList<WorldCoordinates> getDoorsList();
    ArrayList<WorldCoordinates> getEntrancesList();
    Point2D getCellScreenCoordinates(int worldX, int worldY);
    boolean cellExist(WorldCoordinates worldCoordinates);
    int getCellSize();
    int getMapScreenHeight();
    int getMapScreenWidth();
    int getMapWorldHeight();
    int getMapWorldWidth();
}
