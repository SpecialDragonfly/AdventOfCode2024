package dayfifteen;

import util.IntPoint;

import java.util.*;

public class WideMap {
    private final ArrayList<ArrayList<String>> map;
    int robotRow;
    int robotCol;

    public WideMap() {
        this.map = new ArrayList<>();
    }

    public void addRow(String row) {
        if (row.contains("@")) {
            this.robotRow = this.map.size();
            this.robotCol = row.indexOf('@');
        }

        this.map.add(new ArrayList<>(Arrays.stream(row.split("")).toList()));
    }

    private void moveNorth(Stack<IntPoint> pointsToMove, int row, int col) throws ImmovableException {
        if (this.isWall(row, col)) {
            throw new ImmovableException();
        }
        if (this.isObjectRightSide(row, col)) {
            this.moveNorth(pointsToMove, row, col - 1);
        }
        if (this.isObjectLeftSide(row, col)) {
            if (!pointsToMove.contains(new IntPoint(row, col))) {
                pointsToMove.push(new IntPoint(row, col));
            }
            if (!pointsToMove.contains(new IntPoint(row, col + 1))) {
                pointsToMove.push(new IntPoint(row, col + 1));
            }
            this.moveNorth(pointsToMove, row - 1, col);
            this.moveNorth(pointsToMove, row - 1, col + 1);
        }
    }

    private void moveSouth(Stack<IntPoint> pointsToMove, int row, int col) throws ImmovableException {
        if (this.isWall(row, col)) {
            throw new ImmovableException();
        }
        if (this.isObjectRightSide(row, col)) {
            this.moveSouth(pointsToMove, row, col - 1);
        }
        if (this.isObjectLeftSide(row, col)) {
            if (!pointsToMove.contains(new IntPoint(row, col))) {
                pointsToMove.push(new IntPoint(row, col));
            }
            if (!pointsToMove.contains(new IntPoint(row, col + 1))) {
                pointsToMove.push(new IntPoint(row, col + 1));
            }
            this.moveSouth(pointsToMove, row + 1, col);
            this.moveSouth(pointsToMove, row + 1, col + 1);
        }
    }

    public void moveRobot(String direction) {
        switch (direction) {
            case "^":
                try {
                    Stack<IntPoint> pointsToMove = new Stack<>();
                    pointsToMove.push(new IntPoint(this.robotRow, this.robotCol));
                    this.moveNorth(pointsToMove, this.robotRow - 1, this.robotCol);
                    pointsToMove.sort((first, second) -> first.getX() > second.getX() ? -1 : 1);
                    while (!pointsToMove.isEmpty()) {
                        IntPoint point = pointsToMove.pop();
                        this.swap(point.getX(), point.getY(), point.getX() - 1, point.getY());
                    }

                    this.robotRow--;
                } catch (ImmovableException e) {
                    // Can't move.
                    //System.out.println("Can't move");
                }
                break;
            case ">":
                if (this.canMoveEast(this.robotRow, this.robotCol)) {
                    this.moveEast(this.robotRow, this.robotCol);
                    this.robotCol++;
                }
                break;
            case "<":
                if (this.canMoveWest(this.robotRow, this.robotCol)) {
                    this.moveWest(this.robotRow, this.robotCol);
                    this.robotCol--;
                }
                break;
            case "v":
                try {
                    Stack<IntPoint> pointsToMove = new Stack<>();
                    pointsToMove.push(new IntPoint(this.robotRow, this.robotCol));
                    this.moveSouth(pointsToMove, this.robotRow + 1, this.robotCol);
                    pointsToMove.sort((first, second) -> first.getX() < second.getX() ? -1 : 1);
                    while (!pointsToMove.isEmpty()) {
                        IntPoint point = pointsToMove.pop();
                        this.swap(point.getX(), point.getY(), point.getX() + 1, point.getY());
                    }

                    this.robotRow++;
                } catch (ImmovableException e) {
                    // Can't move.
                    //System.out.println("Can't move");
                }
                break;
        }
    }

    private boolean canMoveWest(int row, int col) {
        if (this.isWall(row, col - 1)) {
            return false;
        }
        if (this.isClear(row, col - 1)) {
            return true;
        }
        if (this.isObject(row, col - 1)) {
            return this.canMoveWest(row, col - 1);
        }
        return false;
    }

    private void moveWest(int row, int col) {
        // Don't move an empty space north
        if (this.isClear(row, col)) {
            return;
        }
        // Move this value north.
        this.moveWest(row, col - 1);
        this.swap(row, col, row, col - 1);
    }

    private boolean canMoveEast(int row, int col) {
        if (this.isWall(row, col + 1)) {
            return false;
        }
        if (this.isClear(row, col + 1)) {
            return true;
        }
        if (this.isObject(row, col + 1)) {
            return this.canMoveEast(row, col + 1);
        }
        return false;
    }

    private void moveEast(int row, int col) {
        // Don't move an empty space north
        if (this.isClear(row, col)) {
            return;
        }
        // Move this value north.
        this.moveEast(row, col + 1);
        this.swap(row, col, row, col + 1);
    }

    private void swap(int firstRow, int firstCol, int secondRow, int secondCol) {
        String tmp = this.getValue(secondRow, secondCol);
        this.map.get(secondRow).set(secondCol, this.getValue(firstRow, firstCol));
        this.map.get(firstRow).set(firstCol, tmp);
    }

    private boolean isWall(int row, int col) {
        return this.getValue(row, col).equals("#");
    }

    private boolean isObject(int row, int col) {
        return this.getValue(row, col).equals("]") || this.getValue(row, col).equals("[");
    }

    private boolean isObjectRightSide(int row, int col) {
        return this.getValue(row, col).equals("]");
    }

    private boolean isObjectLeftSide(int row, int col) {
        return this.getValue(row, col).equals("[");
    }

    private boolean isClear(int row, int col) {
        return this.getValue(row, col).equals(".");
    }

    private String getValue(int row, int col) {
        return this.map.get(row).get(col);
    }

    public void output() {
        for (int i = 0; i < this.map.size(); i++) {
            for (int j = 0; j < this.map.get(i).size(); j++) {
                System.out.print(this.getValue(i, j));
            }
            System.out.println();
        }
        System.out.println();
    }

    public long getGPSValue() {
        long total = 0;
        // We can skip top and bottom walls
        for (int row = 1; row < this.map.size() - 1; row++) {
            // We can skip left and right walls
            for (int col = 2; col < this.map.getFirst().size() - 2; col++) {
                if (this.isObjectLeftSide(row, col)) {
                    total += (row * 100L) + col;
                }
            }
        }
        return total;
    }
}
