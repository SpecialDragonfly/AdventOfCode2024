package dayseven;

import java.math.BigInteger;
import java.util.List;

public class Equation {
    private final List<BigInteger> values;
    private final BigInteger target;

    public Equation(BigInteger target, List<BigInteger> values) {
        this.values = values;
        this.target = target;
    }

    public BigInteger calculate() {
        BigInteger current = values.getFirst();
        if (this.add(current, 1) > 0) {
            return this.target;
        }
        if (this.mul(current, 1) > 0) {
            return this.target;
        }
        if (this.concat(current, 1) > 0) {
            return this.target;
        }
        return BigInteger.ZERO;
    }

    private int add(BigInteger current, int index) {
        current = current.add(this.values.get(index));
        // This was the last value. Did we reach our target?
        if (index == this.values.size() - 1) {
            if (current.equals(this.target)) {
                return 1;
            }
            return 0;
        }
        // It's not the last value, have we exceeded our target though?
        if (current.longValue() > this.target.longValue()) {
            return 0;
        }
        return add(current, index + 1) + mul(current, index + 1) + concat(current, index + 1);
    }

    private int mul(BigInteger current, int index) {
        current = current.multiply(this.values.get(index));
        // This was the last value. Did we reach our target?
        if (index == this.values.size() - 1) {
            if (current.equals(this.target)) {
                return 1;
            }
            return 0;
        }
        // It's not the last value, have we exceeded our target though?
        if (current.longValue() > this.target.longValue()) {
            return 0;
        }
        return add(current, index + 1) + mul(current, index + 1) + concat(current, index + 1);
    }

    private int concat(BigInteger current, int index) {
        current = new BigInteger(current.toString() + this.values.get(index).toString());

        if (index == this.values.size() - 1) {
            if (current.equals(this.target)) {
                return 1;
            }
            return 0;
        }
        if (current.longValue() > this.target.longValue()) {
            return 0;
        }
        return add(current, index + 1) + mul(current, index + 1) + concat(current, index + 1);
    }
}
