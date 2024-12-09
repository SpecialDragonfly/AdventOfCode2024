package daynine;

import util.Util;

import java.util.Vector;

public class DayNine {
    public static void main(String[] args) {
        String line = Util.getFileAsLine("./src/daynine/input.txt");

        String[] parts = line.split("");

        DiskMap diskmap = new DiskMap();

        boolean file = true;
        int fileId = 0;
        for (String part : parts) {
            int times = Integer.parseInt(part);
            if (file) {
                for (int i = 0; i < times; i++) {
                    diskmap.addBit(new Bit(fileId));
                }
                fileId++;
            } else {
                for (int i = 0; i < times; i++) {
                    diskmap.addBit(new Bit(-1));
                }
            }
            file = !file;
        }
    }

    public static void partOne() {
        String line = Util.getFileAsLine("./src/daynine/input.txt");
        // Take the line e.g. 2333133121414131402
        // First number is a 'file'
        // Second number is a number of '.'
        // So the above starts 00...111...2...333.44.5555.6666.777.888899

        String[] parts = line.split("");

        DiskMap diskmap = new DiskMap();

        boolean file = true;
        int fileId = 0;
        for (String part : parts) {
            int times = Integer.parseInt(part);
            if (file) {
                for (int i = 0; i < times; i++) {
                    diskmap.addBit(new Bit(fileId));
                }
                fileId++;
            } else {
                for (int i = 0; i < times; i++) {
                    diskmap.addBit(new Bit(-1));
                }
            }
            file = !file;
        }

        // Now we have the final bit.
        // Pointer at the start, pointer at the end.
        // Go through the fileFormat char by char.
        // If it's a . then swap the value at the end pointer with this one.
        // use string.replace(char, char)
        int start = diskmap.getFirstSpacer();
        int end = diskmap.getEndFileBit();
        while (start < end) {
            diskmap.swap(start, end);
            start = diskmap.getFirstSpacer();
            end = diskmap.getEndFileBit();
        }
        diskmap.output();

        System.out.println("Value: " + diskmap.getValue()); // Value was 6356833654075
    }
}
