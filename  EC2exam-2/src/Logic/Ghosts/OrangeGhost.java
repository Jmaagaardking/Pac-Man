package Logic.Ghosts;


import Logic.PacMan;
import Logic.SceneInfo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;

public class OrangeGhost extends Ghost{

    private Point position;

    public OrangeGhost(Point position, SceneInfo sceneInfo, PacMan pacMan) {
        super(position, sceneInfo, pacMan);
        this.setPosition(position);
    }


    @Override
    public void update(KeyCode keyPressed) {
        super.update(keyPressed);
    }

    @Override
    public void chasingAlgorithm( ) {
        if (random.nextInt(200) < 50 || ghostMoves.isEmpty()) {
            ghostMoves = bestFirstSearch(this.getPosition(), pacMan.position);
        }
        if (!ghostMoves.isEmpty()) {
            Point moveTo = ghostMoves.removeLast();
            this.setPosition(moveTo);
        }

    }

    @Override
    public void fleeingAlgorithm() {
        if (random.nextInt(200) < 50 || ghostMoves.isEmpty()) {
            ghostMoves = bestFirstSearch(this.getPosition(), new Point(23,1));
        }
        if (!ghostMoves.isEmpty()) {
            Point moveTo = ghostMoves.removeLast();
            this.setPosition(moveTo);
        }
    }

    @Override
    public void updateRandom() {
        if (random.nextInt(200) < 50 || ghostMoves.isEmpty()) {
            ghostMoves = bestFirstSearch(this.getPosition(), new Point(random.nextInt(24), random.nextInt(24)));
        }
        if (!ghostMoves.isEmpty()) {
            Point moveTo = ghostMoves.removeLast();
            this.setPosition(moveTo);
        }
    }


    public LinkedList<Point> bestFirstSearch(Point startingPoint, Point target) {
        fieldsSearched = 0;
        LinkedList<Point> visitedPoints = new LinkedList<>();
        LinkedList<Point> openPoints = new LinkedList<>();
        LinkedList<Integer> heuristics = new LinkedList<>(); //ekstre
        Point Tjekker = startingPoint;
        Point[][] parents = new Point[25][25];

        //Udgangspunktet tilføjes til listerne
        visitedPoints.add(startingPoint);
        openPoints.add(startingPoint);
        heuristics.add(manhattenDistance(Tjekker,target));


        while (!openPoints.isEmpty()) {
            int closestPoint = heuristics.indexOf(Collections.min(heuristics)); //tag det laves tal

            LinkedList<Point> neighboursList = new LinkedList<>();
            Tjekker = openPoints.get((closestPoint));
            heuristics.remove(closestPoint);

            if (Tjekker.x == target.x && Tjekker.y == target.y) {

                return getPath(parents,Tjekker);

            }

            visitedPoints.add(Tjekker);
            fieldsSearched++;
            openPoints.remove(Tjekker);



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
                if (!visitedPoints.contains(neighbourPosition)) {
                    heuristics.add(manhattenDistance(neighbourPosition, target));

                    parents[neighbourPosition.x][neighbourPosition.y] = Tjekker;
                    openPoints.addLast(neighbourPosition);
                }

            }
        }
        return getPath(parents,startingPoint);
    }

    private LinkedList<Point> getPath(Point[][] parents, Point origin){

        //Linked list for path
        LinkedList<Point> path = new LinkedList<>();
        Point current = origin;

        //Loop through parents array to find the shortest path
        while(current != this.getPosition()){

            path.addLast(current);
            current = parents[current.x][current.y];


        }
        return path;
    }
    //Længden fra startpunkt til slutpunkt.
    public int manhattenDistance(Point start, Point goal){
        int distance = Math.abs(start.x-goal.x) + Math.abs(start.y-goal.y);

        return distance;

    }

    @Override
    public void draw(GraphicsContext graphicsContext, SceneInfo sceneInfo) {
        for (Point predictedMoves : ghostMoves) {
            graphicsContext.setFill(Color.ORANGE);
            drawCenteredCircle(graphicsContext, predictedMoves.x * sceneInfo.getFieldWidth(), predictedMoves.y * sceneInfo.getFieldHeight(), sceneInfo.getFieldWidth()/2, sceneInfo.getFieldHeight()/2);
        }

        graphicsContext.setFill(Color.ORANGE);
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
