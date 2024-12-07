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
        if (this.add(current, 1) ||  this.mul(current, 1) || this.concat(current, 1)) {
            return this.target;
        }
        return BigInteger.ZERO;
    }

    private boolean add(BigInteger current, int index) {
        current = current.add(this.values.get(index));
        return calculateNextNumber(current, index);
    }

    private boolean mul(BigInteger current, int index) {
        current = current.multiply(this.values.get(index));
        return calculateNextNumber(current, index);
    }

    private boolean concat(BigInteger current, int index) {
        current = new BigInteger(current.toString() + this.values.get(index).toString());
        return calculateNextNumber(current, index);
    }

    private boolean calculateNextNumber(BigInteger current, int index) {
        // This was the last value. Did we reach our target?
        if (index == this.values.size() - 1) {
            return current.equals(this.target);
        }
        // If we're not at the last value, are we over the target value. If so, we can stop.
        if (current.longValue() > this.target.longValue()) {
            return false;
        }
        return add(current, index + 1) || mul(current, index + 1) || concat(current, index + 1);
    }
}
