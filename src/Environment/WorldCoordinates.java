package Environment;

import lombok.Data;



@Data
public class WorldCoordinates {

    private int x;
    private int y;

    public WorldCoordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public WorldCoordinates getForwardPointCoordinates(Vector vector){
        int x = this.x + vector.getX();
        int y = this.y + vector.getY();
        return new WorldCoordinates(x, y);
    }
}
