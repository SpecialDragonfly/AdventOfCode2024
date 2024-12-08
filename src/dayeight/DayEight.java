package dayeight;

import util.Util;

import java.util.Vector;

public class DayEight {
    public static void main(String[] args) {
        partOne();
        partTwo();
    }

    public static void partOne() {
        Vector<String> lines = Util.readFile("./src/dayeight/input.txt");
        Map map = new Map();
        lines.forEach(map::addRow);

        map.findAntinodes();
        map.outputAntinodes();
        System.out.println("Unique: " + map.getAntinodes().size());
    }

    public static void partTwo() {
        Vector<String> lines = Util.readFile("./src/dayeight/input.txt");
        Map map = new Map();
        lines.forEach(map::addRow);

        map.findAntinodesPartTwo();
        map.outputAntinodes();
        System.out.println("Unique: " + map.getAntinodes().size());
    }
}
