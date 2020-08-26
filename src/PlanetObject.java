import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class PlanetObject extends GameObject {

    @Override
    Node setView() {
        Circle circle = new Circle(5,5,5, Color.WHITE);
        viewOffset = new Point2D(-5,-5);
        return circle;
    }

    ArrayList<GameObject> gravityTargets = new ArrayList<>();

    public double mu = 50;

    @Override
    public void onAdded() {
        super.onAdded();
        System.out.println("Added");
    }

    @Override
    public void update() {
        super.update();
        for (GameObject g : gravityTargets) {

            // applies acceleration towards body: mu/r^2

            double distance = g.position.distance(position);
            double dv = mu / (distance * distance); // no distance^2 method :(

            Point2D dvec = position.subtract(g.position).normalize().multiply(dv);

            g.velocity = g.velocity.add(dvec);

        }
    }
}
