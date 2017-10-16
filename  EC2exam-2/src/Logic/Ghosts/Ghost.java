package Logic.Ghosts;

import Logic.Cell;
import Logic.GameObject;
import Logic.PacMan;
import Logic.SceneInfo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public abstract class Ghost implements GameObject{

    public enum GhostMode {chasing, random, flee}  //enum Ghostmode -- gruppe af constians --istedet for final
    public int fieldsSearched = 0;
    public Random random = new Random();
    public Point position;
    public GhostMode ghostMode;
    public PacMan pacMan;
    private SceneInfo sceneInfo;

    public LinkedList<Point> ghostMoves = new LinkedList<>();

//-------------------------------------------------------

    public Ghost (Point position, SceneInfo sceneInfo, PacMan pacMan){  //hvad ghost skal indhold
        this.sceneInfo = sceneInfo;      //skal have sceneinfo så kan tegens
        this.pacMan = pacMan;            //skal have en pacman -- ghost efter pacman
        this.position = position;        //skal have en position

    }

    //     Abstract for switching states af ghost
    public abstract void chasingAlgorithm(); //TODO
    public abstract void fleeingAlgorithm();
    public abstract void updateRandom();


    //    Tjek efter om næste felt er en wall
    public boolean wallCollision(int x, int y) {
        Cell pathableCell = sceneInfo.cells[x][y];  //hvis det er en cells
        if (pathableCell.isPath()) {
            // ingen wall
            return false;
        }
        // hit a wall
        return true;  //der er en væg
    }

    // stien som ghost søger efter -- tegne i midten
    public void drawCenteredCircle(GraphicsContext g, double x, double y, double w, double h) {
        x = x + (w/2);
        y = y + (h/2);
        g.fillOval(x,y,w,h);
    }

    // de forskellige mode ghost kan være i -- gør det mulig at ændre mode
    @Override
    public void update(KeyCode keyPressed) {

        switch (ghostMode){
            case chasing:
                chasingAlgorithm();
                break;
            case random:
                updateRandom();
                break;
            case flee:
                fleeingAlgorithm();
                break;
        }

    }

    @Override
    public void draw(GraphicsContext graphicsContext, SceneInfo sceneInfo) {
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

}
