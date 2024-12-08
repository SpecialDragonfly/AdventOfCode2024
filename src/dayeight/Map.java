package dayeight;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Map {
    private final ArrayList<ArrayList<String>> map;
    private final List<Integer[]> antinodes = new ArrayList<>();
    private final HashMap<String, Vector<Integer[]>> antennaTypes;

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
            this.antennaTypes.get(type).add(new Integer[]{this.map.size() - 1, matcher.start()});
        }
    }

    public void findAntinodes() {
        // Go through the antenna types
        for (String type : this.antennaTypes.keySet()) {
            // Get the antenna locations according to type:
            Vector<Integer[]> locations = this.antennaTypes.get(type);
            if (locations.size() == 1) {
                continue;
            }
            int mapRowSize = this.map.size();
            int mapColSize = this.map.getFirst().size();
            for (int i = 0; i < locations.size(); i++) {
                for (int j = i + 1; j < locations.size(); j++) {
                    Integer[] first = locations.get(i);
                    Integer[] second = locations.get(j);

                    int rowDiff = Math.abs(first[0] - second[0]);
                    int colDiff = Math.abs(first[1] - second[1]);

                    // if they're NW - SE of each other then it's:
                    // first[0] - row-diff, first[1] - col-diff
                    // second[0] + row-diff, second[1] + col-diff
                    if (first[1] <= second[1]) {
                        if (first[0] - rowDiff >= 0 && first[1] - colDiff >= 0) {
                            this.antinodes.add(new Integer[]{first[0] - rowDiff, first[1] - colDiff});
                        }
                        if (second[0] + rowDiff < mapRowSize && second[1] + colDiff < mapColSize) {
                            this.antinodes.add(new Integer[]{second[0] + rowDiff, second[1] + colDiff});
                        }
                    }

                    // if they're NE - SW of each other then the first one will be the NE one
                    // first[0] - row-diff, first[1] + col-diff
                    // second[0] + row-diff, second[1] - col-diff
                    if (first[1] > second[1]) {
                        if (first[0] - rowDiff >= 0 && first[1] + colDiff < mapColSize) {
                            this.antinodes.add(new Integer[]{first[0] - rowDiff, first[1] + colDiff});
                        }
                        if (second[0] + rowDiff < mapRowSize && second[1] - colDiff >= 0) {
                            this.antinodes.add(new Integer[]{second[0] + rowDiff, second[1] - colDiff});
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
            Vector<Integer[]> locations = this.antennaTypes.get(type);
            if (locations.size() == 1) {
                continue;
            }
            int mapRowSize = this.map.size();
            int mapColSize = this.map.getFirst().size();
            for (int i = 0; i < locations.size(); i++) {
                for (int j = i + 1; j < locations.size(); j++) {
                    Integer[] first = locations.get(i);
                    Integer[] second = locations.get(j);

                    int rowDiff = Math.abs(first[0] - second[0]);
                    int colDiff = Math.abs(first[1] - second[1]);

                    // if they're NW - SE of each other then it's:
                    // first[0] - row-diff, first[1] - col-diff
                    // second[0] + row-diff, second[1] + col-diff
                    if (first[1] <= second[1]) {
                        int tmpFirstRow = first[0];
                        int tmpFirstCol = first[1];
                        while (tmpFirstRow - rowDiff >= 0 && tmpFirstCol - colDiff >= 0) {
                            this.antinodes.add(new Integer[]{tmpFirstRow - rowDiff, tmpFirstCol - colDiff});
                            tmpFirstRow -= rowDiff;
                            tmpFirstCol -= colDiff;
                        }
                        int tmpSecondRow = second[0];
                        int tmpSecondCol = second[1];
                        while (tmpSecondRow + rowDiff < mapRowSize && tmpSecondCol + colDiff < mapColSize) {
                            this.antinodes.add(new Integer[]{tmpSecondRow + rowDiff, tmpSecondCol + colDiff});
                            tmpSecondRow += rowDiff;
                            tmpSecondCol += colDiff;
                        }
                    } else {
                        // if they're NE - SW of each other then the first one will be the NE one
                        // first[0] - row-diff, first[1] + col-diff
                        // second[0] + row-diff, second[1] - col-diff
                        int tmpFirstRow = first[0];
                        int tmpFirstCol = first[1];
                        while (tmpFirstRow - rowDiff >= 0 && tmpFirstCol + colDiff < mapColSize) {
                            this.antinodes.add(new Integer[]{tmpFirstRow - rowDiff, tmpFirstCol + colDiff});
                            tmpFirstRow -= rowDiff;
                            tmpFirstCol += colDiff;
                        }
                        int tmpSecondRow = second[0];
                        int tmpSecondCol = second[1];
                        while (tmpSecondRow + rowDiff < mapRowSize && tmpSecondCol - colDiff >= 0) {
                            this.antinodes.add(new Integer[]{tmpSecondRow + rowDiff, tmpSecondCol - colDiff});
                            tmpSecondRow += rowDiff;
                            tmpSecondCol -= colDiff;
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
        Set<String> distinct = this.getAntinodes();
        for (int i = 0; i < this.map.size(); i++) {
            for (int j = 0; j < this.map.getFirst().size(); j++) {
                if (!this.map.get(i).get(j).equals(".")) {
                    System.out.print(this.map.get(i).get(j));
                } else {
                    if (distinct.contains(i + " " + j)) {
                        System.out.print("#");
                    } else {
                        System.out.print(".");
                    }
                }
            }
            System.out.print("\n");
        }
    }

    public Set<String> getAntinodes() {
        Set<String> distinct = new HashSet<>(this.antinodes.size());
        this.antinodes.forEach(coord -> distinct.add(coord[0] + " " + coord[1]));
        return distinct;
    }
}
