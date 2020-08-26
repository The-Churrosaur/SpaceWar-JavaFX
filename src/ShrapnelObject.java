import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.Random;

public class ShrapnelObject extends GameObject {

    double maxSpeed = 0.5;

    @Override
    Node setView() {
        double[] points = {
                0,-6,
                3,0,
                6,-6
        };
        Polygon p = new Polygon(points);
        p.setFill(Color.GRAY);
        viewOffset = new Point2D(3,3);
        return p;
    }

    @Override
    public void onAdded() {
        super.onAdded();

        Random rand = new Random();

        // random rotation

        double skew = rand.nextDouble() * 360;
        System.out.println(skew);
        rotate(skew);

        // random velocity vector

        double angle = rand.nextDouble() * 360;
        System.out.println(angle);
        double speed = rand.nextDouble() * maxSpeed;
        Point2D vel = new Point2D(speed,0);
        velocity = GameHelpers.rotate(vel,angle);
    }
}
