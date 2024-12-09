package daynine;

import java.util.*;

public class DiskMap {
    private final ArrayList<Bit> disk = new ArrayList<>();
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
        return this.disk.indexOf(new Bit(-1));
    }

    public void swap(int start, int end) {
        this.swap(start, end, 1);
    }

    public void swap(int to, int from, int length) {
        for (int i = 0; i < length; i++) {
            this.disk.get(to + i).setValue(this.disk.get(from + i).getValue());
            this.disk.get(from + i).setValue(-1);
        }
    }

    public long getChecksum() {
        long total = 0;
        for (int i = 0; i < this.disk.size(); i++) {
            Bit bit = this.disk.get(i);
            if (bit.isFile()) {
                total += i * bit.getValue();
            }
        }
        return total;
    }

    public void shrink() {
        // Now we have the final bit.
        // Pointer at the start, pointer at the end.
        // Go through the fileFormat char by char.
        // If it's a . then swap the value at the end pointer with this one.
        int start = this.getFirstSpacer();
        int end = this.getEndFileBit();
        while (start < end) {
            this.swap(start, end);
            start = this.getFirstSpacer();
            end = this.getEndFileBit();
        }
    }

    public TreeMap<Integer, Integer> findSpaces() {
        TreeMap<Integer, Integer> spaces = new TreeMap<>();
        int startSpacePointer = 0;
        int endSpacePointer = 0;
        while (startSpacePointer < this.disk.size() && endSpacePointer < this.disk.size()) {
            if (this.disk.get(startSpacePointer).isFile()) {
                startSpacePointer++;
            } else {
                endSpacePointer = startSpacePointer;
                while (!this.disk.get(endSpacePointer).isFile()) {
                    endSpacePointer++;
                }
                spaces.put(startSpacePointer, endSpacePointer - startSpacePointer);
                startSpacePointer = endSpacePointer;
            }
        }

        return spaces;
    }

    public Stack<File> findFiles() {
        Stack<File> stack = new Stack<>();
        int startSpacePointer = 0;
        int endSpacePointer = 0;
        while (startSpacePointer < this.disk.size() && endSpacePointer < this.disk.size()) {
            if (!this.disk.get(startSpacePointer).isFile()) {
                startSpacePointer++;
            } else {
                endSpacePointer = startSpacePointer;
                long value = this.disk.get(endSpacePointer).getValue();
                while (endSpacePointer < this.disk.size() &&
                    this.disk.get(endSpacePointer).isFile() &&
                    this.disk.get(endSpacePointer).getValue() == value
                ) {
                    endSpacePointer++;
                }
                stack.add(new File(startSpacePointer, endSpacePointer - startSpacePointer));
                startSpacePointer = endSpacePointer;
            }
        }
        return stack;
    }

    public void alternateShrink() {
        // Spaces are indexed low to high.
        TreeMap<Integer, Integer> spaces = findSpaces();
        // Files are indexed high to low
        Stack<File> files = findFiles();

        while (!files.empty()) {
            File file = files.pop();
            List<Integer> spaceIndex = spaces.keySet().stream().toList();
            int fileSize = file.length();
            for (int index : spaceIndex) {
                // The file can _only_ be swapped to the left, so once spaces index is higher, the file can't be moved.
                if (index > file.index()) {
                    break;
                }
                int spaceSize = spaces.get(index);
                if (spaceSize == fileSize) {
                    // there's space
                    this.swap(index, file.index(), fileSize);
                    // Don't consider this a space in future.
                    spaces.remove(index);
                    break;
                } else if (spaceSize > fileSize) {
                    // There's more than enough space!
                    this.swap(index, file.index(), fileSize);
                    spaces.put(index + fileSize, spaceSize - fileSize);
                    spaces.remove(index);
                    break;
                }
            }
        };
    }
}
