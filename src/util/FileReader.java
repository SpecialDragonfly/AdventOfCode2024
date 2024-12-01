package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.stream.Collectors;

public class FileReader {
    /**
     * @param String file
     * @return Vector<String>
     */
    public static Vector<String> readFile(String file) {
        try {
            Vector<String> lines = Files.lines(Paths.get(file))
                    .collect(Collectors.toCollection(Vector::new));
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Vector<String>();
    }
}
