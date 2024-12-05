package daythree;

import util.Util;

import java.util.Vector;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static Pattern dontToDo = Pattern.compile("don't\\(\\).*?do\\(\\)", Pattern.CASE_INSENSITIVE);
    static Pattern dontToEnd = Pattern.compile("don't\\(\\).*$", Pattern.CASE_INSENSITIVE);
    static Pattern mulPattern = Pattern.compile("mul\\(\\d+,\\d+\\)", Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) {
        Main.partOne();
        Main.partTwo();
    }

    public static void partTwo() {
        // The input for this one carries over from one line to the next, so we just want one line.
        String line = Util.getFileAsLine("./src/daythree/input.txt");

        // Replace everything between don't and do with the positive do().
        Matcher matcher = dontToDo.matcher(line);
        line = matcher.replaceAll("do()");

        // We now have at most 1 don't, right at the end, so replace from that to the end with nothing.
        matcher = dontToEnd.matcher(line);
        line = matcher.replaceAll("");

        matcher = mulPattern.matcher(line);

        int total = 0;
        while (matcher.find()) {
            MatchResult result = matcher.toMatchResult();
            // Strip mul( off the front and ) off the back
            String[] parts = line.substring(result.start() + 4, result.end() - 1).split(",");
            total += Integer.parseInt(parts[0]) * Integer.parseInt(parts[1]);
        }

        System.out.println("Total: " + total); // Correct: 80747545
    }

    public static void partOne() {
        Vector<String> lines = Util.readFile("./src/daythree/input.txt");
        int reduced = lines.stream().map(line -> {
            Pattern pattern = Pattern.compile("mul\\(\\d+,\\d+\\)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(line);
            int total = 0;
            while (matcher.find()) {
                MatchResult result = matcher.toMatchResult();
                // Strip mul( off the front and ) off the back
                String[] parts = line.substring(result.start() + 4, result.end() - 1).split(",");
                total += Integer.parseInt(parts[0]) * Integer.parseInt(parts[1]);
            }
            return total;
        }).reduce(0, Integer::sum);
        System.out.println("Part 1 Total: " + reduced);
    }
}
