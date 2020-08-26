import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class GunObject extends GameObject {

    @Override
    Node setView() {
        viewOffset = new Point2D(-1,-1);
        return new Rectangle(2,2);
    }

    // Game Logic ======================================================

    double muzzleVelocity = 5;
    int cooldown = 60; // frames

    private int cooldownTimer = 0;
    boolean cooldownActive = false;

    //GameObject projectile; // TODO class proto?


    @Override
    public void update() {
        super.update();

        if (cooldownActive){
            cooldownTimer++;
            if (cooldownTimer == cooldown){
                cooldownTimer = 0;
                cooldownActive = false;
            }
        }

    }

    public void fire(){
        GameObject projectile = new BulletObject();
        projectile.position = position;
        scene.addGameObject(projectile);

        projectile.velocity = velocity.add(forwards.multiply(muzzleVelocity));
    }

    public void fire(Point2D shooter, Point2D target){

        if(cooldownActive){
            return;
        }
        cooldownActive = true;

        GameObject projectile = new BulletObject();
        scene.addGameObject(projectile);
        projectile.position = shooter;

        Point2D vec = target.subtract(shooter); // shooter to target
        vec = vec.normalize().multiply(muzzleVelocity); // length of mv

        projectile.velocity = projectile.velocity.add(vec);


        // add projectiles to gravity influence of scene planet
        SpacewarScene s = (SpacewarScene)scene;
        s.planet.gravityTargets.add(projectile);
    }


}
