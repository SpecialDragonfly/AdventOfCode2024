package daytwelve;

import util.Util;

import java.util.Vector;

public class DayTwelve {
    public static void main(String[] args) {
        Util.debug = false;
        Vector<String> lines = Util.readFile("./src/daytwelve/input.txt");
        Map map = new Map();
        lines.forEach(map::addRow);

        // foreach cell, check N, S, E, W for whether it's next to another of the same value.
        // if it's not, place a fence.
        map.assignFences();

        map.createRegions();

        int total = map.calculateFenceCost();
        System.out.println("Total: " + total);

        int discountedTotal = map.calculateDiscountedFenceCost();
        System.out.println("Total: " + discountedTotal);
    }
}
