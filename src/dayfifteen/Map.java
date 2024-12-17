package dayfifteen;

import java.util.ArrayList;
import java.util.Arrays;

public class Map {
    private final ArrayList<ArrayList<String>> map;
    int robotRow;
    int robotCol;

    public Map() {
        this.map = new ArrayList<>();
    }

    public void addRow(String row) {
        if (row.contains("@")) {
            this.robotRow = this.map.size();
            this.robotCol = row.indexOf('@');
        }
        this.map.add(new ArrayList<>(Arrays.stream(row.split("")).toList()));
    }

    public void moveRobot(String direction) {
        switch (direction) {
            case "^":
                if (this.canMoveNorth(this.robotRow, this.robotCol)) {
                    this.moveNorth(this.robotRow, this.robotCol);
                    this.robotRow--;
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
                if (this.canMoveSouth(this.robotRow, this.robotCol)) {
                    this.moveSouth(this.robotRow, this.robotCol);
                    this.robotRow++;
                }
                break;
        }
    }

    private boolean canMoveNorth(int row, int col) {
        if (this.isWall(row - 1, col)) {
            return false;
        }
        if (this.isClear(row - 1, col)) {
            return true;
        }
        if (this.isObject(row - 1, col)) {
            return this.canMoveNorth(row - 1, col);
        }
        return false;
    }

    private void moveNorth(int row, int col) {
        // Don't move an empty space north
        if (this.isClear(row, col)) {
            return;
        }
        // Move this value north.
        this.moveNorth(row - 1, col);
        this.swap(row, col, row - 1, col);
    }

    private void swap(int firstRow, int firstCol, int secondRow, int secondCol) {
        String tmp = this.getValue(secondRow, secondCol);
        this.map.get(secondRow).set(secondCol, this.getValue(firstRow, firstCol));
        this.map.get(firstRow).set(firstCol, tmp);
    }

    private boolean canMoveSouth(int row, int col) {
        if (this.isWall(row + 1, col)) {
            return false;
        }
        if (this.isClear(row + 1, col)) {
            return true;
        }
        if (this.isObject(row + 1, col)) {
            return this.canMoveSouth(row + 1, col);
        }
        return false;
    }

    private void moveSouth(int row, int col) {
        // Don't move an empty space north
        if (this.isClear(row, col)) {
            return;
        }
        // Move this value north.
        this.moveSouth(row + 1, col);
        this.swap(row, col, row + 1, col);
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

    private boolean isWall(int row, int col) {
        return this.getValue(row, col).equals("#");
    }

    private boolean isObject(int row, int col) {
        return this.getValue(row, col).equals("O");
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
            for (int col = 1; col < this.map.get(row).size() - 1; col++) {
                if (this.isObject(row, col)) {
                    total += (100L * row) + col;
                }
            }
        }
        return total;
    }
}
