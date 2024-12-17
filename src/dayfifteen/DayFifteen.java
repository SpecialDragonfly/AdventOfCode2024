package dayfifteen;

import util.Util;

import java.util.Vector;

public class DayFifteen {
    public static void main(String[] args) {
        Vector<String> lines = Util.readFile("./src/dayfifteen/wideinput.txt");
        WideMap map = new WideMap();
        StringBuilder directions = new StringBuilder();
        int i = 0;
        while (i < lines.size()) {
            String line = lines.get(i);
            if (!line.isEmpty()) {
                map.addRow(line);
            } else {
                break;
            }
            i++;
        }
        while (i < lines.size()) {
            directions.append(lines.get(i));
            i++;
        }
        map.output();
        for (i = 0; i < directions.length(); i++) {
            String direction = directions.charAt(i) + "";
            map.moveRobot(direction);
        }
        map.output();
        System.out.println("Total: " + map.getGPSValue());
        // Answer: 1437981
    }

    public static void partOne() {
        Vector<String> lines = Util.readFile("./src/dayfifteen/input.txt");
        Map map = new Map();
        StringBuilder directions = new StringBuilder();
        int i = 0;
        while (i < lines.size()) {
            String line = lines.get(i);
            if (!line.isEmpty()) {
                map.addRow(line);
            } else {
                break;
            }
            i++;
        }
        while (i < lines.size()) {
            directions.append(lines.get(i));
            i++;
        }
        for (i = 0; i < directions.length(); i++) {
            String direction = directions.charAt(i) + "";
            map.moveRobot(direction);
        }
        System.out.println("Total: " + map.getGPSValue());
    }
}
