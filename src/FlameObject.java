import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FlameObject extends GameObject {
    @Override
    Node setView() {
        Image image = new Image("assets/ThrustWash.png");
        ImageView sprite = new ImageView();
        sprite.setImage(image);

        sprite.setFitWidth(10);
        sprite.setFitHeight(20);

        viewRotationOffset = -90;
        viewOffset = new Point2D(-5,-10);

        return sprite;
    }

    public void toggle(boolean on){
        view.setVisible(on);
    }
}
