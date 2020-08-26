import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class TestGO extends GameObject {

    @Override
    Node setView() {
        return new Circle(5,5,5,Color.BLUE);
    }

    @Override
    public void update() {
        super.update();
        velocity = velocity.add(5,5);
        position = position.add(5,5);
        System.out.println(position);
    }
}
