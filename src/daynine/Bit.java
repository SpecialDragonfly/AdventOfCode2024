package daynine;

public class Bit {
    private final int id;

    public Bit(int id) {
        this.id = id;
    }

    public boolean isFile() {
        return id != -1;
    }

    public String toString() {
        return this.id == -1 ? "." : ("" + this.id);
    }

    public long getValue() {
        return this.id;
    }
}
