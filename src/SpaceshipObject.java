import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class SpaceshipObject extends GameObject {

    @Override
    Node setView() {

        /* vertical arrow
        Polygon p = new Polygon(new double[]{
                0.0,20.0,
                5.0,0.0,
                10.0,20.0,
                5.0,17.0
        });

        p.setFill(Color.BLACK);
        return p;
         */

        Image image = new Image("assets/Rocket.png");
        ImageView sprite = new ImageView();
        sprite.setImage(image);

        sprite.setFitWidth(10);
        sprite.setFitHeight(40);

        viewRotationOffset = 90;
        viewOffset = new Point2D(-5,-20);

        return sprite;
    }

    // Game Logic ======================================================

    public float accel = 0.005f; // pixels/s^2
    boolean isAlive = true;

    GunObject gun = new GunObject();
    FlameObject flame = new FlameObject();

    @Override
    public void onAdded() {
        super.onAdded();

        // gun

        scene.addGameObject(gun);
        setChild(gun); // for cleanup

        // thrust wash

        scene.addGameObject(flame);
        flame.position = position.add(new Point2D(-30,0));
        setChild(flame);
    }

    boolean isThrusting = false;

    @Override
    public void update() {
        super.update();

        if(isThrusting)
            flame.toggle(true);
        else
            flame.toggle(false);

        isThrusting = false;
    }

    public void thrust(){
        System.out.println("thrusting");
        Point2D dv = forwards.multiply(accel);
        velocity = velocity.add(dv);

        isThrusting = true;
    }

    public void fire(){
        Point2D muzzle = position.add(forwards.multiply(25)); // in front of ship
        Point2D target = position.add(forwards.multiply(50)); // double
        gun.fire(muzzle,target);
    }

    public void explode(){
        System.out.println("kaboom!");

        for(int i = 0; i < 20; i++){
            ShrapnelObject s = new ShrapnelObject();
            scene.queueAddGameObject(s);
            s.position = position;
            s.velocity = s.velocity.add(velocity);
        }

    }

}
