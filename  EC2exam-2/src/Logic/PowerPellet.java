package Logic;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.awt.*;

public class PowerPellet implements GameObject{

    public Point position;
    private Boolean edible;

    public PowerPellet(Point position, Boolean edible){

        this.position = position;
        this.setEdible(edible);
    }

    // sætte super pillen i midten
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
            graphicsContext.setFill(javafx.scene.paint.Color.WHITE);
            drawCenteredCircle(graphicsContext, position.x * sceneInfo.getFieldWidth()-1.7, position.y * sceneInfo.getFieldHeight()-1.7, sceneInfo.getFieldWidth()/1.6, sceneInfo.getFieldHeight()/1.6);
        }
    }

    public Boolean getEdible() {
        return edible;
    }
    public void setEdible(Boolean edible) {
        this.edible = edible;
    }
}
