package dayfour;

import java.util.List;
import java.util.Vector;

public class MasWordSearch {
    private final List<String> lines;

    public MasWordSearch(List<String> lines) {
        this.lines = lines;
    }

    public boolean charEquals(int row, int col, char ch) {
        return this.lines.get(row).charAt(col) == ch;
    }

    public boolean check(int row, int col) {
        // If we're at an edge, then we can't have a cross, so it's false.
        if (row - 1 < 0 || col - 1 < 0) {
            return false;
        }
        if (row + 1 >= this.lines.size() || col + 1 >= this.lines.get(row).length()) {
            return false;
        }
        // A = (row, col)
        // So we're looking for:
        // M @ NW, S @ SE
        // M @ NE, S @ SW
        // M @ SE, S @ NW
        // M @ SW, S @ NE
        // and there must be 2.

        int total = 0;
        if (checkNWSE(row, col)) {
            total++;
        }
        if (checkNESW(row, col)) {
            total++;
        }

        return total == 2;
    }

    private boolean checkNESW(int row, int col) {
        // A = row, col
        // NE = row - 1, col + 1
        // SW = row + 1, col - 1
        return (charEquals(row - 1, col + 1, 'M') && charEquals(row + 1, col - 1, 'S') ||
                charEquals(row - 1, col + 1, 'S') && charEquals(row + 1, col - 1, 'M'));
    }

    private boolean checkNWSE(int row, int col) {
        // A = row, col
        // NW = row - 1, col - 1
        // SE = row + 1, col + 1
        return (charEquals(row - 1, col - 1, 'M') && charEquals(row + 1, col + 1, 'S') ||
                charEquals(row - 1, col - 1, 'S') && charEquals(row + 1, col + 1, 'M'));
    }
}
