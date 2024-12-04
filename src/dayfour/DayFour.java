package dayfour;

import util.FileReader;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayFour {
    public static void main(String[] args) {
        Vector<String> lines = FileReader.readFile("./src/dayfour/input.txt");
        MasWordSearch wordSearch = new MasWordSearch(lines);
        Pattern pattern = Pattern.compile("A");

        int total = 0;
        for (int row = 1; row < lines.size(); row++) {
            Matcher matcher = pattern.matcher(lines.get(row));
            while (matcher.find()) {
                if (wordSearch.check(row, matcher.start())) {
                    total++;
                }
            }
        }
        System.out.println("Total instances: " + total);
    }

    public static void partOne() {
        Vector<String> lines = FileReader.readFile("./src/dayfour/input.txt");
        WordSearch wordSearch = new WordSearch(lines);
        Pattern pattern = Pattern.compile("X");

        int total = 0;
        for (int row = 0; row < lines.size(); row++) {
            Matcher matcher = pattern.matcher(lines.get(row));
            while (matcher.find()) {
                int instances = wordSearch.check(row, matcher.start());
                if (instances > 0) {
                    //System.out.println("On row " + row + " found "+instances+" at " + matcher.start());
                    total += instances;
                }
            }
        }
        System.out.println("Total instances: " + total);
    }
}
