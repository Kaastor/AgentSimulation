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
}
