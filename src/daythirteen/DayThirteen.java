package daythirteen;

import util.Util;

import java.util.Vector;

public class DayThirteen {
    public static void main(String[] args) {
        Vector<String> lines = Util.readFile("./src/daythirteen/input.txt");
        Vector<ClawMachine> machines = new Vector<>();
        ClawMachine machine = new ClawMachine(10000000000000L);
        for (String line : lines) {
            if (line.isEmpty()) {
                machines.add(machine);
                machine = new ClawMachine(10000000000000L);
            }
            machine.addLine(line);
        }
        machines.add(machine);

        double total = 0;
        for (ClawMachine x : machines) {
            try {
                total += x.solve();
            } catch (Exception e) {
                System.out.println("No solution: " + x);
            }
        }
        System.out.println(total);
    }

    private static void partOne() {
        Vector<String> lines = Util.readFile("./src/daythirteen/test.txt");
        Vector<ClawMachine> machines = new Vector<>();
        ClawMachine machine = new ClawMachine();
        for (String line : lines) {
            if (line.isEmpty()) {
                machines.add(machine);
                machine = new ClawMachine();
            }
            machine.addLine(line);
        }
        machines.add(machine);

        double total = 0;
        for (ClawMachine x : machines) {
            try {
                total += x.solve();
            } catch (Exception e) {
                System.out.println("No solution: " + x);
            }
        }
        System.out.println(total);
    }
}
