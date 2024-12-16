package dayfourteen;

import util.IntPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Map {
    private final ArrayList<ArrayList<String>> map;
    private final int height;
    private final int width;
    private Vector<Robot> robots;

    public Map(int height, int width) {
        this.height = height;
        this.width = width;
        this.map = new ArrayList<>();
        this.robots = new Vector<>();
    }

    public void addRobot(Robot robot) {
        this.robots.add(robot);
    }

    public void move() {
        robots.forEach(r -> r.move(this.width, this.height));
    }

    public void output() {
        int rowMidpoint = (int) (double) (this.height / 2);
        int colMidpooint = (int) (double) (this.width / 2);
        //System.out.println("Midpoint: " + rowMidpoint + ", " + colMidpooint);
        HashMap<IntPoint, Integer> robotCount = new HashMap<>();
        robots.forEach(r -> {
            IntPoint p = new IntPoint(r.getRow(), r.getCol());
            if (!robotCount.containsKey(p)) {
                robotCount.put(p, 0);
            }
            robotCount.merge(p, 1, Integer::sum);
        });
        for (int row = 0; row < height; row++) {
            String rowString = row + "";
            if (rowString.length() == 1) {
                rowString = "0" + row;
            }
            if (rowString.length() == 2) {
                rowString = "0" + row;
            }
            System.out.print(rowString + " ");
            for (int col = 0; col < width; col++) {
                if (row == rowMidpoint || col == colMidpooint) {
                    System.out.print("#");
                } else {
                    IntPoint p = new IntPoint(row, col);
                    if (robotCount.containsKey(p)) {
                        System.out.print(robotCount.get(p));
                    } else {
                        System.out.print(".");
                    }
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    public HashMap<String, Integer> getValues() {
        HashMap<String, Integer> values = new HashMap<>();
        values.put("NW", 0);
        values.put("NE", 0);
        values.put("SW", 0);
        values.put("SE", 0);

        HashMap<IntPoint, Integer> robotLocations = new HashMap<>();
        robots.forEach(r -> {
            robotLocations.merge(new IntPoint(r.getRow(), r.getCol()), 1, Integer::sum);
        });

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (robotLocations.containsKey(new IntPoint(row, col))) {
                    //System.out.println("Width/2: " + (width/2) + " Height/2: " + (height/2));
                    if (col < (width/2)) {
                        if (row < (height/2)) {
                            // NW
                            values.merge("NW", robotLocations.get(new IntPoint(row, col)), Integer::sum);
                        } else if (row > (height/2)) {
                            // SW
                            values.merge("SW", robotLocations.get(new IntPoint(row, col)), Integer::sum);
                        }
                    } else if (col > (width/2)) {
                        if (row < height/2) {
                            // NE
                            values.merge("NE", robotLocations.get(new IntPoint(row, col)), Integer::sum);
                        } else if (row > (height/2)) {
                            // SE
                            values.merge("SE", robotLocations.get(new IntPoint(row, col)), Integer::sum);
                        }
                    }
                }
            }
        }

        return values;
    }

    public void toImage(String fileName) throws IOException {
        BufferedImage image = new BufferedImage(101, 103, BufferedImage.TYPE_BYTE_BINARY);
        for (int i = 0; i < this.robots.size(); i++) {
            image.setRGB(this.robots.get(i).getCol(), this.robots.get(i).getRow(), new Color(255, 255, 255).getRGB());
        }
        ImageIO.write(image, "BMP", new File(fileName));
    }
}
