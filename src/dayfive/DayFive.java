package dayfive;

import util.Util;

import java.util.*;

public class DayFive {
    public static void main(String[] args) {
        Vector<String> lines = Util.readFile("./src/dayfive/input.txt");
        Vector<String> ruleValues = new Vector<>();

        while (!lines.getFirst().isEmpty()) {
            String line = lines.removeFirst();
            ruleValues.add(line);
        }
        lines.removeFirst(); // Remove the blank line.
        Rules rules = new Rules(ruleValues);

        Vector<PrintRun> input = new Vector<>();

        while (!lines.isEmpty()) {
            String line = lines.removeFirst();
            input.add(new PrintRun(rules, new ArrayList<>(Arrays.stream(line.split(",")).toList())));
        }

        partOne(input);
        partTwo(input);
    }

    // Count the middle number from just the valid print runs.
    public static void partOne(Vector<PrintRun> input) {
        int total = input.stream().map(pr -> pr.valid() ? pr.middleNumber() : 0).reduce(0, Integer::sum);
        System.out.println("Total: " + total); // 5964
    }

    // Count the middle number from just the invalid print runs, after fixing them.
    public static void partTwo(Vector<PrintRun> input) {
        int total = input.stream().map(pr -> {
            if (pr.valid()) {
                return 0;
            }
            pr.order();
            return pr.middleNumber();
        }).reduce(0, Integer::sum);
        System.out.println("Total: " + total); // 4719
    }
}
