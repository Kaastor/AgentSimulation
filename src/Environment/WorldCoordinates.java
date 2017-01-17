package Environment;

import lombok.Data;

import java.awt.*;


@Data
public class WorldCoordinates {

    private int x;
    private int y;

    public WorldCoordinates(){}

    public WorldCoordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public WorldCoordinates getForwardPointCoordinates(Point vector){
        int x = this.x + (int)vector.getX();
        int y = this.y + (int)vector.getY();
        return new WorldCoordinates(x, y);
    }
    public WorldCoordinates getRightPointCoordinates(Point vector){
        return getForwardPointCoordinates(rotateVectorRight(vector));
    }
    public WorldCoordinates getLeftPointCoordinates(Point vector){
        return getForwardPointCoordinates(rotateVectorLeft(vector));
    }
    public WorldCoordinates getBackPointCoordinates(Point vector){
        int x = this.x - (int)vector.getX();
        int y = this.y - (int)vector.getY();
        return new WorldCoordinates(x, y);
    }

    private Point rotateVectorRight(Point vector){
        vector.setLocation(vector.getY()*(-1),
                vector.getX());
        return vector;
    }

    private Point rotateVectorLeft(Point vector){
        vector.setLocation(vector.getY(),
                vector.getX()*(-1));
        return vector;
    }
}
