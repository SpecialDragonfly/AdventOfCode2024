package daytwo;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import util.Util;

public class Main {
    public static void main(String[] args) {
        Vector<String> lines = Util.readFile("./src/input.txt");
        Main.partOne(lines);
        Main.partTwo(lines);
    }

    public static void partOne(Vector<String> lines) {
        Vector<List<Integer>> valid = new Vector<>();
        lines.forEach(line -> {
            List<Integer> numbers = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
            if (numbers.get(0) > numbers.get(1)) {
                // possible descending strat
                if (testDescending(numbers)) {
                    valid.add(numbers);
                }
            } else {
                // possible ascending strat
                if (testAscending(numbers)) {
                    valid.add(numbers);
                }
            }
        });
        System.out.println("Valid lines: " + valid.size());
    }

    public static void partTwo(Vector<String> lines) {
        Vector<List<Integer>> valid = new Vector<>();
        Vector<List<Integer>> invalid = new Vector<>();
        lines.forEach(line -> {
            List<Integer> numbers = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
            if (numbers.get(0) > numbers.get(1)) {
                // possible descending strat
                if (!Main.testDescending(numbers)) {
                    invalid.add(numbers);
                } else {
                    valid.add(numbers);
                }
            } else {
                // possible ascending strat
                if (!Main.testAscending(numbers)) {
                    invalid.add(numbers);
                } else {
                    valid.add(numbers);
                }
            }
        });
        invalid.forEach(numbers -> {
            // For i = 0; i < numbers.length; i++ // remove this index
            for (int i = 0; i < numbers.size(); i++) {
                List<Integer> newList = recreateList(numbers, i);
                if (newList.get(0).equals(newList.get(1))) {
                    // could be either, check asc and desc
                    if (Main.testAscending(newList)) {
                        valid.add(newList);
                        break;
                    }
                    if (Main.testDescending(newList)) {
                        valid.add(newList);
                        break;
                    }

                } else if (newList.get(0) > newList.get(1)) {
                    // desc
                    if (Main.testDescending(newList)) {
                        valid.add(newList);
                        break;
                    }
                } else {
                    // asc
                    if (Main.testAscending(newList)) {
                        valid.add(newList);
                        break;
                    }
                }
            }
            // Check whether asc/desc
            // Run PartOne.Asc or PartOne.Desc on it.
            // If we get a valid run. Move it to valid.
        });
        System.out.println("Valid lines: " + valid.size());
    }

    private static List<Integer> recreateList(List<Integer> numbers, int ignoreIndex) {
        List<Integer> newList = new Vector<>();
        for (int i = 0; i < numbers.size(); i++) {
            if (i == ignoreIndex) {
                continue;
            }
            newList.add(numbers.get(i));
        }
        return newList;
    }

    public static boolean testAscending(List<Integer> numbers) {
        boolean valid = true;
        for (int i = 0; i < numbers.size() - 1; i++) {
            int first = numbers.get(i);
            int second = numbers.get(i+1);
            if (first >= second || first + 3 < second) {
                valid = false;
                break;
            }
        }
        return valid;
    }

    public static boolean testDescending(List<Integer> numbers) {
        boolean valid = true;
        for (int i = 0; i < numbers.size() - 1; i++) {
            int first = numbers.get(i);
            int second = numbers.get(i + 1);
            if (first <= second || first - 3 > second) {
                valid = false;
                break;
            }
        }
        return valid;
    }
}
