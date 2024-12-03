package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.stream.Collectors;

public class FileReader {
    /**
     * Returns the input file as a Vector, one line for each element.
     * @param file
     * @return Vector<String>
     */
    public static Vector<String> readFile(String file) {
        try {
            return Files.lines(Paths.get(file))
                    .collect(Collectors.toCollection(Vector::new));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Vector<>();
    }

    /**
     * Returns the input file as a singular line
     * @param file
     * @return String
     */
    public static String getFileAsLine(String file) {
        Vector<String> lines = FileReader.readFile(file);
        StringBuilder sb = new StringBuilder();
        lines.forEach(sb::append);

        return sb.toString();
    }
}
