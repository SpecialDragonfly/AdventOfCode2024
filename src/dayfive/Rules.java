package dayfive;

import java.util.List;

public class Rules {
    private final List<String> rules;

    public Rules(List<String> rules) {
        this.rules = rules;
    }

    public boolean contains(String left, String right) {
        return this.rules.contains(left + "|" + right);
    }
}
