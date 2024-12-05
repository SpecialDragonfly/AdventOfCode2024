package dayfive;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

public class PrintRun implements Comparator<String> {
    private final Rules rules;
    private final ArrayList<String> pages;

    public PrintRun(Rules rules, ArrayList<String> pages) {
        this.rules = rules;
        this.pages = pages;
    }

    public String toString() {
        return this.pages.toString();
    }

    // Sorts the numbers based on the rules.
    public void order() {
        this.pages.sort(this);
    }

    // It's valid if before and after sorting the lists are the same.
    public boolean valid() {
        Vector<String> newOrder = (new Vector<>(this.pages));
        newOrder.sort(this);

        return this.pages.equals(newOrder);
    }

    public int middleNumber() {
        int middle = this.pages.size() / 2;
        return Integer.parseInt(this.pages.get(middle));
    }

    @Override
    public int compare(String o1, String o2) { // +ve if o1 appears after o2
        // Same values don't change.
        if (o1.equals(o2)) {
            return 0;
        }

        // If the rule exists, o1 occurs before o2.
        if (this.rules.contains(o1, o2)) {
            return -1;
        }

        return 0;
    }
}
