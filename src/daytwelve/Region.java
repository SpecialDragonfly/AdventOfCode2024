package daytwelve;

import util.IntPoint;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Region {
    private final HashSet<IntPoint> area = new HashSet<>();
    public Region() {
    }

    public boolean contains(IntPoint point) {
        return this.area.contains(point);
    }

    public void assignToRegion(IntPoint current) {
        this.area.add(current);
    }

    public int getArea() {
        return this.area.size();
    }

    public int getCircumference(Map map) {
        return this.area.stream()
            .map(p -> map.getPlotAt(p).getFences().size())
            .reduce(0, Integer::sum);
    }

    public int getDiscountedCircumference(Map map) {
        List<Integer> extremes = this.area.stream()
            .collect(Collectors.teeing(
                Collectors.reducing((p1, p2) -> p1.getX() < p2.getX() ? p1 : p2), // Smallest x
                Collectors.reducing((p1, p2) -> p1.getY() < p2.getY() ? p1 : p2), // Smallest y
                (minXPoint, minYPoint) -> List.of(
                    minXPoint.orElseThrow().getX(),
                    minYPoint.orElseThrow().getY(),
                    this.area.stream().max(Comparator.comparingInt(IntPoint::getX)).orElseThrow().getX(),
                    this.area.stream().max(Comparator.comparingInt(IntPoint::getY)).orElseThrow().getY()
                )
            ));
        int minRow = extremes.get(0);
        int minCol = extremes.get(1);
        int maxRow = extremes.get(2);
        int maxCol = extremes.get(3);

        // The region is entirely within this area
        // Check N fences
        int northSides = countNorthSides(map, minRow, maxRow, minCol, maxCol);

        // Check S fences
        int southSides = countSouthSides(map, minRow, maxRow, minCol, maxCol);

        // Check E fences
        int eastSides = countEastSides(map, minRow, maxRow, minCol, maxCol);

        // Check W fences
        int westSides = countWestSides(map, minRow, maxRow, minCol, maxCol);

        return northSides + southSides + eastSides + westSides;
    }

    private int countNorthSides(Map map, int minRow, int maxRow, int minCol, int maxCol) {
        int northSides = 0;
        for (int row = minRow; row <= maxRow; row++) {
            // Find the first value on this row with a north facing fence
            int col = minCol;
            while (col <= maxCol && (!this.contains(new IntPoint(row, col)) || !map.getPlotAt(row, col).hasFence("N"))) {
                col++;
            }
            // This is the first point to have a North side on this row.
            int side = 0;
            boolean isBreak = false;
            while (col <= maxCol) {
                if (this.contains(new IntPoint(row, col)) && map.getPlotAt(row, col).hasFence("N")) {
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
            while (col <= maxCol && (!this.contains(new IntPoint(row, col)) || !map.getPlotAt(row, col).hasFence("S"))) {
                col++;
            }
            // This is the first point to have a South side on this row.
            int side = 0;
            boolean isBreak = false;
            while (col <= maxCol) {
                if (this.contains(new IntPoint(row, col)) && map.getPlotAt(row, col).hasFence("S")) {
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
            while (row <= maxRow && (!this.contains(new IntPoint(row, col)) || !map.getPlotAt(row, col).hasFence("E"))) {
                row++;
            }
            // This is the first point to have an East side on the row
            int side = 0;
            boolean isBreak = false;
            while (row <= maxRow) {
                if (this.contains(new IntPoint(row, col)) && map.getPlotAt(row, col).hasFence("E")) {
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
            while (row <= maxRow && (!this.contains(new IntPoint(row, col)) || !map.getPlotAt(row, col).hasFence("W"))) {
                row++;
            }
            // This is the first point to have a West side on the row
            int side = 0;
            boolean isBreak = false;
            while (row <= maxRow) {
                if (this.contains(new IntPoint(row, col)) && map.getPlotAt(row, col).hasFence("W")) {
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
