package dayten;

import util.Util;

import java.awt.*;
import java.util.Vector;

public class DayTen {
    public static void main(String[] args) {
        Vector<String> lines = Util.readFile("./src/dayten/input.txt");
        Map map = new Map();
        lines.forEach(map::addRow);

        Vector<Point> trailheads = map.getTrailHeads();
        int total = 0;
        for (Point trailhead : trailheads) {
            total += map.getRatingForTrailhead(trailhead);
        }
        System.out.println(total);
    }

    public static void partOne() {
        Vector<String> lines = Util.readFile("./src/dayten/input.txt");
        Map map = new Map();
        lines.forEach(map::addRow);

        Vector<Point> trailheads = map.getTrailHeads();
        int total = 0;
        for (Point trailhead : trailheads) {
            total += map.getScoreForTrailhead(trailhead);
        }
        System.out.println(total);
    }
}
