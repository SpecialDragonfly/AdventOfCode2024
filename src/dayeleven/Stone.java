package dayeleven;

import java.util.Vector;

public class Stone {
    private final Long value;
    private final Vector<Stone> children;

    public Stone(Long value) {
        this.value = value;
        this.children = new Vector<>();
    }

    public void blink() {
        /*
If the stone is engraved with the number 0, it is replaced by a stone engraved with the number 1.
If the stone is engraved with a number that has an even number of digits, it is replaced by two stones. The left half of the digits are engraved on the new left stone, and the right half of the digits are engraved on the new right stone. (The new numbers don't keep extra leading zeroes: 1000 would become stones 10 and 0.)
If none of the other rules apply, the stone is replaced by a new stone; the old stone's number multiplied by 2024 is engraved on the new stone.
         */

        if (this.value.equals(0L)) {
            children.add(new Stone(1L));
        } else if (String.valueOf(this.value).length() % 2 == 0) {
            String x = String.valueOf(this.value);
            String x0 = x.substring(0, x.length()/2);
            String x1 = x.substring(x.length()/2);
            children.add(new Stone(Long.parseLong(x0)));
            children.add(new Stone(Long.parseLong(x1)));
        } else {
            children.add(new Stone(this.value * 2024L));
        }
    }

    public Vector<Stone> getChildren() {
        return this.children;
    }

    public String toString() {
        return this.value + "";
    }

    public Long getValue() {
        return this.value;
    }
}
