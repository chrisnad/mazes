package chrisna.sandbox.mazes.domain;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Random;

public class Grid {
    private static final Random rand = new Random();
    private static final int BLACK = new Color(0, 0, 0).getRGB();
    private static final int WHITE = new Color(255, 255, 255).getRGB();
    private final int rows, cols;
    private final Cell[][] cells;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        cells = new Cell[rows][cols];
        init();
        configureCells();
    }

    public int rows() {
        return rows;
    }

    public int columns() {
        return cols;
    }

    public int size() {
        return rows * cols;
    }

    public Cell[][] cells() {
        return cells;
    }

    public Cell randomCell() {
        int row = rand.nextInt(rows);
        int col = rand.nextInt(cells[row].length);
        return cells[row][col];
    }

    private void init() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    private void configureCells() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (row > 0) cells[row][col].north = cells[row - 1][col];
                if (row + 1 < rows) cells[row][col].south = cells[row + 1][col];
                if (col > 0) cells[row][col].west = cells[row][col - 1];
                if (col + 1 < cols) cells[row][col].east = cells[row][col + 1];
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        output.append("+");
        Collections.nCopies(cols, "---+").forEach(output::append);
        output.append("\n");

        for (Cell[] row : cells) {
            var top = new StringBuilder("|");
            var bottom = new StringBuilder("+");

            for (Cell cell : row) {
                var eastBoundary = cell.links().contains(cell.east) ? " " : "|";
                top.append("   ").append(eastBoundary);

                var southBoundary = cell.links().contains(cell.south) ? "   " : "---";
                bottom.append(southBoundary).append("+");
            }
            output.append(top).append("\n");
            output.append(bottom).append("\n");
        }
        return output.toString();
    }

    public void toPng(String fileName) {
        BufferedImage image = new BufferedImage(2 * cols + 1, 2 * rows + 1, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x <= 2 * cols; x++) {
            image.setRGB(x, 0, BLACK);
        }
        for (int y = 0; y <= 2 * rows; y++) {
            image.setRGB(0, y, BLACK);
        }
        for (int col = 0; col < cols; col++)
            for (int row = 0; row < rows; row++) {
                int x = 2 * col + 1;
                int y = 2 * row + 1;
                image.setRGB(x, y, WHITE);
                image.setRGB(x + 1, y + 1, BLACK);

                if (cells[row][col].east == null || !cells[row][col].links().contains(cells[row][col + 1])) {
                    image.setRGB(x + 1, y, BLACK);
                } else {
                    image.setRGB(x + 1, y, WHITE);
                }

                if (cells[row][col].south == null || !cells[row][col].links().contains(cells[row + 1][col])) {
                    image.setRGB(x, y + 1, BLACK);
                } else {
                    image.setRGB(x, y + 1, WHITE);
                }
            }
        File out = new File(fileName);
        try {
            ImageIO.write(image, "png", out);
        } catch (IOException e) {
            System.out.println("Error Writing to File");
            System.exit(5);
        }
    }
}
