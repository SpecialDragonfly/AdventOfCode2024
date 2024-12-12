package daytwelve;

import util.IntPoint;

import java.util.Stack;
import java.util.Vector;

public class Regions {
    private final Map map;
    private final Vector<Region> regions;

    public Regions(Map map) {
        this.map = map;
        this.regions = new Vector<>();
    }

    public void identifyRegions() {
        // Start at 0, 0.
        // Add value to a TreeMap<Point, Vector<Point>> (new Region)
        // Go N, S, E, W.
        // If the value is the same, add it to the treemap
        for (int row = 0; row < this.map.getWidth(); row++) {
            for (int col = 0; col < this.map.getHeight(); col++) {
                if (this.plotAccountedForInRegions(row, col)) {
                    continue;
                }
                IntPoint first = new IntPoint(row, col);
                Stack<IntPoint> points = new Stack<>();
                Region region = new Region();
                points.push(first);
                while (!points.isEmpty()) {
                    IntPoint point = points.pop();
                    // We've already covered this point, ignore it now.
                    if (region.contains(point)) {
                        continue;
                    }
                    // Assign the point to the region
                    region.assignToRegion(point);

                    GardenPlot plot = this.map.getPlotAt(point.x(), point.y());
                    if (!plot.hasFences()) {
                        // No fences, go in all directions
                        points.push(point.getNorthernPoint());
                        points.push(point.getSouthernPoint());
                        points.push(point.getEasternPoint());
                        points.push(point.getWesternPoint());
                    } else {
                        if (!plot.hasFence("N")) {
                            points.push(point.getNorthernPoint());
                        }
                        if (!plot.hasFence("S")) {
                            points.push(point.getSouthernPoint());
                        }
                        if (!plot.hasFence("E")) {
                            points.push(point.getEasternPoint());
                        }
                        if (!plot.hasFence("W")) {
                            points.push(point.getWesternPoint());
                        }
                    }
                }
                this.regions.add(region);
            }
        }
    }

    public int calculateFenceCost() {
        return this.regions.stream()
            .map(r -> r.getArea() * r.getCircumference(this.map))
            .reduce(0, Integer::sum);
    }

    public int calculateDiscountedFenceCost() {
        return this.regions.stream()
            .map(r -> r.getArea() * r.getDiscountedCircumference(this.map))
            .reduce(0, Integer::sum);
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
}
