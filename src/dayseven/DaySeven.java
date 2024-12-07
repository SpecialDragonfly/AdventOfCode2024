package dayseven;

import util.Util;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Vector;

public class DaySeven {
    public static void main(String[] args) {
        Vector<String> lines = Util.readFile("./src/dayseven/input.txt");

        BigInteger total = BigInteger.ZERO;
        for (String line : lines) {
            String[] parts = line.split(":");
            Equation equation = new Equation(
                new BigInteger(parts[0]),
                Arrays.stream(parts[1].trim().split(" ")).map(BigInteger::new).toList()
            );
            total = total.add(equation.calculate());
        }
        System.out.println(total);
        // Part 1: 1153997401072
        // Part 2: 97902809384118
    }
}
