package daytwelve;

import util.IntPoint;

import java.util.*;

public class Map {
    private final ArrayList<ArrayList<GardenPlot>> map;

    public Map() {
        this.map = new ArrayList<>();
    }

    public int getWidth() {
        return this.map.size();
    }

    public int getHeight() {
        return this.map.getFirst().size();
    }

    public void addRow(String row) {
        ArrayList<GardenPlot> line = new ArrayList<>(Arrays.stream(row.split("")).map(GardenPlot::new).toList());
        this.map.add(line);
    }

    public GardenPlot getPlotAt(int x, int y) {
        return this.map.get(x).get(y);
    }

    public GardenPlot getPlotAt(IntPoint point) {
        return this.getPlotAt(point.x(), point.y());
    }

    public void assignFences() {
        for (int row = 0; row < this.map.size(); row++) {
            for (int col = 0; col < this.map.get(row).size(); col++) {
                // Check N
                if (this.checkNorth(row, col)) {
                    this.getPlotAt(row, col).setFence("N");
                }
                // Check S
                if (this.checkSouth(row, col)) {
                    this.getPlotAt(row, col).setFence("S");
                }
                // Check E
                if (this.checkEast(row, col)) {
                    this.getPlotAt(row, col).setFence("E");
                }
                // Check W
                if (this.checkWest(row, col)) {
                    this.getPlotAt(row, col).setFence("W");
                }
            }
        }
    }

    private boolean checkNorth(int row, int col) {
        if (row == 0) {
            return true;
        }
        return !this.map.get(row - 1).get(col).equals(this.map.get(row).get(col));
    }

    private boolean checkSouth(int row, int col) {
        if (row == this.map.size() - 1) {
            return true;
        }
        return !this.map.get(row + 1).get(col).equals(this.map.get(row).get(col));
    }

    private boolean checkEast(int row, int col) {
        if (col == this.map.getFirst().size() - 1) {
            return true;
        }
        return !this.map.get(row).get(col).equals(this.map.get(row).get(col + 1));
    }

    private boolean checkWest(int row, int col) {
        if (col == 0) {
            return true;
        }
        return !this.map.get(row).get(col).equals(this.map.get(row).get(col - 1));
    }
}
