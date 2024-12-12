package daytwelve;

import util.Util;

import java.util.Vector;

public class DayTwelve {
    public static void main(String[] args) {
        Vector<String> lines = Util.readFile("./src/daytwelve/input.txt");
        Map map = new Map();
        lines.forEach(map::addRow);

        // foreach cell, check N, S, E, W for whether it's next to another of the same value.
        // if it's not, place a fence.
        map.assignFences();

        Regions regions = new Regions(map);
        regions.identifyRegions();

        int total = regions.calculateFenceCost();
        System.out.println("Part 1: " + total);

        int discountedTotal = regions.calculateDiscountedFenceCost();
        System.out.println("Part 2: " + discountedTotal);
    }
}
