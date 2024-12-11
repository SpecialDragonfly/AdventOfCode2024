package dayeleven;

import util.Util;

import java.util.*;

public class DayEleven {
    public static void main(String[] args) {
        String line = Util.getFileAsLine("./src/dayeleven/input.txt");
        List<Stone> stones = Arrays.stream(line.split(" ")).map(value -> new Stone(Long.valueOf(value))).toList();
        HashMap<Long, Stone> stoneCache = new HashMap<>();

        partOne(stones, 25, stoneCache);
        partTwo(stones, 75, stoneCache);
    }

    public static void partTwo(List<Stone> stones, int times, HashMap<Long, Stone> stoneCache) {
        // Set up initial values
        HashMap<Long, Long> stoneCount = new HashMap<>();
        for (Stone stone : stones) {
            if (!stoneCount.containsKey(stone.getValue())) {
                stoneCount.put(stone.getValue(), 0L);
            }
            stoneCount.merge(stone.getValue(), 1L, Long::sum);
        }

        // Foreach blink
        for (int i = 0; i < times; i++) {
            HashMap<Long, Long> newStoneCount = new HashMap<>();
            // Iterate through unique stonecount values
            for (Map.Entry<Long, Long> entry: stoneCount.entrySet()) {
                Long stoneValue = entry.getKey();
                if (!stoneCache.containsKey(stoneValue)) {
                    Stone stone = new Stone(stoneValue);
                    stone.blink();
                    stoneCache.put(stoneValue, stone);
                }
                Vector<Stone> children = stoneCache.get(stoneValue).getChildren();
                for (Stone child : children) {
                    newStoneCount.merge(child.getValue(), entry.getValue(), Long::sum);
                }
            }
            // These are the new stones, and their associated quantities.
            stoneCount = newStoneCount;
        }

        System.out.println("Total: " + stoneCount.values().stream().reduce(0L, Long::sum));
    }

    public static void partOne(List<Stone> stones, int times, HashMap<Long, Stone> stoneCache) {
        // Set up initial values
        HashMap<Long, Long> stoneCount = new HashMap<>();
        for (Stone stone : stones) {
            if (!stoneCount.containsKey(stone.getValue())) {
                stoneCount.put(stone.getValue(), 0L);
            }
            stoneCount.merge(stone.getValue(), 1L, Long::sum);
        }

        // Foreach blink
        for (int i = 0; i < times; i++) {
            HashMap<Long, Long> newStoneCount = new HashMap<>();
            // Iterate through unique stonecount values
            for (Map.Entry<Long, Long> entry: stoneCount.entrySet()) {
                Long stoneValue = entry.getKey();
                if (!stoneCache.containsKey(stoneValue)) {
                    Stone stone = new Stone(stoneValue);
                    stone.blink();
                    stoneCache.put(stoneValue, stone);
                }
                Vector<Stone> children = stoneCache.get(stoneValue).getChildren();
                for (Stone child : children) {
                    newStoneCount.merge(child.getValue(), 1L, Long::sum);
                }
            }
            // These are the new stones, and their associated quantities.
            stoneCount = newStoneCount;
        }

        System.out.println("Total: " + stoneCount.values().stream().reduce(0L, Long::sum));
    }
}
