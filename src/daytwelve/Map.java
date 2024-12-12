package daytwelve;

import util.IntPoint;
import util.Util;

import java.util.*;

public class Map {
    private final ArrayList<ArrayList<GardenPlot>> map;
    Vector<Region> regions = new Vector<>();

    public Map() {
        this.map = new ArrayList<>();
    }

    public void addRow(String row) {
        ArrayList<GardenPlot> line = new ArrayList<>(Arrays.stream(row.split("")).map(GardenPlot::new).toList());
        this.map.add(line);
    }

    public void assignFences() {
        for (int row = 0; row < this.map.size(); row++) {
            for (int col = 0; col < this.map.get(row).size(); col++) {
                // Check N
                if (this.checkNorth(row, col)) {
                    this.map.get(row).get(col).setFence("N");
                }
                // Check S
                if (this.checkSouth(row, col)) {
                    this.map.get(row).get(col).setFence("S");
                }
                // Check E
                if (this.checkEast(row, col)) {
                    this.map.get(row).get(col).setFence("E");
                }
                // Check W
                if (this.checkWest(row, col)) {
                    this.map.get(row).get(col).setFence("W");
                }
            }
        }
    }

    public void createRegions() {
        // Start at 0, 0.
        // Add value to a TreeMap<Point, Vector<Point>> (new Region)
        // Go N, S, E, W.
        // If the value is the same, add it to the treemap
        for (int row = 0; row < this.map.size(); row++) {
            for (int col = 0; col < this.map.get(row).size(); col++) {
                if (this.plotAccountedForInRegions(row, col)) {
                    continue;
                }
                Region region = new Region();
                IntPoint first = new IntPoint(row, col);
                Stack<IntPoint> points = new Stack<>();
                points.push(first);
                while (!points.isEmpty()) {
                    IntPoint point = points.pop();
                    // We've already covered this point, ignore it now.
                    if (region.contains(point)) {
                        continue;
                    }
                    // Assign the point to the region
                    region.assignToRegion(point);

                    GardenPlot plot = this.map.get(point.x()).get(point.y());
                    if (!plot.hasFences()) {
                        // No fences, go in all directions
                        points.push(point.getNorthernPoint());
                        points.push(point.getSouthernPoint());
                        points.push(point.getEasternPoint());
                        points.push(point.getWesternPoint());
                    } else {
                        ArrayList<String> fences = plot.getFences();
                        if (!fences.contains("N")) {
                            points.push(point.getNorthernPoint());
                        }
                        if (!fences.contains("S")) {
                            points.push(point.getSouthernPoint());
                        }
                        if (!fences.contains("E")) {
                            points.push(point.getEasternPoint());
                        }
                        if (!fences.contains("W")) {
                            points.push(point.getWesternPoint());
                        }
                    }
                }
                this.regions.add(region);
            }
        }
    }

    public int calculateFenceCost() {
        // At this point, all points on the map should be accounted for.
        int total = 0;
        for (Region region : this.regions) {
            System.out.println(region.getId(this) + ": " + region.getArea() * region.getCircumference(this));
            total += (region.getArea() * region.getCircumference(this));
        }
        return total;
    }

    public int calculateDiscountedFenceCost() {
        // At this point, all points on the map should be accounted for.
        int total = 0;
        for (Region region : this.regions) {
            int area = region.getArea();
            int discountedCircumference = region.getDiscountedCircumference(this);
            total += (area * discountedCircumference);
            Util.debug(region.getId(this) + ": (" + area + " * " + discountedCircumference + ") = " + area * discountedCircumference);
        }
        return total;
    }

    private boolean plotAccountedForInRegions(int row, int col) {
        if (this.regions.isEmpty()) {
            return false;
        }
        IntPoint point = new IntPoint(row, col);
        for (Region region : this.regions) {
            if (region.contains(point)) {
                return true;
            }
        }
        return false;
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

    public GardenPlot get(IntPoint point) {
        return this.map.get(point.x()).get(point.y());
    }
}
