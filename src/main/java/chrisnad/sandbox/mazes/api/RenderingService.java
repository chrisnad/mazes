package chrisnad.sandbox.mazes.api;

import chrisnad.sandbox.mazes.domain.Cell;
import chrisnad.sandbox.mazes.domain.Grid;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

@Service
public class RenderingService {

    private static final int DARK = new Color(47,79,79).getRGB();
    private static final int WHITE = new Color(255, 255, 255).getRGB();

    public BufferedImage getBufferedImage(Grid maze, int scale) {
        if (scale < 1) return getBufferedImage(maze);
        return unsafeBufferedImage(maze, scale);
    }

    public BufferedImage getBufferedImage(Grid maze) {
        return unsafeBufferedImage(maze, 1);
    }

    public void toPng(Grid maze, int scale, String fileName) {
        saveToFile(getBufferedImage(maze, scale), fileName);
    }

    public void toPng(Grid maze, String fileName) {
        saveToFile(getBufferedImage(maze, 1), fileName);
    }

    private void saveToFile(BufferedImage image, String fileName) {
        File out = new File(fileName);
        try {
            ImageIO.write(image, "png", out);
        } catch (IOException e) {
            System.exit(5);
        }
    }

    private BufferedImage unsafeBufferedImage(Grid maze, int scale) {
        int cols = maze.getColumns();
        int rows = maze.getRows();
        int width = (2 * cols + 1) * scale;
        int height = (2 * rows + 1) * scale;
        Cell[][] cells = maze.getCells();
        BufferedImage image = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            int stepX = x;
            IntStream.range(0, scale).forEach(i -> image.setRGB(stepX, i, DARK));
        }
        for (int y = 0; y < height; y++) {
            int stepY = y;
            IntStream.range(0, scale).forEach(i -> image.setRGB(i, stepY, DARK));
        }
        for (int col = 0; col < cols; col++)
            for (int row = 0; row < rows; row++) {
                int x = 2 * col * scale + scale;
                int y = 2 * row * scale + scale;
                IntStream.range(0, scale)
                        .forEach(j -> IntStream.range(0, scale).forEach(i -> image.setRGB(x + i, y + j, WHITE)));
                IntStream.range(0, scale)
                        .forEach(j -> IntStream.range(0, scale).forEach(i -> image.setRGB(x + scale + i, y + scale + j, DARK)));

                if (cells[row][col].east == null || !cells[row][col].getLinks().contains(cells[row][col + 1])) {
                    IntStream.range(0, scale)
                            .forEach(j -> IntStream.range(0, scale).forEach(i -> image.setRGB(x + scale + i, y + j, DARK)));
                } else {
                    IntStream.range(0, scale)
                            .forEach(j -> IntStream.range(0, scale).forEach(i -> image.setRGB(x + scale + i, y + j, WHITE)));
                }

                if (cells[row][col].south == null || !cells[row][col].getLinks().contains(cells[row + 1][col])) {
                    IntStream.range(0, scale)
                            .forEach(j -> IntStream.range(0, scale).forEach(i -> image.setRGB(x + i, y + scale + j, DARK)));
                } else {
                    IntStream.range(0, scale)
                            .forEach(j -> IntStream.range(0, scale).forEach(i -> image.setRGB(x + i, y + scale + j, WHITE)));
                }
            }
        return image;
    }

}
