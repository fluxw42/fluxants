package info.fluxprojects.fluxant.utils;

/*
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
*/

public class MapPrinter {

/*
    public static synchronized void printMap(boolean[][] map, String fileName) {
        int cols = map.length;
        int rows = map[0].length;

        BufferedImage bimage = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                boolean pixel = map[c][r];
                bimage.setRGB(c, r, pixel ? Color.GRAY.darker().getRGB() : Color.WHITE.getRGB());
            }
        }

        BufferedImage resizedImage = new BufferedImage(cols * 10, rows * 10, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(bimage, 0, 0, cols * 10, rows * 10, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            ImageIO.write(resizedImage, "png", new File("c:\\antdebug\\" + fileName + "_" + System.currentTimeMillis() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static synchronized void printMap(int[][] map, String fileName) {
        int cols = map.length;
        int rows = map[0].length;

        BufferedImage bimage = new BufferedImage(cols, rows, BufferedImage.TYPE_INT_RGB);
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                int pixel = map[c][r];
                if (pixel < 0) {
                    bimage.setRGB(c, r, Color.GRAY.getRGB());
                } else if (pixel > 0) {
                    int high = getHighestValue(map);
                    int low = getLowestValue(map);
                    double scaler = 254d / (high - low);
                    int rgb = (int) ((pixel - low) * scaler);
                    rgb <<= 8;
                    bimage.setRGB(c, r, rgb);
                } else {
                    bimage.setRGB(c, r, Color.BLACK.getRGB());
                }
            }
        }

        BufferedImage resizedImage = new BufferedImage(cols * 10, rows * 10, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(bimage, 0, 0, cols * 10, rows * 10, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            ImageIO.write(resizedImage, "png", new File("c:\\antdebug\\" + fileName + "_" + System.currentTimeMillis() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static synchronized void printMap(long[][] map, String fileName) {
        int cols = MapUtils.getCols(map);
        int rows = MapUtils.getRows(map);
        int[][] ints = new int[cols][rows];
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                ints[c][r] = (int) (map[c][r] / (Long.MAX_VALUE / Integer.MAX_VALUE));
            }
        }
        printMap(ints, fileName);
    }

    private static int getLowestValue(int[][] map) {
        int cols = map.length;
        int rows = map[0].length;
        int low = Integer.MAX_VALUE;
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                int value = map[c][r];
                low = (value < low) && (value >= 0) ? value : low;
            }
        }
        return low;
    }

    private static int getHighestValue(int[][] map) {
        int cols = map.length;
        int rows = map[0].length;
        int high = 0;
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows; r++) {
                high = map[c][r] > high ? map[c][r] : high;
            }
        }
        return high;
    }
*/

}
