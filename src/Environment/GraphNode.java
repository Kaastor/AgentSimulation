package Environment;

import lombok.Data;

@Data
public class GraphNode {

    private int x;
    private int y;

    public GraphNode(int x, int y){
        this.x = x;
        this.y = y;
    }
}
