package daysix;

import util.Util;

import java.util.HashMap;
import java.util.Vector;

public class DaySix {
    public static void main(String[] args) {
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
        System.out.println("Part One: " + guard.numberOfSteps());

        HashMap<String, Vector<String>> movementHistory = guard.getMovementHistory();

        // There are X positions where we might want to put an obstacle,
        // Add a new obstacle to the map and then rerun the simulation
        int totalInfiniteLoops = 0;
        int skipCount = 0;
        for (String s : movementHistory.keySet()) {
            String[] parts = s.split(":");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            if (row == guardPosition[0] && col == guardPosition[1]) {
                continue;
            }

            // should we add an obstacle here?
            // we need to know which way the guard was going when they might interact with the obstacle
            // once we know that, we know that if they turned right, is there a block for them to bounce off?
            // if not, we needn't put an obstacle here.
            Vector<String> directions = movementHistory.get(s);
            boolean placeObstacle = false;
            for (String direction : directions) {
                if (direction.equals("+")) {
                    // don't do anything, we can't be sure where they're coming from.
                }
                if (direction.equals("^")) {
                    placeObstacle = placeObstacle || map.checkRowGoingEast(row + 1, col);
                }
                if (direction.equals(">")) {
                    placeObstacle = placeObstacle || map.checkColGoingSouth(row, col - 1);
                }
                if (direction.equals("V")) {
                    placeObstacle = placeObstacle || map.checkRowGoingWest(row - 1, col);
                }
                if (direction.equals("<")) {
                    placeObstacle = placeObstacle || map.checkColGoingNorth(row, col + 1);
                }
            }
            if (!placeObstacle) {
                skipCount++;
                continue;
            }

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
        System.out.println("Part Two: Total Infinite Loops: " + totalInfiniteLoops + " Skipping: " + skipCount);
    }
}
