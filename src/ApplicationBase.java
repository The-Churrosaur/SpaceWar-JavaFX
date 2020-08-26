import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ApplicationBase extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        stage = primaryStage;
        primaryStage.show();
        startUpdate();

        GameScene mainScene = new SpacewarScene(newRootPane());
        startScene(mainScene);

    }

    private Pane currentRoot;
    private GameScene currentScene;

    private Stage stage;

    public Pane newRootPane(){
        Pane rootPane = new Pane();
        rootPane.setPrefSize(600,600);
        return rootPane;
    }

    // GameScenes ===========================================================

    private void startScene(GameScene scene){

        currentScene = scene;
        currentRoot = scene.rootPane;
        stage.setScene(scene);

        scene.onAdded();
    }

    // Update manager =======================================================

    AnimationTimer updateTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            update();
        }
    };

    private void startUpdate(){
        updateTimer.start();
    }

    private void update(){
        currentScene.update();
    }


}
