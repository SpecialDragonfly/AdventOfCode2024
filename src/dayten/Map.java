package dayten;

import java.util.*;
import java.util.stream.Collectors;

public class Map {
    private final ArrayList<ArrayList<Integer>> map;
    private final Vector<IntPoint> trailHeads;

    public Map() {
        this.map = new ArrayList<>();
        this.trailHeads = new Vector<>();
    }

    public void addRow(String row) {
        ArrayList<Integer> line = Arrays.stream(row.split(""))
                .map(Integer::valueOf)
                .collect(Collectors.toCollection(ArrayList::new));
        for (int i = 0; i < line.size(); i++) {
            if (line.get(i) == 0) {
                this.trailHeads.add(new IntPoint(this.map.size(), i));
            }
        }
        this.map.add(line);
    }

    public Vector<IntPoint> getTrailHeads() {
        return this.trailHeads;
    }

    public int getRatingForTrailhead(IntPoint start) {
        Vector<IntPoint> ends = new Vector<>(this.getEndPoints(start));
        return ends.size();
    }

    public int getScoreForTrailhead(IntPoint start) {
        Vector<IntPoint> ends = new Vector<>(this.getEndPoints(start));
        return (int) ends.stream().distinct().count();
    }

    public Vector<IntPoint> getEndPoints(IntPoint start) {
        if (this.valueAt(start) == 9) {
            Vector<IntPoint> p = new Vector<>();
            p.add(start);
            return p;
        }
        // Check N, S, E, W for valid routes.
        // If they're valid, add them to the stack.
        Stack<String> directions = new Stack<>();
        if (this.northValid(start)) {
            directions.push("N");
        }
        if (this.eastValid(start)) {
            directions.push("E");
        }
        if (this.southValid(start)) {
            directions.push("S");
        }
        if (this.westValid(start)) {
            directions.push("W");
        }
        Vector<IntPoint> points = new Vector<>();
        while (!directions.isEmpty()) {
            String direction = directions.pop();
            switch (direction) {
                case "N":
                    points.addAll(this.getEndPoints(new IntPoint((start.getX() - 1), start.getY())));
                    break;
                case "E":
                    points.addAll(this.getEndPoints(new IntPoint((start.getX()), start.getY() + 1)));
                    break;
                case "S":
                    points.addAll(this.getEndPoints(new IntPoint((start.getX() + 1), start.getY())));
                    break;
                case "W":
                    points.addAll(this.getEndPoints(new IntPoint((start.getX()), start.getY() - 1)));
                    break;
            }
        }

        return points;
    }

    private boolean northValid(IntPoint start) {
        if (start.getX() == 0) {
            return false;
        }
        int value = this.valueAt(start);
        return (this.valueAt(start.getX() - 1, start.getY()) == value + 1);
    }

    private boolean eastValid(IntPoint start) {
        if (start.getY() == this.map.getFirst().size() - 1) {
            return false;
        }
        int value = this.valueAt(start);
        return (this.valueAt(start.getX(), start.getY() + 1) == value + 1);
    }

    private boolean southValid(IntPoint start) {
        if (start.getX() == this.map.size() - 1) {
            return false;
        }
        int value = this.valueAt(start);
        return (this.valueAt(start.getX() + 1, start.getY()) == value + 1);
    }

    private boolean westValid(IntPoint start) {
        if (start.getY() == 0) {
            return false;
        }
        int value = this.valueAt(start);
        return (this.valueAt(start.getX(), start.getY() - 1) == value + 1);
    }

    private int valueAt(IntPoint start) {
        return this.map.get(start.getX()).get(start.getY());
    }

    private int valueAt(int x, int y) {
        return this.map.get(x).get(y);
    }
}