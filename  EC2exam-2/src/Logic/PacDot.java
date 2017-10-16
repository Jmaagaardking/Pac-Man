package Logic;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.awt.*;

public class PacDot implements GameObject{

    public Point position;
    private Boolean edible;

    public PacDot(Point position, Boolean edbile){
        this.position = position;
        this.setEaten(edbile);
    }

    public void drawCenteredCircle(GraphicsContext g, double x, double y, double w, double h) {
        x = x + (w/2);
        y = y + (h/2);
        g.fillOval(x,y,w,h);
    }


    @Override
    public void update(KeyCode keyPressed) {
    }

    @Override
    public void draw(GraphicsContext graphicsContext, SceneInfo sceneInfo) {
        if (getEdible()){
            graphicsContext.setFill(Color.WHITE);
            drawCenteredCircle(graphicsContext, position.x * sceneInfo.getFieldWidth()+5.7, position.y * sceneInfo.getFieldHeight()+5.7, sceneInfo.getFieldWidth()/4, sceneInfo.getFieldHeight()/4);
        }
    }


    public Boolean getEdible() {
        return edible;
    }
    public void setEaten(Boolean eaten) {
        edible = eaten;
    }
}
