package daysix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Map {
    private final ArrayList<ArrayList<String>> map;
    private int guardRowCache;
    private int guardColCache;
    public Map() {
        this.map = new ArrayList<>();
    }

    public void addRow(String row) {
        if (row.contains("^")) {
            this.guardRowCache = this.map.size();
            this.guardColCache = row.indexOf("^");
        }
        this.map.add(new ArrayList<>(Arrays.stream(row.split("")).toList()));
    }

    public Integer[] getGuard() {
        return new Integer[]{this.guardRowCache, this.guardColCache};
    }

    public boolean blocked(int row, int col) throws GuardLeft {
        if (row < 0 || row == this.map.size() || col < 0 || col == this.map.get(row).size()) {
            throw new GuardLeft();
        }
        return this.valueAt(row, col).equals("#") || this.valueAt(row, col).equals("@");
    }

    public String valueAt(int row, int col) {
        return this.map.get(row).get(col);
    }

    public void addObstacle(int row, int col) {
        this.map.get(row).set(col, "@");
    }

    public void removeObstacle(int row, int col) {
        this.map.get(row).set(col, ".");
    }

    public boolean checkRowGoingEast(int row, int col) {
        List<String> rowData = this.map.get(row);
        return !rowData.subList(col, rowData.size()).stream()
            .filter(v -> v.equals("#") || v.equals("@"))
            .toList()
            .isEmpty();
    }

    public boolean checkRowGoingWest(int row, int col) {
        List<String> rowData = this.map.get(row);
        return !rowData.subList(0, col + 1).stream()
                .filter(v -> v.equals("#") || v.equals("@"))
                .toList()
                .isEmpty();
    }

    public boolean checkColGoingSouth(int row, int col) {
        List<String> colData = this.map.stream().map(rowData -> rowData.get(col)).toList();
        return !colData.subList(row, map.size()).stream()
            .filter(v -> v.equals("#") || v.equals("@"))
            .toList()
            .isEmpty();
    }

    public boolean checkColGoingNorth(int row, int col) {
        List<String> colData = this.map.stream().map(rowData -> rowData.get(col)).toList();
        return !colData.subList(0, row + 1).stream()
                .filter(v -> v.equals("#") || v.equals("@"))
                .toList()
                .isEmpty();
    }
}
