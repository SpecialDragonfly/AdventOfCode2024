package dayeight;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Map {
    private final ArrayList<ArrayList<String>> map;
    private final List<Point> antinodes = new ArrayList<>();
    private final HashMap<String, Vector<Point>> antennaTypes;

    public Map() {
        this.map = new ArrayList<>();
        this.antennaTypes = new HashMap<>();
    }

    public void addRow(String row) {
        ArrayList<String> line = new ArrayList<>(Arrays.stream(row.split("")).toList());
        this.map.add(line);
        Pattern pattern = Pattern.compile("[A-Za-z0-9]");
        Matcher matcher = pattern.matcher(row);
        while (matcher.find()) {
            String type = String.valueOf(row.charAt(matcher.start()));
            if (!this.antennaTypes.containsKey(type)) {
                this.antennaTypes.put(type, new Vector<>());
            }
            this.antennaTypes.get(type).add(new Point(this.map.size() - 1, matcher.start()));
        }
    }

    public void findAntinodes() {
        // Go through the antenna types
        for (String type : this.antennaTypes.keySet()) {
            // Get the antenna locations according to type:
            Vector<Point> locations = this.antennaTypes.get(type);
            if (locations.size() == 1) {
                continue;
            }
            int mapRowSize = this.map.size();
            int mapColSize = this.map.getFirst().size();
            for (int i = 0; i < locations.size(); i++) {
                for (int j = i + 1; j < locations.size(); j++) {
                    Point first = locations.get(i);
                    Point second = locations.get(j);

                    int rowDiff = (int) Math.abs(first.getX() - second.getX());
                    int colDiff = (int) Math.abs(first.getY() - second.getY());

                    // if they're NW - SE of each other, then it's:
                    // first[0] - row-diff, first[1] - col-diff
                    // second[0] + row-diff, second[1] + col-diff
                    if (first.getY() <= second.getY()) {
                        if (first.getX() - rowDiff >= 0 && first.getY() - colDiff >= 0) {
                            this.antinodes.add(new Point((int) (first.getX() - rowDiff), (int) (first.getY() - colDiff)));
                        }
                        if (second.getX() + rowDiff < mapRowSize && second.getY() + colDiff < mapColSize) {
                            this.antinodes.add(new Point((int) (second.getX() + rowDiff), (int) (second.getY() + colDiff)));
                        }
                    } else {
                        // if they're NE - SW of each other, then it's
                        // first[0] - row-diff, first[1] + col-diff
                        // second[0] + row-diff, second[1] - col-diff
                        if (first.getX() - rowDiff >= 0 && first.getY() + colDiff < mapColSize) {
                            this.antinodes.add(new Point((int) (first.getX() - rowDiff), (int) (first.getY() + colDiff)));
                        }
                        if (second.getX() + rowDiff < mapRowSize && second.getY() - colDiff >= 0) {
                            this.antinodes.add(new Point((int) (second.getX() + rowDiff), (int) (second.getY() - colDiff)));
                        }
                    }
                }
            }
        }
    }

    public void findAntinodesPartTwo() {
        // Go through the antenna types
        for (String type : this.antennaTypes.keySet()) {
            // Get the antenna locations according to type:
            Vector<Point> locations = this.antennaTypes.get(type);
            if (locations.size() == 1) {
                continue;
            }
            int mapRowSize = this.map.size();
            int mapColSize = this.map.getFirst().size();
            for (int i = 0; i < locations.size(); i++) {
                Point first = locations.get(i);
                for (int j = i + 1; j < locations.size(); j++) {
                    Point second = locations.get(j);

                    int rowDiff = (int) Math.abs(first.getX() - second.getX());
                    int colDiff = (int) Math.abs(first.getY() - second.getY());

                    // if they're NW - SE of each other, then it's:
                    // first[0] - row-diff, first[1] - col-diff
                    // second[0] + row-diff, second[1] + col-diff
                    int count = 1;
                    if (first.getY() <= second.getY()) {
                        while (first.getX() - (rowDiff * count) >= 0 && first.getY() - (colDiff * count) >= 0) {
                            this.antinodes.add(new Point((int) (first.getX() - (rowDiff * count)), (int) (first.getY() - (colDiff* count))));
                            count++;
                        }
                        count = 1;
                        while (second.getX() + (rowDiff * count) < mapRowSize && second.getY() + (colDiff * count) < mapColSize) {
                            this.antinodes.add(new Point((int) (second.getX() + (rowDiff * count)), (int) (second.getY() + (colDiff * count))));
                            count++;
                        }
                    } else {
                        // if they're NE - SW of each other, then it's:
                        // first[0] - row-diff, first[1] + col-diff
                        // second[0] + row-diff, second[1] - col-diff
                        while (first.getX() - (rowDiff*count) >= 0 && first.getY() + (colDiff*count) < mapColSize) {
                            this.antinodes.add(new Point((int) (first.getX() - (rowDiff*count)), (int) (first.getY() + (colDiff*count))));
                            count++;
                        }
                        count = 1;
                        while (second.getX() + (rowDiff*count) < mapRowSize && second.getY() - (colDiff*count) >= 0) {
                            this.antinodes.add(new Point((int) (second.getX() + (rowDiff*count)), (int) (second.getY() - (colDiff*count))));
                            count++;
                        }
                    }
                }
            }
            if (locations.size() >= 2) {
                // All antenna are in line with 2 other antenna, so they're all antinodes too
                this.antinodes.addAll(locations);
            }
        }
    }

    public void outputAntinodes() {
        Set<Point> distinct = this.getAntinodes();
        for (int i = 0; i < this.map.size(); i++) {
            for (int j = 0; j < this.map.getFirst().size(); j++) {
                if (!this.map.get(i).get(j).equals(".")) {
                    System.out.print(this.map.get(i).get(j));
                } else {
                    if (distinct.contains(new Point(i, j))) {
                        System.out.print("#");
                    } else {
                        System.out.print(".");
                    }
                }
            }
            System.out.print("\n");
        }
    }

    /**
     * Dump non-unique list items into a set. Now items are unique. Return unique list.
     */
    public Set<Point> getAntinodes() {
        Set<Point> distinct = new HashSet<>(this.antinodes.size());
        distinct.addAll(this.antinodes);
        return distinct;
    }
}
