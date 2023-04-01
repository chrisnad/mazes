package chrisna.sandbox.mazes.domain;

import java.util.HashSet;
import java.util.Set;

public class Cell {
    private final int row, column;
    private final Set<Cell> links;
    public Cell north, south, east, west;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        links = new HashSet<>();
    }

    public void link(Cell cell) {
        link(cell, true);
    }

    public void link(Cell cell, boolean bidirectional) {
        links.add(cell);
        if (bidirectional) cell.link(this, false);
    }

    public void unlink(Cell cell) {
        unlink(cell, true);
    }

    public void unlink(Cell cell, boolean bidirectional) {
        links.remove(cell);
        if (bidirectional) cell.unlink(this, false);
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }

    public Set<Cell> links() {
        return links;
    }

    public Neighbors neighbors() {
        return new Neighbors(north, south, east, west);
    }

    record Neighbors(Cell north, Cell south, Cell east, Cell west){
        public int length() {
            int len = 0;
            if (north != null) len++;
            if (south != null) len++;
            if (east != null) len++;
            if (west != null) len++;
            return len;
        }
    }

}
