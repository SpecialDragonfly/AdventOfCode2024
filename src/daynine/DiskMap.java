package daynine;

import java.util.ArrayList;

public class DiskMap {
    private ArrayList<Bit> disk = new ArrayList<>();
    public DiskMap() {

    }

    public void addBit(Bit bit) {
        this.disk.add(bit);
    }

    public int getEndFileBit() {
        for (int i = this.disk.size() - 1; i >= 0; i--) {
            if (this.disk.get(i).isFile()) {
                return i;
            }
        }
        return -1;
    }

    public int getFirstSpacer() {
        for (int i = 0; i < this.disk.size(); i++) {
            if (!this.disk.get(i).isFile()) {
                return i;
            }
        }
        return this.disk.size() + 1;
    }

    public void swap(int start, int end) {
        Bit endBit = this.disk.remove(end);
        this.disk.add(start, endBit);
        Bit startBit = this.disk.remove(start + 1);
        this.disk.add(end, startBit);
    }

    public void output() {
        for (Bit bit : this.disk) {
            System.out.print(bit);
        }
        System.out.print("\n");
    }

    public long getValue() {
        long total = 0;
        for (int i = 0; i < this.disk.size(); i++) {
            Bit bit = this.disk.get(i);
            if (bit.isFile()) {
                System.out.println("Multiplying " + i + " * " + bit.getValue());
                total += i * bit.getValue();
            }
        }
        return total;
    }
}
