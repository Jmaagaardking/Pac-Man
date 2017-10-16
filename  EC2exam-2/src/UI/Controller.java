package UI;

import Logic.GameObject;
import Logic.Ghosts.*;
import Logic.PacMan;
import Logic.PowerPellet;
import Logic.SceneInfo;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.awt.*;
import java.util.ArrayList;

public class Controller {


    @FXML
    private Canvas myCanvas; //canvas fra fxml
    @FXML
    public Text score, bfsText, biText, bsText; // text label fra fxml

    //------------------------------------------------------

    private KeyCode keyPressed = KeyCode.BACK_SPACE;
    private float refreshRate = 350;
    private int counter;
    private int superCounter;
    boolean superPac = false;
    private SceneInfo sceneInfo;
    private PacMan pacMan;
    private Ghost blueGhost;
    private Ghost pinkGhost;
    private Ghost orangeGhost;
    private Ghost redGhost;

    //------------------------------------------------------

    ArrayList<Ghost> allGhosts = new ArrayList<>();
    ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();

    //------------------------------------------------------


    public void keyPressed(KeyCode keyCode) {
        this.keyPressed = keyCode;  //key lytter
    }

        // load alle ting
    public void initialize() {
        sceneInfo = new SceneInfo(myCanvas);
        sceneInfo.generateMaze();
        sceneInfo.createPowerPellets();

        pacMan = new PacMan(new Point(12,18), sceneInfo); //laver pacman
        gameObjects.add(pacMan);

        redGhost = new RedGhost(new Point(15,12), sceneInfo, pacMan); //laver Ghost
        allGhosts.add(redGhost); //add allghosts

        blueGhost = new BlueGhost(new Point(15,12), sceneInfo, pacMan); //laver Ghost
        allGhosts.add(blueGhost); //add allghosts

        pinkGhost = new PinkGhost(new Point(11,12) ,sceneInfo, pacMan); //laver Ghost
        allGhosts.add(pinkGhost); //add allghosts

        orangeGhost = new OrangeGhost(new Point(13,12), sceneInfo, pacMan); //laver Ghost
        allGhosts.add(orangeGhost); //add allghosts

        for (Ghost ghost : allGhosts){  //loop alle ghosts
            gameObjects.add(ghost); //add til gameobjects
        }

      //  System.out.println("gameObject size: " + gameObjects.size());


        // Start and control game loop
        new AnimationTimer() {
            long lastUpdate;

            public void handle(long now) {
                if (now > lastUpdate + refreshRate * 1000000) {
                    lastUpdate = now;
                    update(now);
                }
            }
        }.start();
    }


    private void update(long now) {

        countersUpdate();
        changeGhostModes();
        drawCanvas();

        for (int i = 0; i < gameObjects.size(); i++) {  //loop alle gameobjects igennem
            gameObjects.get(i).update(keyPressed);
        }

    }


    private void drawCanvas() {
        GraphicsContext gc = myCanvas.getGraphicsContext2D();


        //        tegne alle cells & dots
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                sceneInfo.cells[i][j].draw(gc, sceneInfo);
                sceneInfo.dots[i][j].draw(gc, sceneInfo);
            }
        }

        //       tegne  alle powerPellets
        for (PowerPellet powerPellet : sceneInfo.powerPellets){
            powerPellet.draw(gc, sceneInfo);
        }

        //        Tegner alle spil-objekter
        for (GameObject g : gameObjects) {
            g.draw(gc, sceneInfo);
        }
    }
        //skriver antal træk
    private void countersUpdate(){
        bfsText.setText("   BFS:  " + Integer.toString(pinkGhost.fieldsSearched));
        biText.setText("   BI:  " + Integer.toString(blueGhost.fieldsSearched));
        bsText.setText("   BS:  " + Integer.toString(orangeGhost.fieldsSearched));

        // sætte points for spise dots
        score.setText(Integer.toString(pacMan.pacPoints));
    }

        // ændre mode på alle ghoste
    private void changeGhostModes(){
        counter++;

//        System.out.println("counter: " + counter );

        if (counter == 60) {
            counter = 0;
        }

        if (counter < 40){
            for (Ghost g : allGhosts){
                g.ghostMode = Ghost.GhostMode.chasing;
//                System.out.println("chasing-->");
            }
        } else if (counter > 50){
            for (Ghost g : allGhosts){
                g.ghostMode = Ghost.GhostMode.random;
//                System.out.println("random...");
            }
        }


        if (pacMan.powerPickUp()){
            superPac = true;
//            System.out.println("POWER PICKUP.");
        }

        if (superPac) {
            superCounter++;
            if (superCounter < 20) {
                for (Ghost g : allGhosts) {
                    g.ghostMode = Ghost.GhostMode.flee;
                    if (g.getX() == pacMan.position.x && g.getY() == pacMan.position.y) {
                        g.setX(12);
                        g.setY(12);
                    }
                }
            }
            if (superCounter > 20) {
                superCounter = 0;
                superPac = false;
            }
            System.out.println("super counter: " + superCounter);
        }
    }
}

