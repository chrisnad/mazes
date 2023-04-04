package chrisna.sandbox.mazes.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Collections;
import java.util.Random;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Grid {

    private static final Random rand = new Random();
    private final int rows, columns;
    private final Cell[][] cells;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        cells = new Cell[rows][columns];
        init();
        configureCells();
    }

    private void init() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    private void configureCells() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (row > 0) cells[row][column].north = cells[row - 1][column];
                if (row + 1 < rows) cells[row][column].south = cells[row + 1][column];
                if (column > 0) cells[row][column].west = cells[row][column - 1];
                if (column + 1 < columns) cells[row][column].east = cells[row][column + 1];
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int size() {
        return rows * columns;
    }

    public Cell randomCell() {
        int row = rand.nextInt(rows);
        int column = rand.nextInt(cells[row].length);
        return cells[row][column];
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        output.append("+");
        Collections.nCopies(columns, "---+").forEach(output::append);
        output.append("\n");

        for (Cell[] row : cells) {
            var top = new StringBuilder("|");
            var bottom = new StringBuilder("+");

            for (Cell cell : row) {
                var eastBoundary = cell.getLinks().contains(cell.east) ? " " : "|";
                top.append("   ").append(eastBoundary);

                var southBoundary = cell.getLinks().contains(cell.south) ? "   " : "---";
                bottom.append(southBoundary).append("+");
            }
            output.append(top).append("\n");
            output.append(bottom).append("\n");
        }
        return output.toString();
    }
}
