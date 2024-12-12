package daytwelve;

import java.util.ArrayList;

public class GardenPlot {
    private final String value;
    ArrayList<String> fences = new ArrayList<>();

    public GardenPlot(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public void setFence(String fence) {
        fences.add(fence);
    }

    public boolean hasFences() {
        return !this.fences.isEmpty();
    }

    public ArrayList<String> getFences() {
        return this.fences;
    }

    public boolean equals(GardenPlot gardenPlot) {
        return this.value.equals(gardenPlot.getValue());
    }
}
