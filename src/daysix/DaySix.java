package daysix;

import util.Util;

import java.util.List;
import java.util.Vector;

public class DaySix {
    public static void main(String[] args) {
        partOne();
        partTwo();
    }

    public static void partTwo() {
        Vector<String> lines = Util.readFile("./src/daysix/input.txt");
        Map map = new Map();
        lines.forEach(map::addRow);
        Integer[] guardPosition = map.getGuard();
        Guard guard = new Guard(guardPosition[0], guardPosition[1], map.valueAt(guardPosition[0], guardPosition[1]));

        while (true) {
            try {
                guard.move(map);
            } catch (GuardLeft e) {
                break;
            } catch (InfiniteLoop e) {
                System.out.println("Shouldn't have found one...");
            }
        }

        List<String> movementHistory = guard.getMovementHistory().stream()
            .filter(v -> !v.equals(guardPosition[0] + ":" + guardPosition[1]))
            .toList();

        // There are X positions where we might want to put an obstacle,
        // Add a new obstacle to the map and then rerun the simulation
        int totalInfiniteLoops = 0;
        for (String s : movementHistory) {
            String[] parts = s.split(":");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);

            map.addObstacle(row, col);
            guard = new Guard(guardPosition[0], guardPosition[1], map.valueAt(guardPosition[0], guardPosition[1]));
            while (true) {
                try {
                    guard.move(map);
                } catch (GuardLeft e) {
                    break;
                } catch (InfiniteLoop e) {
                    totalInfiniteLoops++;
                    break;
                }
            }
            map.removeObstacle(row, col);
        }
        System.out.println("Total Infinite Loops: " + totalInfiniteLoops);
    }

    public static void partOne() {
        Vector<String> lines = Util.readFile("./src/daysix/input.txt");
        Map map = new Map();
        lines.forEach(map::addRow);
        Integer[] guardPosition = map.getGuard();
        Guard guard = new Guard(guardPosition[0], guardPosition[1], map.valueAt(guardPosition[0], guardPosition[1]));

        while (true) {
            try {
                guard.move(map);
            } catch (GuardLeft | InfiniteLoop e) {
                break;
            }
        }

        System.out.println("Total: " + guard.numberOfSteps());
    }
}
