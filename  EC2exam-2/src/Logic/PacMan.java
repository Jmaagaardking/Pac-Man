package Logic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.awt.*;

public class PacMan implements GameObject {

    public Point position;
    private int drawPacMouth;
    public int pacPoints;

    private SceneInfo sceneInfo;


//    -------------------------------------------------------

    public PacMan(Point position, SceneInfo sceneInfo) {
        this.position = position;
        this.sceneInfo = sceneInfo;
    }


    //    Tjek efter om næste felt er en wall
    private boolean wallCollision() {
        Cell pathableCell = sceneInfo.cells[position.x][position.y];
        if (pathableCell.isPath()) {
            // ingen wall
            return false;
        }
        // hit a wall
        return true;
    }

    //    Tjek efter om næste felt er en PacDot
    private boolean dotCollision() {
        PacDot edibleDot = sceneInfo.dots[position.x][position.y];
        if (!edibleDot.getEdible()) {
            // no pac-dot here
            return false;
        }
        // edible pac-dot available
        return true;
    }

    //    Tjek efter om næste felt er en PowerPellet
    public boolean powerPickUp(){
        for (PowerPellet powerPellet : sceneInfo.powerPellets) {
            if (powerPellet.position.x == this.position.x && powerPellet.position.y == this.position.y) {
                sceneInfo.powerPellets.remove(powerPellet);
                return true;
            }
        }
        return false;
    }


    //    tenge pacman
    private void goingUp(GraphicsContext gc){
        // UP
        gc.fillArc(position.x * sceneInfo.getFieldWidth(), position.y * sceneInfo.getFieldHeight(), sceneInfo.getFieldWidth(), sceneInfo.getFieldHeight(), 135, 270, ArcType.ROUND);
    }
    private void goingDown(GraphicsContext gc) {
        // DOWN
        gc.fillArc(position.x * sceneInfo.getFieldWidth(), position.y * sceneInfo.getFieldHeight(), sceneInfo.getFieldWidth(), sceneInfo.getFieldHeight(), 315, 270, ArcType.ROUND);

    }
    private void goingLeft(GraphicsContext gc){
        // LEFT
        gc.fillArc(position.x * sceneInfo.getFieldWidth(), position.y * sceneInfo.getFieldHeight(), sceneInfo.getFieldWidth(), sceneInfo.getFieldHeight(), 230, 270, ArcType.ROUND);

    }
    private void goingRight(GraphicsContext gc){
        // RIGHT
        gc.fillArc(position.x * sceneInfo.getFieldWidth(), position.y * sceneInfo.getFieldHeight(), sceneInfo.getFieldWidth(), sceneInfo.getFieldHeight(), 45, 270, ArcType.ROUND);
    }
    private void goingNowhere(GraphicsContext gc){
        gc.fillOval(position.x * sceneInfo.getFieldWidth(), position.y * sceneInfo.getFieldHeight(), sceneInfo.getFieldWidth(), sceneInfo.getFieldHeight());

    }


    @Override
    public void update(KeyCode keyPressed) {

        switch (keyPressed)
        {
            case DOWN:
                this.setY(this.getY() + 1);
                if (wallCollision()) {
                    this.setY(this.getY() - 1);
                }
                if (dotCollision()){
                    sceneInfo.dots[this.getX()][this.getY()].setEaten(false);
                    pacPoints = pacPoints +10;
                }
                setDrawPacMouth(1);
                    break;
            case LEFT:
                this.setX(this.getX() - 1);
                if (wallCollision()) {
                    this.setX(this.getX() + 1);
                }
                if (dotCollision()){
                    sceneInfo.dots[this.getX()][this.getY()].setEaten(false);
                    pacPoints = pacPoints +10;
                }
                setDrawPacMouth(2);
                    break;
            case RIGHT:
                this.setX(this.getX() + 1);
                if (wallCollision()) {
                    this.setX(this.getX() - 1);
                }
                if (dotCollision()){
                    sceneInfo.dots[this.getX()][this.getY()].setEaten(false);
                    pacPoints = pacPoints +10;
                }
                setDrawPacMouth(3);
                    break;
            case UP:
                this.setY(this.getY() - 1);
                if (wallCollision()) {
                    this.setY(this.getY() + 1);
                }
                if (dotCollision()){
                    sceneInfo.dots[this.getX()][this.getY()].setEaten(false);
                    pacPoints = pacPoints +10;
                }
                setDrawPacMouth(4);
                    break;
        }
    }

    @Override
    public void draw(GraphicsContext graphicsContext, SceneInfo sceneInfo) {
        graphicsContext.setFill(Color.YELLOW);

        switch (drawPacMouth){
            case 0:
                    goingNowhere(graphicsContext);
                    break;
            case 1:
                    goingDown(graphicsContext);
                    break;
            case 2:
                    goingLeft(graphicsContext);
                    break;
            case 3:
                    goingRight(graphicsContext);
                    break;
            case 4:
                    goingUp(graphicsContext);
                    break;
        }
    }


    public int getX() {
        return position.x;
    }
    public void setX(int x) {
        this.position.x = x;
    }


    public int getY() {
        return position.y;
    }
    public void setY(int y) {
        this.position.y = y;
    }

    public int getDrawPacMouth() {
        return drawPacMouth;
    }
    public void setDrawPacMouth(int drawPacMouth) {
        this.drawPacMouth = drawPacMouth;
    }
}
