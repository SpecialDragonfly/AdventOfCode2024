package daynine;

import util.Util;

public class DayNine {
    public static void main(String[] args) {
        partOne();
        //partTwo();
    }

    public static void partTwo() {
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
        Util.debug = false;
        diskmap.alternateShrink();

        System.out.println("Value: " + diskmap.getChecksum()); // Answer: 6389911791746
        // 8573601415879 too high,
        // 8137352565462 too high
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
        diskmap.shrink();

        System.out.println("Value: " + diskmap.getChecksum()); // Value was 6356833654075
    }
}
