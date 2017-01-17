package Environment;


import Agent.Agent;
import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;

public interface Map {

    GraphMap createAndGetGraphMap();
    List<CellType> getCellTypes(int worldX, int worldY);
    List<Agent> getAgentsList();
    List<WorldCoordinates> getDoorsList();
    List<WorldCoordinates> getEntrancesList();
    Point2D getCellScreenCoordinates(int worldX, int worldY);
    boolean cellExist(WorldCoordinates worldCoordinates);
    int getCellSize();
    int getMapScreenHeight();
    int getMapScreenWidth();
    int getMapWorldHeight();
    int getMapWorldWidth();
}
