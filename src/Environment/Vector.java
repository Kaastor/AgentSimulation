package Environment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Vector extends WorldCoordinates{

    public Vector(int x, int y){
        super(x, y);
    }

    private void rotateRight(){
        int x = this.getY()*(-1);
        this.setY(this.getX());
        this.setX(x);
    }

    public void rotateForwardRight(){
        Vector rightVector = new Vector(this.getX(), getY());
        rightVector.rotateRight();
        vectorSum(rightVector);
        normalize();
    }

    public void rotateForwardLeft(){
        Vector leftVector = new Vector(this.getX(), getY());
        leftVector.rotateLeft();
        vectorSum(leftVector);
        normalize();
    }

    private void rotateLeft(){
        int x = this.getY();
        this.setY(this.getX()*(-1));
        this.setX(x);
    }

    private void rotateBack(){
        this.setX(this.getX()*(-1));
        this.setY(this.getY()*(-1));
    }
    public void rotateBackRight(){
        Vector rightVector = new Vector(this.getX(), getY());
        rightVector.rotateRight();
        this.rotateBack();
        vectorSum(rightVector);
        normalize();
    }

    public void rotateBackLeft(){
        Vector leftVector = new Vector(this.getX(), getY());
        leftVector.rotateLeft();
        this.rotateBack();
        vectorSum(leftVector);
        normalize();
    }

    private void vectorSum(Vector vector){
        this.setX(this.getX() + vector.getX());
        this.setY(this.getY() + vector.getY());
    }

    private void normalize(){
        double vectorLength = Math.sqrt(Math.pow(this.getX(), 2) + Math.pow(this.getY(), 2) );
        this.setX(getX()/(int)vectorLength);
        this.setY(getY()/(int)vectorLength);
    }

    public void update(GraphVertex position, GraphVertex nextPosition){
        this.setX(nextPosition.getWorldCoordinates().getX() - position.getWorldCoordinates().getX());
        this.setY(nextPosition.getWorldCoordinates().getY() - position.getWorldCoordinates().getY());
    }


}
