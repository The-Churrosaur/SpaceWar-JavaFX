import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BulletObject extends GameObject {
    @Override
    Node setView() {
        Circle c = new Circle(1,1,2, Color.ORANGERED);
        return c;
    }

    // Game Logic ======================================================

    @Override
    public void onAdded() {
        super.onAdded();
        System.out.println("bullet spawned");
    }

    public void explode(){
        System.out.println("kaboom");
    }
}
