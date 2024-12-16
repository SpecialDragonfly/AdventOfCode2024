package dayfourteen;

import util.Util;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayFourteen {
    public static void main(String[] args) {
        Vector<String> lines = Util.readFile("./src/dayfourteen/input.txt");
        Map map = new Map(103, 101);
        lines.forEach(line -> {
            Pattern pattern = Pattern.compile("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                Robot r = new Robot(
                    Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),
                    Integer.parseInt(matcher.group(3)),
                    Integer.parseInt(matcher.group(4))
                );
                map.addRobot(r);
            }
        });

        try {
            map.toImage("./src/dayfourteen/output/image.initial.bmp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 101*103; i++) {
            map.move();
            try {
                map.toImage("./src/dayfourteen/output/image." + i + ".bmp");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        BigInteger total = BigInteger.ONE;
        total = total.multiply(new BigInteger(String.valueOf(map.getValues().get("NW"))));
        total = total.multiply(new BigInteger(String.valueOf(map.getValues().get("NE"))));
        total = total.multiply(new BigInteger(String.valueOf(map.getValues().get("SW"))));
        total = total.multiply(new BigInteger(String.valueOf(map.getValues().get("SE"))));
        System.out.println("Total: " + total);
    }

    private static void partOne() {
        Vector<String> lines = Util.readFile("./src/dayfourteen/input.txt");
        Map map = new Map(103, 101);
        lines.forEach(line -> {
            Pattern pattern = Pattern.compile("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                Robot r = new Robot(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3)),
                        Integer.parseInt(matcher.group(4))
                );
                map.addRobot(r);
            }
        });

        for (int i = 0; i < 100; i++) {
            map.move();
        }

        BigInteger total = BigInteger.ONE;
        total = total.multiply(new BigInteger(String.valueOf(map.getValues().get("NW"))));
        total = total.multiply(new BigInteger(String.valueOf(map.getValues().get("NE"))));
        total = total.multiply(new BigInteger(String.valueOf(map.getValues().get("SW"))));
        total = total.multiply(new BigInteger(String.valueOf(map.getValues().get("SE"))));
        System.out.println("Total: " + total);
    }
}
