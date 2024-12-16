package dayfourteen;

public class Robot {
    private int col;
    private int row;
    private final int velCol;
    private final int velRow;

    public Robot(int col, int row, int velCol, int velRow) {
        this.col = col;
        this.row = row;
        this.velCol = velCol;
        this.velRow = velRow;
    }

    public String toString() {
        return "(" + this.row + ", " + this.col + ") Vel: (" + this.velRow + ", " + this.velCol + ")";
    }

    public void move(int widthBound, int heightBound) {
        this.col = col + velCol;
        this.row = row + velRow;
        if (this.col >= widthBound) {
            this.col = this.col - widthBound;
        }
        if (this.col < 0) {
            this.col = widthBound + this.col;
        }
        if (this.row >= heightBound) {
            this.row = this.row - heightBound;
        }
        if (this.row < 0) {
            this.row = heightBound + this.row;
        }
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
