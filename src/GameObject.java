import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class GameObject {

    GameObject(){
        view = setView();
    }

    // scene management

    Node view;
    Point2D viewOffset = new Point2D(0,0); // offset so that center is 0,0
    double viewRotationOffset = 0;

    GameScene scene;

    // physics

    Point2D position = new Point2D(0,0);
    Point2D velocity = new Point2D(0,0); // pixels/tick
    private double rotation = 0; // +x, use rotation helper methods
    Point2D forwards = new Point2D(1,0); // right

    // View ============================================================

    Node setView(){ // sets javafx object
        return new Circle(5,5,5);
    }

    // Game Logic ======================================================

    public void onAdded(){
        updateViewPosition();
    }

    public void update(){

        // update physics

        updateChildren();

        updatePositionFromVelocity();
        updateViewPosition();
        updateViewRotation();
        updateForwards(rotation);
    }

    public void onDestroyed(){

    }

    public void explode(){

    }

    // Control methods =================================================

    public void setPosition(double x, double y){
        position = new Point2D(x,y);
        updateViewPosition();
    }

    public void setVelocity(double x, double y){
        velocity = new Point2D(x,y);
    }

    public void rotate(double angle){
        rotateTo(rotation - angle);
    }

    public void rotateTo(double angle){
        rotation = angle;
        view.setRotate(angle + viewRotationOffset);

        //rotateOffsetChildren(angle);
    }

    // control helpers

    private void updateViewPosition(){
        view.setTranslateX(position.getX() + viewOffset.getX());
        view.setTranslateY(position.getY() + viewOffset.getY());
    }

    private void updateViewRotation(){
        view.setRotate(rotation + viewRotationOffset);
    }

    private void updatePositionFromVelocity(){
        position = position.add(velocity);
    }

    private void updateForwards(double angle){
        angle = Math.toRadians(angle);
        double y = Math.sin(angle);
        double x = Math.cos(angle);
        forwards = new Point2D(x,y);
    }

    // Parenting =========================================================

    // child objects are position and rotation locked to their parent
    ArrayList<GameObject> children = new ArrayList<>();
    GameObject parent;

    Point2D relativePosition;
    double relativeRotation;

    boolean isChild = false;

    public void setChild(GameObject g){
        children.add(g);
        g.parent = this;
        g.isChild = true;

        g.relativePosition = g.position.subtract(position); // parent to child
        g.relativeRotation = rotation;
    }

    private void updateChildren(){
        for (GameObject g: children) {
            double dRot = rotation - g.relativeRotation;
            // position + (offset rotated)
            Point2D offset = GameHelpers.rotate(g.relativePosition,dRot);
            g.position = position.add(offset);
            g.rotateTo(rotation);
        }
    }

    private void rotateOffsetChildren(double angle){
        for (GameObject g : children) {
            Point2D distance = g.position.subtract(position);
            System.out.println(distance);
            distance = GameHelpers.rotate(distance, angle);
            System.out.println(distance);
            g.position = distance;
        }
    }

    // Collision =========================================================

    public boolean isColliding(GameObject g){
        return view.getBoundsInParent().intersects(g.view.getBoundsInParent());
    }
}
