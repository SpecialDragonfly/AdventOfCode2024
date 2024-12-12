package util;

public record IntPoint(int x, int y) {
    public boolean equals(Object o) {
        if (o instanceof IntPoint(int x1, int y1)) {
            return this.x == x1 && this.y == y1;
        }
        return false;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public IntPoint getNorthernPoint() {
        return new IntPoint(this.x - 1, this.y);
    }

    public IntPoint getSouthernPoint() {
        return new IntPoint(this.x + 1, this.y);
    }

    public IntPoint getEasternPoint() {
        return new IntPoint(this.x, this.y + 1);
    }

    public IntPoint getWesternPoint() {
        return new IntPoint(this.x, this.y - 1);
    }
}
