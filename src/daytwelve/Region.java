package daytwelve;

import util.IntPoint;
import util.Util;

import java.util.HashSet;
import java.util.Optional;

public class Region {
    HashSet<IntPoint> area = new HashSet<>();
    public Region() {
    }

    public boolean contains(IntPoint point) {
        return this.area.contains(point);
    }

    public void assignToRegion(IntPoint current) {
        this.area.add(current);

    }

    public String getId(Map map) {
        Optional<IntPoint> x = this.area.stream().findFirst();
        if (x.isPresent()) {
            return map.get(x.get()).getValue();
        }
        return "?";
    }

    public int getArea() {
        return this.area.size();
    }

    public int getCircumference(Map map) {
        int fences = 0;
        for (IntPoint point : this.area) {
            fences += map.get(point).getFences().size();
        }
        return fences;
    }

    public int getDiscountedCircumference(Map map) {
        int minRow = Integer.MAX_VALUE;
        int maxRow = 0;
        int minCol = Integer.MAX_VALUE;
        int maxCol = 0;
        for (IntPoint point : this.area) {
            if (point.x() < minRow) {
                minRow = point.x();
            }
            if (point.x() > maxRow) {
                maxRow = point.x();
            }
            if (point.y() < minCol) {
                minCol = point.y();
            }
            if (point.y() > maxCol) {
                maxCol = point.y();
            }
        }
        // The region is entirely within this area
        // Check N fences
        int northSides = countNorthSides(map, minRow, maxRow, minCol, maxCol);

        // Check S fences
        int southSides = countSouthSides(map, minRow, maxRow, minCol, maxCol);

        // Check E fences
        int eastSides = countEastSides(map, minRow, maxRow, minCol, maxCol);

        // Check W fences
        int westSides = countWestSides(map, minRow, maxRow, minCol, maxCol);

        Util.debug("Region: " + this.getId(map) + " -  N: " + northSides + " S: " + southSides + " E: " + eastSides + " W: " + westSides);
        return northSides + southSides + eastSides + westSides;
    }

    private int countNorthSides(Map map, int minRow, int maxRow, int minCol, int maxCol) {
        int northSides = 0;
        for (int row = minRow; row <= maxRow; row++) {
            // Find the first value on this row with a north facing fence
            int col = minCol;
            while (col <= maxCol && (!this.contains(new IntPoint(row, col)) || !map.get(new IntPoint(row, col)).getFences().contains("N"))) {
                col++;
            }
            // This is the first point to have a North side on this row.
            int side = 0;
            boolean isBreak = false;
            while (col <= maxCol) {
                if (this.contains(new IntPoint(row, col)) && map.get(new IntPoint(row, col)).getFences().contains("N")) {
                    if (isBreak || side == 0) {
                        // We had a break, so this is a new side.
                        side++;
                        isBreak = false;
                    }
                } else {
                    // Possible break.
                    isBreak = true;
                }
                col++;
            }
            northSides += side;
        }
        return northSides;
    }

    private int countSouthSides(Map map, int minRow, int maxRow, int minCol, int maxCol) {
        int southSides = 0;
        for (int row = minRow; row <= maxRow; row++) {
            // Find the first value on this row with a south facing fence
            int col = minCol;
            while (col <= maxCol && (!this.contains(new IntPoint(row, col)) || !map.get(new IntPoint(row, col)).getFences().contains("S"))) {
                col++;
            }
            // This is the first point to have a South side on this row.
            int side = 0;
            boolean isBreak = false;
            while (col <= maxCol) {
                if (this.contains(new IntPoint(row, col)) && map.get(new IntPoint(row, col)).getFences().contains("S")) {
                    if (isBreak || side == 0) {
                        // We had a break, so this is a new side.
                        side++;
                        isBreak = false;
                    }
                } else {
                    // Possible break.
                    isBreak = true;
                }
                col++;
            }
            southSides += side;
        }
        return southSides;
    }

    private int countEastSides(Map map, int minRow, int maxRow, int minCol, int maxCol) {
        int eastSides = 0;
        for (int col = minCol; col <= maxCol; col++) {
            int row = minRow;
            while (row <= maxRow && (!this.contains(new IntPoint(row, col)) || !map.get(new IntPoint(row, col)).getFences().contains("E"))) {
                row++;
            }
            // This is the first point to have an East side on the row
            int side = 0;
            boolean isBreak = false;
            while (row <= maxRow) {
                if (this.contains(new IntPoint(row, col)) && map.get(new IntPoint(row, col)).getFences().contains("E")) {
                    if (isBreak || side == 0) {
                        side++;
                        isBreak = false;
                    }
                } else {
                    isBreak = true;
                }
                row++;
            }
            eastSides += side;
        }
        return eastSides;
    }

    private int countWestSides(Map map, int minRow, int maxRow, int minCol, int maxCol) {
        int westSides = 0;
        for (int col = minCol; col <= maxCol; col++) {
            int row = minRow;
            while (row <= maxRow && (!this.contains(new IntPoint(row, col)) || !map.get(new IntPoint(row, col)).getFences().contains("W"))) {
                row++;
            }
            // This is the first point to have an East side on the row
            int side = 0;
            boolean isBreak = false;
            while (row <= maxRow) {
                if (this.contains(new IntPoint(row, col)) && map.get(new IntPoint(row, col)).getFences().contains("W")) {
                    if (isBreak || side == 0) {
                        side++;
                        isBreak = false;
                    }
                } else {
                    isBreak = true;
                }
                row++;
            }
            westSides += side;
        }
        return westSides;
    }

}
