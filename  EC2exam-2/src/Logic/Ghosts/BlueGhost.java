package Logic.Ghosts;


import Logic.PacMan;
import Logic.SceneInfo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.LinkedList;


public class BlueGhost extends Ghost {

    private Point position;

    //    -------------------------------------------------------

    public BlueGhost(Point position, SceneInfo sceneInfo, PacMan pacMan) {
        super(position, sceneInfo, pacMan);
        this.setPosition(position);
    }

    //TODO PinkGhost chasingAlgorithm bidirectionalSearch
    @Override
    public void chasingAlgorithm() {
        if (random.nextInt(100) < 50 || ghostMoves.isEmpty()) {  //opdater min 50 eller ghostmoves tom
            ghostMoves = bidirectionalSearch (this.getPosition(), pacMan.position); //ghostMoves = BID -- søg efter
        }

        if (!ghostMoves.isEmpty()) {  //ikke er tom
            Point moveToPosition = ghostMoves.removeLast(); //fjerne sidste fra listen -- går frem
            this.setPosition(moveToPosition);  //rykker til fjerne
        }
    }
    //TODO PinkGhost fleeingAlgorithm bidirectionalSearch
    @Override
    public void fleeingAlgorithm() {
        // System.out.println("Blue trying to flee");
        if (random.nextInt(300) < 50 || ghostMoves.isEmpty()) {
            ghostMoves = bidirectionalSearch (this.getPosition(), new Point(23,23));
        }

        if (!ghostMoves.isEmpty()) {
            Point moveToPosition = ghostMoves.removeLast();
            this.setPosition(moveToPosition);
        }
    }
    //TODO PinkGhost updateRandom bidirectionalSearch
    @Override
    public void updateRandom() {
        //System.out.println("blue truing to randomAround");
        if (random.nextInt(100) < 50 || ghostMoves.isEmpty()) {ghostMoves = bidirectionalSearch (this.getPosition(), new Point(random.nextInt(23 -1 +1)+1, random.nextInt(23 -1 +1)+1));
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



    public LinkedList<Point> bidirectionalSearch(Point startingPosition, Point targetPosition) {
        fieldsSearched = 0;

        LinkedList<Point> startingPositionVisitedCells      = new LinkedList<>();
        LinkedList<Point> startingPositionNonVisitedCells   = new LinkedList<>();

        LinkedList<Point> targetPositionVisitedCells        = new LinkedList<>(); //ekstre
        LinkedList<Point> targetPositionNonVisitedCells     = new LinkedList<>(); //ekstre

        Point[][] parents = new Point[25][25];
        Point[][] parents2 = new Point[25][25];  //ekstre

        Point startTjekker   = startingPosition;
        Point targetTjekker     = targetPosition;

        startingPositionVisitedCells.add(startingPosition);
        startingPositionNonVisitedCells.add(startingPosition);  //ekstre

        targetPositionVisitedCells.add(targetPosition);
        targetPositionNonVisitedCells.add(targetPosition);  //ekstre

//-----------------------------------------------------------------
        //hvis lister ikke er tom kør  //TODO tjek finder sti tag en af gang
        while (!startingPositionNonVisitedCells.isEmpty() && !targetPositionNonVisitedCells.isEmpty()){

            LinkedList<Point> startingPositionNeighboursList = new LinkedList<>();
            LinkedList<Point> targetPositionNeighboursList = new LinkedList<>();


            if (!startingPositionNonVisitedCells.isEmpty()) {  //hvis ikke er tom //TODO

                startTjekker = startingPositionNonVisitedCells.removeFirst(); // = start og fjerne

                if (targetPositionVisitedCells.contains(startTjekker)) {  //tjekker = target -- sti færdig
                    //System.out.println("HIT from start");
                    return getPath(parents, parents2, startTjekker , targetPosition); //return sti TODO færdig
                }
                //tjek nabo og tilføjde til listen
                for (int i = 1; i <= 4; i++) {
                    Point neighbourPosition = startTjekker;

                    //  Add top
                    if (i == 1 && !wallCollision(neighbourPosition.x, neighbourPosition.y - 1)) {
                        startingPositionNeighboursList.add(new Point(neighbourPosition.x, neighbourPosition.y - 1));
                    }
                    // Add Right
                    if (i == 2 && !wallCollision(neighbourPosition.x + 1, neighbourPosition.y)) {
                        startingPositionNeighboursList.add(new Point(neighbourPosition.x + 1, neighbourPosition.y));
                    }
                    // Add Bottom
                    if (i == 3 && !wallCollision(neighbourPosition.x, neighbourPosition.y + 1)) {
                        startingPositionNeighboursList.add(new Point(neighbourPosition.x, neighbourPosition.y + 1));
                    }
                    //  Add Left
                    if (i == 4 && !wallCollision(neighbourPosition.x - 1, neighbourPosition.y)) {
                        startingPositionNeighboursList.add(new Point(neighbourPosition.x - 1, neighbourPosition.y));
                    }
                }
                //nabo tjek og tilføjdes til lister -- en afgangen
                for (Point neighbourPosition : startingPositionNeighboursList) { //1,2,3,4
                    if (!startingPositionVisitedCells.contains(neighbourPosition)) { //indhold
                        startingPositionVisitedCells.add(neighbourPosition); //add
                        fieldsSearched++;
                        parents[neighbourPosition.x][neighbourPosition.y] = startTjekker; //sæt = find den igen
                        startingPositionNonVisitedCells.addLast(neighbourPosition); //tilføjde i bund af listen
                    }
                }
            }


            if (!targetPositionNonVisitedCells.isEmpty()) {

                targetTjekker = targetPositionNonVisitedCells.removeFirst(); // = target og fjerne

                if (startingPositionVisitedCells.contains(targetTjekker)) {  //tjekker = target -- sti færdig
                    //    System.out.println("HIT from target");
                    return getPath(parents, parents2, targetTjekker, targetPosition); //return sti
                }

                //tjek nabo og tilføjde til listen
                for (int i = 1; i <= 4; i++) {
                    Point neighbourPosition = targetTjekker;

                    //  Add top
                    if (i == 1 && !wallCollision(neighbourPosition.x, neighbourPosition.y - 1)) {
                        targetPositionNeighboursList.add(new Point(neighbourPosition.x, neighbourPosition.y - 1));
                    }
                    // Add Right
                    if (i == 2 && !wallCollision(neighbourPosition.x + 1, neighbourPosition.y)) {
                        targetPositionNeighboursList.add(new Point(neighbourPosition.x + 1, neighbourPosition.y));
                    }
                    // Add Bottom
                    if (i == 3 && !wallCollision(neighbourPosition.x, neighbourPosition.y + 1)) {
                        targetPositionNeighboursList.add(new Point(neighbourPosition.x, neighbourPosition.y + 1));
                    }
                    //  Add Left
                    if (i == 4 && !wallCollision(neighbourPosition.x - 1, neighbourPosition.y)) {
                        targetPositionNeighboursList.add(new Point(neighbourPosition.x - 1, neighbourPosition.y));
                    }
                }

                //nabo tjek og tilføjdes til lister -- en afgangen
                for (Point neighbourPosition : targetPositionNeighboursList) {
                    if (!targetPositionVisitedCells.contains(neighbourPosition)) {
                        targetPositionVisitedCells.add(neighbourPosition);
                        fieldsSearched++;
                        parents2[neighbourPosition.x][neighbourPosition.y] = targetTjekker; //sæt = find den igen
                        targetPositionNonVisitedCells.addLast(neighbourPosition); //tilføjde i bund af listen
                    }
                }
            }


        }
        return getPath(parents, parents2, targetTjekker, targetPosition);
    }

    //find vej tilbage
    private LinkedList<Point> getPath(Point[][] parents, Point[][] parents2, Point meetingPosition, Point targetPosition ){


        LinkedList<Point> path = new LinkedList<>(); //  Linked list for path
        Point predictedPath  = meetingPosition; //-- meetingPosition position

        while (predictedPath != this.getPosition()) { //ikke =
            path.addLast(predictedPath);
            predictedPath = parents[predictedPath.x][predictedPath.y];
        }

        predictedPath  = meetingPosition;

        while (predictedPath != targetPosition) { //ikke =
            path.addFirst(predictedPath);
            predictedPath = parents2[predictedPath.x][predictedPath.y]; //TODO ib
        }

        return path;
    }


    @Override
    public void draw(GraphicsContext graphicsContext, SceneInfo sceneInfo) {
        for (Point predictedMoves : ghostMoves) {
            graphicsContext.setFill(Color.CYAN);
            drawCenteredCircle(graphicsContext, predictedMoves.x * sceneInfo.getFieldWidth(), predictedMoves.y * sceneInfo.getFieldHeight(), sceneInfo.getFieldWidth()/2, sceneInfo.getFieldHeight()/2);
        }

        graphicsContext.setFill(javafx.scene.paint.Color.CYAN);
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
