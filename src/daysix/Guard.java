package daysix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Guard {
    int row;
    int col;
    String direction;
    ArrayList<String> movementHistory = new ArrayList<>();

    public Guard(int row, int col, String direction) {
        this.row = row;
        this.col = col;
        this.direction = direction;
    }

    public Set<String> getMovementHistory() {
        Set<String> coords = new HashSet<>();
        for (String movement : movementHistory) {
            coords.add(movement.substring(0, movement.lastIndexOf(":")));
        }
        return coords;
    }

    public int numberOfSteps() {
        Set<String> coords = new HashSet<>();
        for (String movement : movementHistory) {
            coords.add(movement.substring(0, movement.lastIndexOf(":")));
        }
        return coords.size();
    }

    public void move(Map map) throws GuardLeft, InfiniteLoop {
        this.movementHistory.add(this.row + ":" + this.col + ":" + this.direction);
        switch (this.direction) {
            case "^" -> {
                if (map.blocked(this.row - 1, this.col)) {
                    this.rotate();
                } else {
                    this.moveNorth();
                    if (this.isInLoop()) {
                        throw new InfiniteLoop();
                    }
                }
            }
            case ">" -> {
                if (map.blocked(this.row, this.col + 1)) {
                    this.rotate();
                } else {
                    this.moveEast();
                    if (this.isInLoop()) {
                        throw new InfiniteLoop();
                    }
                }
            }
            case "V" -> {
                if (map.blocked(this.row + 1, this.col)) {
                    this.rotate();
                } else {
                    this.moveSouth();
                    if (this.isInLoop()) {
                        throw new InfiniteLoop();
                    }
                }
            }
            case "<" -> {
                if (map.blocked(this.row, this.col - 1)) {
                    this.rotate();
                } else {
                    this.moveWest();
                    if (this.isInLoop()) {
                        throw new InfiniteLoop();
                    }
                }
            }
        }
    }

    private void rotate() {
        switch (this.direction) {
            case "^" -> this.direction = ">";
            case ">" -> this.direction = "V";
            case "V" -> this.direction = "<";
            case "<" -> this.direction = "^";
        }
        movementHistory.add(this.row + ":" + this.col + ":+");
    }

    private void moveNorth() {
        this.row--;
    }

    private void moveSouth() {
        this.row++;
    }

    private void moveEast() {
        this.col++;
    }

    private void moveWest() {
        this.col--;
    }

    private boolean isInLoop() {
        return this.movementHistory.contains(this.row + ":" + this.col + ":" + this.direction);
    }
}
