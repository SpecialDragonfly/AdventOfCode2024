package dayfour;

import java.util.List;

public class WordSearch {
    private final List<String> lines;

    public WordSearch(List<String> lines) {
        this.lines = lines;
    }

    public int check(int row, int col) {
        int total = 0;
        if (this.checkUp(row, col)) {
            total += 1;
        }
        if (this.checkDown(row, col)) {
            total += 1;
        }
        if (this.checkLeft(row, col)) {
            total += 1;
        }
        if (this.checkRight(row, col)) {
            total += 1;
        }
        if (this.checkNE(row, col)) {
            total += 1;
        }
        if (this.checkSE(row, col)) {
            total += 1;
        }
        if (this.checkSW(row, col)) {
            total += 1;
        }
        if (this.checkNW(row, col)) {
            total += 1;
        }
        return total;
    }

    private boolean charEquals(int row, int col, char ch) {
        return this.lines.get(row).charAt(col) == ch;
    }

    private boolean checkUp(int row, int col) {
        // X = row
        // M = row - 1
        // A = row - 2
        // S = row - 3
        if (row - 3 < 0) {
            return false;
        }
        return (charEquals(row - 1, col, 'M') &&
                charEquals(row - 2, col, 'A') &&
                charEquals(row - 3, col, 'S')
        );
    }

    private boolean checkDown(int row, int col) {
        // X = row
        // M = row + 1
        // A = row + 2
        // S = row + 3
        if (row + 3 >= this.lines.size()) {
            return false;
        }
        return (charEquals(row + 1, col, 'M') &&
                charEquals(row + 2, col, 'A') &&
                charEquals(row + 3, col, 'S')
        );
    }

    private boolean checkLeft(int row, int col) {
        // X = col
        // M = col - 1
        // A = col - 2
        // S = col - 3
        if (col - 3 < 0) {
            return false;
        }
        return (charEquals(row, col - 1, 'M') &&
                charEquals(row, col - 2, 'A') &&
                charEquals(row, col - 3, 'S')
        );
    }

    private boolean checkRight(int row, int col) {
        // X = col
        // M = col + 1
        // A = col + 2
        // S = col + 3
        if (col + 3 >= this.lines.get(row).length()) {
            return false;
        }
        return (charEquals(row, col + 1, 'M') &&
                charEquals(row, col + 2, 'A') &&
                charEquals(row, col + 3, 'S')
        );
    }

    private boolean checkNE(int row, int col) {
        // X = col
        // M = row - 1, col + 1
        // A = row - 2, col + 2
        // S = row - 3, col + 3
        if (row - 3 < 0 || col + 3 >= this.lines.get(row).length()) {
            return false;
        }
        return (charEquals(row - 1, col + 1, 'M') &&
                charEquals(row - 2, col + 2, 'A') &&
                charEquals(row - 3, col + 3, 'S')
        );
    }

    private boolean checkSE(int row, int col) {
        // X = col
        // M = row + 1, col + 1
        // A = row + 2, col + 2
        // S = row + 3, col + 3
        if (row + 3 >= this.lines.size() || col + 3 >= this.lines.get(row).length()) {
            return false;
        }
        return (charEquals(row + 1, col + 1, 'M') &&
                charEquals(row + 2, col + 2, 'A') &&
                charEquals(row + 3, col + 3, 'S')
        );
    }

    private boolean checkSW(int row, int col) {
        // X = col
        // M = row + 1, col - 1
        // A = row + 2, col - 2
        // S = row + 3, col - 3
        if (row + 3 >= this.lines.size() || col - 3 < 0) {
            return false;
        }
        return (charEquals(row + 1, col - 1, 'M') &&
                charEquals(row + 2, col - 2, 'A') &&
                charEquals(row + 3, col - 3, 'S')
        );
    }

    private boolean checkNW(int row, int col) {
        // X = col
        // M = row - 1, col - 1
        // A = row - 2, col - 2
        // S = row - 3, col - 3
        if (row - 3 < 0 || col - 3 < 0) {
            return false;
        }
        return (charEquals(row - 1, col - 1, 'M') &&
                charEquals(row - 2, col - 2, 'A') &&
                charEquals(row - 3, col - 3, 'S')
        );
    }
}
