import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class GameScene extends Scene{

    Pane rootPane;

    // Constructors =====================================================

    GameScene(Pane p){
        super(p);
        if (p.equals(getRoot())) { // just in case
            rootPane = p;
        }
        else System.out.println("Scene: root not set as parent");
    }

    GameScene(ApplicationBase base){
        super(base.newRootPane());
    }

    // Input ===========================================================

    private void registerKeyEvents(){
        EventHandler<KeyEvent> keyDownHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleKeysDown(event);
            }
        };
        addEventHandler(KeyEvent.KEY_PRESSED, keyDownHandler);

        EventHandler<KeyEvent> keyUpHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                handleKeysUp(event);
            }
        };
        addEventHandler(KeyEvent.KEY_RELEASED, keyUpHandler);
    }

    ArrayList<ActionInput> inputs = new ArrayList<>();

    protected void addInput(ActionInput a){
        inputs.add(a);
    }

    protected void setupInputs(){ // Override this to setup inputs

        // to add a new input:
        // input class extends action
            // override setkey()
            // override action()
        // inputs.add(new input class)

        // TODO can this be made better?

    }

    private void handleKeysDown(KeyEvent event){
        for (ActionInput input : inputs) {
            if (input.keyCode == event.getCode()){
                input.isHeld = true;
            }
        }
    }

    private void handleKeysUp (KeyEvent event){
        for (ActionInput input : inputs) {
            if (input.keyCode == event.getCode()){
                input.isHeld = false;
            }
        }
    }

    protected void handleKeyActions(){
        for (ActionInput input : inputs) {
            input.doAction();
        }
    }

    // Game Logic ======================================================

    public void onAdded(){
        setupInputs();
        registerKeyEvents();
    }

    public void update(){
        handleKeyActions();
        for (GameObject g : gameObjects) {
            g.update();
        }
        addQueuedGOs();
        deleteQueuedGOs();
    }

    // Game Objects =====================================================

    ArrayList<GameObject> gameObjects = new ArrayList<>();
    private ArrayList<GameObject> gameObjectsAddQueue = new ArrayList<>();

    public void queueAddGameObject(GameObject go){
        gameObjectsAddQueue.add(go);
    }

    private void addQueuedGOs(){
        for (GameObject g : gameObjectsAddQueue) {
            addGameObject(g);
        }
        gameObjectsAddQueue.clear();
    }

    public void addGameObject(GameObject go){
        go.scene = this;
        gameObjects.add(go);
        rootPane.getChildren().add(go.view);
        go.onAdded();
    }

    private ArrayList<GameObject> gameObjectRemoveQueue = new ArrayList<>();

    public void queueRemoveGameObject(GameObject go){
        System.out.println("queueing for deletion");
        gameObjectRemoveQueue.add(go);
    }

    private void deleteQueuedGOs(){
        for (GameObject g : gameObjectRemoveQueue) {
            removeGameObject(g);
        }
        gameObjectRemoveQueue.clear();
    }

    private void removeGameObject (GameObject go){

        // delete children

        for (GameObject child : go.children) {
            removeGameObject(child);
        }

        System.out.println("deleting: " + go);
        go.onDestroyed();
        gameObjects.remove(go);
        rootPane.getChildren().remove(go.view);
    }


}
