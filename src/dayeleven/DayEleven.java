package dayeleven;

import util.Util;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class DayEleven {
    public static void main(String[] args) {
        String line = Util.getFileAsLine("./src/dayeleven/input.txt");
        List<Stone> stones = Arrays.stream(line.split(" ")).map(value -> new Stone(new BigInteger(value))).toList();
        System.out.println(stones);

        int blinkTimes = 25;
        for (int i = 0; i < blinkTimes; i++) {
            for (Stone stone : stones) {
                stone.blink();
            }
        }
        Vector<Stone> output = new Vector<>();
        for (Stone stone : stones) {
            output.addAll(stone.getChildren());
        }
        System.out.println(output);
        System.out.println(output.size());
    }
}
