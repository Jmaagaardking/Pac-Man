package Logic;

import javafx.scene.canvas.Canvas;

import java.awt.*;
import java.util.ArrayList;

public class SceneInfo {

    private double fieldHeight;
    private double fieldWidth;

    private int rows = 25; //sætte størrelser på maza
    private int columns = 25; // -:-

    public Cell[][] cells; //datastruktur
    public Cell cell;

    public PacDot[][] dots;
    public PacDot pacDot;

    public ArrayList<PowerPellet> powerPellets = new ArrayList<>(); //arraylist til dem
    public PowerPellet powerPelletUL; //forskellige powerPellet
    public PowerPellet powerPelletDL; // -:-
    public PowerPellet powerPelletUR; // -:-
    public PowerPellet powerPelletDR; // -:-

    //    -------------------------------------------------------

    // indele grid
    public SceneInfo(Canvas canvas) {
        fieldHeight = canvas.getHeight() / rows; //snak om
        fieldWidth = canvas.getWidth() / columns;
    }

    // laver maza med cell og dot
    public void generateMaze() {
        cells = new Cell[rows][columns];
        dots = new PacDot[rows][columns];

        // Hardcoded Maze
        int[] xWallValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 3, 3, 3, 4, 4, 4, 6, 6, 6, 7, 7, 7, 8, 8, 8, 9, 9, 9, 10, 10, 10, 12, 12, 12, 12, 14, 14, 14, 15, 15, 15, 16, 16, 16, 17, 17, 17, 18, 18, 18, 20, 20, 20, 21, 21, 21, 22, 22, 22, 2, 2, 3, 3, 4, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 1, 2, 3, 4, 6, 6, 6, 6, 6, 6, 7, 8, 9, 10, 8, 9, 10, 11, 12, 13, 14, 15, 16, 12, 12, 18, 18, 18, 18, 18, 18, 14, 15, 16, 17, 20, 20, 21, 21, 22, 22, 20, 20, 20, 21, 21, 21, 22, 22, 22, 23, 23, 23, 20, 20, 20, 21, 21, 21, 22, 22, 22, 23, 23, 23, 20, 20, 20, 20, 21, 22, 18, 17, 16, 15, 14, 22, 22, 23, 23, 14, 15, 16, 17, 18, 19, 20, 21, 22, 18, 18, 18, 18, 18, 18, 8, 9, 10, 11, 12, 13, 14, 15, 16, 12, 12, 8, 9, 10, 11, 12, 13, 14, 15, 16, 12, 12, 8, 9, 10, 11, 12, 13, 14, 15, 16, 12, 12, 12, 12, 6, 7, 8, 9, 10, 2, 3, 4, 4, 4, 4, 6, 6, 6, 1, 2, 1, 2, 6, 6, 6, 2, 3, 4, 5, 6, 7, 8, 9, 10, 8, 8, 8, 8, 9, 10, 11, 13, 14, 15, 16, 16, 16, 16, 15, 14, 13, 12, 11, 10, 9};
        int[] yWallValues = {0, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 1, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 2, 3, 4, 6, 7, 6, 7, 6, 7, 9, 9, 9, 9, 10, 10, 10, 10, 11, 11, 11, 11, 13, 13, 13, 13, 14, 14, 14, 14, 15, 15, 15, 15, 6, 7, 8, 9, 10, 11, 8, 8, 8, 8, 6, 6, 6, 6, 6, 6, 6, 6, 6, 7, 8, 6, 7, 8, 9, 10, 11, 8, 8, 8, 8, 6, 7, 6, 7, 6, 7, 9, 10, 11, 9, 10, 11, 9, 10, 11, 9, 10, 11, 13, 14, 15, 13, 14, 15, 13, 14, 15, 13, 14, 15, 17, 18, 19, 20, 17, 17, 17, 17, 17, 17, 17, 19, 20, 19, 20, 22, 22, 22, 22, 22, 22, 22, 22, 22, 21, 20, 19, 13, 14, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 16, 17, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 21, 22, 17, 17, 17, 17, 17, 17, 17, 17, 18, 19, 20, 13, 14, 15, 19, 19, 20, 20, 19, 20, 21, 22, 22, 22, 22, 22, 22, 22, 22, 22, 13, 12, 11, 10, 10, 10, 10, 10, 10, 10, 10, 11, 12, 13, 13, 13, 13, 13, 13, 13, 13};

        // Add Cells
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {      //løber grid igennem
                cell = new Cell(new Point(i, j), true); //laver stier
                cells[i][j] = cell;
            }
        }

        // Add dots
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {      //løber grid igennem
                pacDot = new PacDot(new Point(i, j), true); //laver pacdot
                dots[i][j] = pacDot;
            }
        }

        // tjek om der er en wall -- false cells og dots
        for (int i = 0; i < xWallValues.length; i++) {
            cells[xWallValues[i]][yWallValues[i]].setPath(false); // ikke lave
            dots[xWallValues[i]][yWallValues[i]].setEaten(false); // ikke lave
        }
    }

    public void createPowerPellets(){

        // Add PowerPellets - Hardcoded
        powerPelletUL = new PowerPellet(new Point(1,3), true); //hardcode sætte til true
        powerPellets.add(powerPelletUL); //add til arraylist
        powerPelletDL = new PowerPellet(new Point(1,18), true);
        powerPellets.add(powerPelletDL);
        powerPelletDR = new PowerPellet(new Point(23,18), true);
        powerPellets.add(powerPelletDR);
        powerPelletUR = new PowerPellet(new Point(23,3), true);
        powerPellets.add(powerPelletUR);
    }


    // Størrelsen på fields i højde og bredte
    public double getFieldHeight() {
        return fieldHeight;
    }
    public void setFieldHeight(double fieldHeight) {
        this.fieldHeight = fieldHeight;
    }
    public double getFieldWidth() {
        return fieldWidth;
    }
    public void setFieldWidth(double fieldWidth) {
        this.fieldWidth = fieldWidth;
    } //bliver aldrig brugt


    // Antal rows
    public int getRows() {
        return rows;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    // Antal Columns
    public int getColumns() {
        return columns;
    }
    public void setColumns(int columns) {
        this.columns = columns;
    }
}
