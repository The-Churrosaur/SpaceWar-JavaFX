import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BackgroundObject extends GameObject {
    @Override
    Node setView() {
        //double x = scene.rootPane.getPrefWidth();
        //double y = scene.rootPane.getPrefHeight();
        Rectangle bg = new Rectangle(100,100, Color.BLACK);
        return bg;
    }
}
