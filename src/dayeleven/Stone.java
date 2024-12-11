package dayeleven;

import java.math.BigInteger;
import java.util.Vector;

public class Stone {
    private BigInteger value;
    private final Vector<Stone> children;

    public Stone(BigInteger value) {
        this.value = value;
        this.children = new Vector<>();
    }

    public void blink() {
        if (children.isEmpty()) {
        /*
If the stone is engraved with the number 0, it is replaced by a stone engraved with the number 1.
If the stone is engraved with a number that has an even number of digits, it is replaced by two stones. The left half of the digits are engraved on the new left stone, and the right half of the digits are engraved on the new right stone. (The new numbers don't keep extra leading zeroes: 1000 would become stones 10 and 0.)
If none of the other rules apply, the stone is replaced by a new stone; the old stone's number multiplied by 2024 is engraved on the new stone.
         */
            if (this.value.equals(new BigInteger("0"))) {
                this.children.add(new Stone(new BigInteger("1")));
            } else if (String.valueOf(this.value).length() % 2 == 0) {
                String x = String.valueOf(this.value);
                String x0 = x.substring(0, x.length()/2);
                String x1 = x.substring(x.length()/2);
                this.children.add(new Stone(new BigInteger(x0)));
                this.children.add(new Stone(new BigInteger(x1)));
            } else {
                this.children.add(new Stone(this.value.multiply(new BigInteger("2024"))));
            }
        } else {
            for (Stone stone : children) {
                stone.blink();
            }
        }
    }

    public String toString() {
        return this.value + "";
    }

    public Vector<Stone> getChildren() {
        if (this.children.isEmpty()) {
            Vector<Stone> x = new Vector<>();
            x.add(this);
            return x;
        }
        Vector<Stone> x = new Vector<>();
        for (Stone stone : this.children) {
            x.addAll(stone.getChildren());
        }
        return x;
    }

    public int getChildCount() {
        int total = 0;
        if (this.children.isEmpty()) {
            total++; // this one.
        } else {
            for (Stone stone : this.children) {
                total += stone.getChildCount();
            }
        }
        return total;
    }
}
