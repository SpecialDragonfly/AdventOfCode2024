package dayone;

import util.Util;

import java.util.*;

public class DayOne {
    public static void main(String[] args) {
        DayOne.partOne();
        DayOne.partTwo();
    }

    public static void partTwo() {
        Vector<String> lines = Util.readFile("./src/dayone/input.txt");
        List<Integer> first = new Vector<>();
        Map<Integer, Integer> occurrences = new HashMap<>();
        lines.forEach(s -> {
            String[] parts = s.split(" {3}");
            first.add(Integer.parseInt(parts[0]));
            int second = Integer.parseInt(parts[1]);
            Integer value = occurrences.putIfAbsent(second, 1);
            if (value != null) {
                occurrences.put(second, value + 1);
            }
        });

        int total = 0;
        for (int value : first) {
            if (occurrences.containsKey(value)) {
                total += value * occurrences.get(value);
            }
        }
        System.out.println("Total : " + total); // 21142653
    }

    public static void partOne() {
        Vector<String> lines = Util.readFile("./src/dayone/input.txt");
        int total = 0;
        List<Integer> first = new Vector<>();
        List<Integer> second = new Vector<>();
        lines.forEach(s -> {
            String[] parts = s.split(" {3}");
            first.add(Integer.parseInt(parts[0]));
            second.add(Integer.parseInt(parts[1]));
        });
        Collections.sort(first);
        Collections.sort(second);

        if (first.size() != second.size()) {
            System.out.println("Sizes different");
            return;
        }

        for (int i = 0; i < first.size(); i++) {
            total += Math.abs(second.get(i) - first.get(i));
        }

        System.out.println("total: " + total); // Answer: 2285373
    }
}
