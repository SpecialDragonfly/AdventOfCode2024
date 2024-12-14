package daythirteen;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClawMachine {
    Button buttonA;
    Button buttonB;
    Prize prize;
    long prizeOffset;

    public ClawMachine() {
        this.prizeOffset = 0L;
    }

    public ClawMachine(long prizeOffset) {
        this.prizeOffset = prizeOffset;
    }

    public void addLine(String line) {
        Pattern buttonPattern = Pattern.compile(".*?: X\\+(\\d+), Y\\+(\\d+)");
        if (line.startsWith("Button A")) {
            Matcher matcher = buttonPattern.matcher(line);
            if (matcher.find()) {
                buttonA = new Button(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), 3);
            }
        }
        if (line.startsWith("Button B")) {
            Matcher matcher = buttonPattern.matcher(line);
            if (matcher.find()) {
                buttonB = new Button(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), 1);
            }
        }
        if (line.startsWith("Prize")) {
            Pattern prizePatten = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)");
            Matcher matcher = prizePatten.matcher(line);
            if (matcher.find()) {
                prize = new Prize(Integer.parseInt(matcher.group(1)) + this.prizeOffset, Integer.parseInt(matcher.group(2)) + this.prizeOffset);
            }
        }
    }

    public String toString() {
        try {
            return "Button A: " + buttonA.toString() + ", Button B: " + buttonB.toString() + ", Solve: " + this.solveWithoutCare() + " Prize: " + this.prize;
        } catch (Exception e) {
            return "Button A: " + buttonA.toString() + ", Button B: " + buttonB.toString() + ", Solve: Det was 0";
        }
    }

    private HashMap<String, Double> solveWithoutCare() throws Exception {
        int inverseDet = this.inverseDet();
        if (inverseDet == 0) {
            throw new Exception("No Solution");
        }
        double buttonAPresses = ((double) 1 /inverseDet) * ((buttonB.y() * prize.x()) + (-1 * buttonB.x() * prize.y()));
        double buttonBPresses = ((double) 1 /inverseDet) * ((-1 * buttonA.y() * prize.x()) + (buttonA.x() * prize.y()));
        HashMap<String, Double> x = new HashMap<>();
        x.put("A", buttonAPresses);
        x.put("B", buttonBPresses);
        return x;
    }

    public double solve() throws Exception {
        int inverseDet = this.inverseDet();
        if (inverseDet == 0) {
            throw new Exception("No Solution");
        }
        double buttonAPresses = ((double) 1 /inverseDet) * ((buttonB.y() * prize.x()) + (-1 * buttonB.x() * prize.y()));
        double buttonBPresses = ((double) 1 /inverseDet) * ((-1 * buttonA.y() * prize.x()) + (buttonA.x() * prize.y()));

        // Just double check
        long buttonARounded = Math.round(buttonAPresses);
        long buttonBRounded = Math.round(buttonBPresses);
        if (buttonARounded * buttonA.x() + buttonBRounded * buttonB.x() == prize.x() && buttonARounded * buttonA.y() + buttonBRounded * buttonB.y() == prize.y()) {
            System.out.println(this);
            return buttonAPresses*buttonA.cost() + buttonBPresses*buttonB.cost();
        }

        throw new Exception("No solution");
        // Matrix = [A_X, B_X, [line1_val,
        //          A_Y, B_Y]  line2_val]
        //InverseMatrix = DET * Adjoint
        //DET = 1/(A_X*B_Y - B_X*A_Y) // if DET == 0, no solution
        //Adjoint = [B_Y, -A_Y,
        //           -B_X, A_X]
        //X = DET * [B_Y*line1_val + A_Y*line2_val]
        //Y = DET * [-B_X*line1_val + A_X*line2_val]
    }

    private int inverseDet() {
        return buttonA.x()*buttonB.y() - buttonB.x()*buttonA.y();
    }
}
