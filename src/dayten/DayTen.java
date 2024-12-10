package dayten;

import util.Util;

import java.awt.*;
import java.util.Vector;

public class DayTen {
    public static void main(String[] args) {
        partOne();
        partTwo();
    }

    public static void partTwo() {
        Vector<String> lines = Util.readFile("./src/dayten/input.txt");
        Map map = new Map();
        lines.forEach(map::addRow);

        Vector<IntPoint> trailheads = map.getTrailHeads();
        int total = 0;
        for (IntPoint trailhead : trailheads) {
            total += map.getRatingForTrailhead(trailhead);
        }
        System.out.println(total); // Answer 1511
    }

    public static void partOne() {
        Vector<String> lines = Util.readFile("./src/dayten/input.txt");
        Map map = new Map();
        lines.forEach(map::addRow);

        Vector<IntPoint> trailheads = map.getTrailHeads();
        int total = 0;
        for (IntPoint trailhead : trailheads) {
            total += map.getScoreForTrailhead(trailhead);
        }
        System.out.println(total); // Answer 682
    }
}
