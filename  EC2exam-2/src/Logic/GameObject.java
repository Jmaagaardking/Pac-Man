package Logic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public interface GameObject {

    void update(KeyCode keyPressed);

    void draw(GraphicsContext graphicsContext, SceneInfo sceneInfo);
}
