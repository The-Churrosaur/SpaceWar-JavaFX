import javafx.geometry.Point2D;

public class GameHelpers {

    // Static helpers ===================================================

    static Point2D rotate(Point2D v, double angle) { // angle
        angle = Math.toRadians(angle);
        double x = v.getX() * Math.cos(angle) - v.getY() * Math.sin(angle);
        double y = v.getX() * Math.sin(angle) + v.getY() * Math.cos(angle);
        return new Point2D(x,y);
    }
}
