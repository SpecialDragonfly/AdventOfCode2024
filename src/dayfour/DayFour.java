package dayfour;

import util.Util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayFour {
    public static void main(String[] args) {
        partOne(Util.readFile("./src/dayfour/input.txt"));
        partTwo(Util.readFile("./src/dayfour/input.txt"));
    }

    private static void partTwo(List<String> lines) {
        MasWordSearch wordSearch = new MasWordSearch(lines);
        Pattern pattern = Pattern.compile("A");

        int total = 0;
        // We skip the first and last row because we're looking for a cross. If it's an edge, there can't be a cross.
        for (int row = 1; row < lines.size() - 1; row++) {
            Matcher matcher = pattern.matcher(lines.get(row));
            while (matcher.find()) {
                if (wordSearch.check(row, matcher.start())) {
                    total++;
                }
            }
        }
        System.out.println("Total instances: " + total); // 1972
    }

    private static void partOne(List<String> lines) {
        WordSearch wordSearch = new WordSearch(lines);
        Pattern pattern = Pattern.compile("X");

        int total = 0;
        for (int row = 0; row < lines.size(); row++) {
            Matcher matcher = pattern.matcher(lines.get(row));
            while (matcher.find()) {
                total += wordSearch.check(row, matcher.start());
            }
        }
        System.out.println("Total instances: " + total); // 2578
    }
}
