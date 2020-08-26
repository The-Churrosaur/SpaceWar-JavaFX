import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.ArrayList;


public class SpacewarScene extends GameScene {

    SpacewarScene(Pane p){
        super(p);
    }

    // Game Logic ======================================================

    SpaceshipObject ship = new SpaceshipObject();
    SpaceshipObject ship2 = new SpaceshipObject();
    ArrayList<SpaceshipObject> ships = new ArrayList<>();

    PlanetObject planet = new PlanetObject();

    @Override
    public void onAdded() {
        super.onAdded();

        // background

        double x = rootPane.getPrefWidth();
        double y = rootPane.getPrefHeight();

        BackgroundObject background = new BackgroundObject();
        background.view = new Rectangle(x,y, Color.BLACK);
        background.viewOffset = new Point2D(-(x/2),-(y/2));
        addGameObject(background);
        background.position = new Point2D(x/2,y/2);

        // ships

        addGameObject(ship);
        ship.setPosition(350,350);
        ship.setVelocity(0,-1);
        ship.rotate(90);

        addGameObject(ship2);
        ship2.setPosition(250,250);
        ship2.setVelocity(0,1);
        ship2.rotate(-90);

        //ships.add(ship);
        //ships.add(ship2);

        // planet

        addGameObject(planet);
        planet.setPosition(300,300);
        planet.gravityTargets.add(ship);
        planet.gravityTargets.add(ship2);
    }

    @Override
    public void update() {
        super.update();

        for (GameObject g : gameObjects) {

            Class gClass = g.getClass();

            // bullets/ships collide with planet

            if (gClass.equals(BulletObject.class) || gClass.equals(SpaceshipObject.class)){
                if (g.isColliding(planet)){
                    g.explode();
                    queueRemoveGameObject(g);
                }
            }

            // bullets explode ships

            if (gClass.equals(BulletObject.class)){
                if (ship.isAlive && g.isColliding(ship)){
                    ship.explode();
                    queueRemoveGameObject(ship);
                    ship.isAlive = false;
                }
                if (ship2.isAlive && g.isColliding(ship2)){
                    ship2.explode();
                    queueRemoveGameObject(ship2);
                    ship2.isAlive = false;
                }
            }

        }

    }

    // Input ===========================================================

    @Override
    protected void setupInputs(){

        // player 1

        class InputP1W extends ActionInput{

            @Override
            void setKey() {
                keyCode = KeyCode.W;
            }

            @Override
            void action() {
                ship.thrust();
            }
        }
        addInput(new InputP1W());

        class InputP1A extends ActionInput{

            @Override
            void setKey() {
                keyCode = KeyCode.A;
            }

            @Override
            void action() {
                ship.rotate(2);
            }
        }
        addInput(new InputP1A());

        class InputP1D extends ActionInput{

            @Override
            void setKey() {
                keyCode = KeyCode.D;
            }

            @Override
            void action() {
                ship.rotate(-2);
            }
        }
        addInput(new InputP1D());

        class InputP1Space extends ActionInput{

            @Override
            void setKey() {
                keyCode = KeyCode.SPACE;
            }

            @Override
            void action() {
                ship.fire();
            }
        }
        addInput(new InputP1Space());

        // Player 2

        class InputP2Up extends ActionInput{

            @Override
            void setKey() {
                keyCode = KeyCode.UP;
            }

            @Override
            void action() {
                ship2.thrust();
            }
        }
        addInput(new InputP2Up());

        class InputP2Left extends ActionInput{

            @Override
            void setKey() {
                keyCode = KeyCode.LEFT;
            }

            @Override
            void action() {
                ship2.rotate(2);
            }
        }
        addInput(new InputP2Left());

        class InputP2Right extends ActionInput{

            @Override
            void setKey() {
                keyCode = KeyCode.RIGHT;
            }

            @Override
            void action() {
                ship2.rotate(-2);
            }
        }
        addInput(new InputP2Right());

        class InputP2Enter extends ActionInput{

            @Override
            void setKey() {
                keyCode = KeyCode.ENTER;
            }

            @Override
            void action() {
                ship2.fire();
            }
        }
        addInput(new InputP2Enter());

    }

}
