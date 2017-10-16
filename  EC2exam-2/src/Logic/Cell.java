package Logic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.awt.*;

public class Cell implements GameObject{

    private Point point;
    private boolean isPath;

    //    -------------------------------------------------------

    public Cell(Point position, boolean pathable){
        this.point = position;
        this.setPath(pathable);
    }

    @Override
    public void update(KeyCode keyPressed) {
    }

    @Override
    public void draw(GraphicsContext graphicsContext, SceneInfo sceneInfo) {
        if (isPath()){
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillRoundRect(point.x * sceneInfo.getFieldWidth(), point.y * sceneInfo.getFieldHeight(), sceneInfo.getFieldWidth(), sceneInfo.getFieldHeight(), 0, 0);
        } else {
            graphicsContext.setFill(Color.BLUE);
            graphicsContext.fillRoundRect(point.x * sceneInfo.getFieldWidth(), point.y * sceneInfo.getFieldHeight(), sceneInfo.getFieldWidth(), sceneInfo.getFieldHeight(), 0, 0);

        }

    }

    public boolean isPath() {
        return isPath;
    }
    public void setPath(boolean path) {
        isPath = path;
    }

}
