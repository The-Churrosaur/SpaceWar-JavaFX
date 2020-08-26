import javafx.scene.input.KeyCode;

public abstract class ActionInput {

    ActionInput(){
        setKey();
    }

    abstract void setKey();

    boolean isHeld = false;
    KeyCode keyCode;

    abstract void action();

    public void doAction(){
        if (isHeld) action();
    }

}
