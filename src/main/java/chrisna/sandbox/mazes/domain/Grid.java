package chrisna.sandbox.mazes.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Collections;
import java.util.Random;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Grid {

    private static final Random rand = new Random();
    private final int rows, cols;
    private final Cell[][] cells;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        cells = new Cell[rows][cols];
        init();
        configureCells();
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
}
