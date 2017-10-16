package Logic.Ghosts;

import Logic.PacMan;
import Logic.SceneInfo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import java.awt.*;
import java.util.LinkedList;

public class PinkGhost extends Ghost{

    private Point position;

    //    -------------------------------------------------------

    public PinkGhost(Point position, SceneInfo sceneInfo, PacMan pacMan) {
        super(position, sceneInfo, pacMan);
        this.setPosition(position);
    }

    // PinkGhost chasingAlgorithm breadthFirstSearch
    @Override
    public void chasingAlgorithm() {
        if (random.nextInt(100) < 50 || ghostMoves.isEmpty()) {
//            System.out.println("Pacman: " + pacMan.position);
            ghostMoves = breadthFirstSearch(this.getPosition(), pacMan.position);
//            System.out.println("Planned Road: " + ghostMoves);

        }

        if (!ghostMoves.isEmpty()) {
            Point moveToPosition = ghostMoves.removeLast();
            this.setPosition(moveToPosition);
        }
    }

    // PinkGhost fleeingAlgorithm breadthFirstSearch
    @Override
    public void fleeingAlgorithm() {
        if (random.nextInt(100) < 50 || ghostMoves.isEmpty()) {
//            System.out.println("Pacman: " + pacMan.position);
            ghostMoves = breadthFirstSearch(this.getPosition(), new Point(1, 1));
//            System.out.println("Planned Road: " + ghostMoves);
        }

        if (!ghostMoves.isEmpty()) {
            Point moveToPosition = ghostMoves.removeLast();
            this.setPosition(moveToPosition);
        }
    }

    // PinkGhost randdom breadthFirstSearch
    @Override
    public void updateRandom() {
        if (random.nextInt(100) < 50 || ghostMoves.isEmpty()) {
//            System.out.println("Pacman: " + pacMan.position);
            ghostMoves = breadthFirstSearch(this.getPosition(), new Point(random.nextInt(24), random.nextInt(24)));
//            System.out.println("Planned Road: " + ghostMoves);
        }

        if (!ghostMoves.isEmpty()) {
            Point moveToPosition = ghostMoves.removeLast();
            this.setPosition(moveToPosition);
        }

    }


    @Override
    public void update(KeyCode keyPressed) {
        super.update(keyPressed);
    }


    public LinkedList<Point> breadthFirstSearch(Point startingPosition, Point targetPosition) {
        fieldsSearched=0;

        LinkedList<Point> visitedCells = new LinkedList<>();
        LinkedList<Point> nonVisitedCells = new LinkedList<>();

        Point[][] parents = new Point[25][25];
        Point Tjekker = startingPosition;

        visitedCells.add(startingPosition);
        nonVisitedCells.add(startingPosition);


        while (!nonVisitedCells.isEmpty()) {

            LinkedList<Point> neighboursList = new LinkedList<>();
            Tjekker = nonVisitedCells.removeFirst();

            if (Tjekker.x == targetPosition.x && Tjekker.y == targetPosition.y) {
//                System.out.println("HIT");
//                System.out.println("HIT fra if. currentPos: " + currentPosition);
                return getPath(parents, Tjekker);
            }
            //tjek neighbour
            for (int i = 1; i <= 4; i++) {
                Point neighbourPosition = Tjekker;

                //  Add top
                if (i == 1 && !wallCollision(neighbourPosition.x, neighbourPosition.y - 1)) {
                    neighboursList.add(new Point(neighbourPosition.x, neighbourPosition.y - 1));
                }
                // Add Right
                if (i == 2 && !wallCollision(neighbourPosition.x + 1, neighbourPosition.y)) {
                    neighboursList.add(new Point(neighbourPosition.x + 1, neighbourPosition.y));
                }
                // Add Bottom
                if (i == 3 && !wallCollision(neighbourPosition.x, neighbourPosition.y + 1)) {
                    neighboursList.add(new Point(neighbourPosition.x, neighbourPosition.y + 1));
                }
                //  Add Left
                if (i == 4 && !wallCollision(neighbourPosition.x - 1, neighbourPosition.y)) {
                    neighboursList.add(new Point(neighbourPosition.x - 1, neighbourPosition.y));
                }

            }

            for (Point neighbourPosition : neighboursList) {
                if (!visitedCells.contains(neighbourPosition)) {
                    visitedCells.add(neighbourPosition);
                    fieldsSearched++;
                    parents[neighbourPosition.x][neighbourPosition.y] = Tjekker;
                    nonVisitedCells.addLast(neighbourPosition);
                }
            }
        }
       return getPath(parents, Tjekker);
    }


    private LinkedList<Point> getPath(Point[][] parents, Point origin){

        //  Linked list for path
        LinkedList<Point> path = new LinkedList<>();
        Point pathPosition = origin;

        // loop igennem alle parents array for at find den bedste vej
        while(pathPosition != this.getPosition()){
            path.addLast(pathPosition);
            pathPosition = parents[pathPosition.x][pathPosition.y];
        }
        return path;
    }


    @Override
    public void draw(GraphicsContext graphicsContext, SceneInfo sceneInfo) {

        for (Point predictedMoves : ghostMoves) {
            graphicsContext.setFill(Color.PINK);
            drawCenteredCircle(graphicsContext, predictedMoves.x * sceneInfo.getFieldWidth(), predictedMoves.y * sceneInfo.getFieldHeight(), sceneInfo.getFieldWidth()/2, sceneInfo.getFieldHeight()/2);
        }

        graphicsContext.setFill(Color.PINK);
        graphicsContext.fillRoundRect(this.getX() * sceneInfo.getFieldWidth(), this.getY() * sceneInfo.getFieldHeight(), sceneInfo.getFieldWidth(), sceneInfo.getFieldHeight(), 5, 5);
    }

    public int getX() {
        return getPosition().x;
    }
    public void setX(int x) {
        this.getPosition().x = x;
    }

    public int getY() {
        return getPosition().y;
    }
    public void setY(int y) {
        this.getPosition().y = y;
    }

    public Point getPosition() {
        return position;
    }
    public void setPosition(Point position) {
        this.position = position;
    }
}
