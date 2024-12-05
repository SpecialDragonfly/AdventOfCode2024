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

        partOne(rules, new Vector<>(lines));
        partTwo(rules, new Vector<>(lines));
    }

    // Count up just the valid print runs.
    public static void partOne(Rules rules, Vector<String> lines) {
        Vector<PrintRun> input = new Vector<>();

        while (!lines.isEmpty()) {
            String line = lines.removeFirst();
            input.add(new PrintRun(rules, new ArrayList<>(Arrays.stream(line.split(",")).toList())));
        }

        int total = input.stream().map(pr -> pr.valid() ? pr.middleNumber() : 0).reduce(0, Integer::sum);
        System.out.println("Total: " + total); // 5964
    }

    // Count the invalid print runs
    public static void partTwo(Rules rules, Vector<String> lines) {
        Vector<PrintRun> input = new Vector<>();

        while (!lines.isEmpty()) {
            String line = lines.removeFirst();
            input.add(new PrintRun(rules, new ArrayList<>(Arrays.stream(line.split(",")).toList())));
        }

        int total = input.stream().map(pr -> !pr.valid() ? pr.middleNumber() : 0).reduce(0, Integer::sum);
        System.out.println("Total: " + total); // 4719
    }
}
