package Environment;


import Agent.Agent;
import javafx.geometry.Point2D;
import java.util.ArrayList;

public interface Map {

    CellType getCellType(int x, int y);
    ArrayList<Agent> getAgentsList();
    Point2D getCellScreenCoordinates(int x, int y);

    int getCellSize();
    int getMapScreenHeight();
    int getMapScreenWidth();
    int getMapWorldHeight();
    int getMapWorldWidth();


}
