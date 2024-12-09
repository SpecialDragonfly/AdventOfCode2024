package daynine;

public class Bit {
    private long id;

    public Bit(int id) {
        this.id = id;
    }

    public boolean isFile() {
        return id != -1;
    }

    public String toString() {
        return this.id == -1 ? "." : (this.id + "|");
    }

    public long getValue() {
        return this.id;
    }

    public void setValue(long value) {
        this.id = value;
    }

    public boolean equals(Object other) {
        if (other instanceof Bit) {
            return ((Bit) other).getValue() == this.id;
        }
        return false;
    }
}
