package chrisnad.sandbox.mazes.domain.algorithms;

import chrisnad.sandbox.mazes.domain.Cell;
import chrisnad.sandbox.mazes.domain.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sidewinder {
    private static final Random rand = new Random();
    private Sidewinder() {}

    public static void on(Grid grid) {
        for (Cell[] row : grid.getCells()) {
            List<Cell> run = new ArrayList<>();
            for (Cell cell : row) {
                run.add(cell);

                var atEasternBoundary = cell.east == null;
                var atNortherBoundary = cell.north == null;

                var shouldCloseOut = atEasternBoundary || (!atNortherBoundary && rand.nextInt(2) == 0);

                if (shouldCloseOut) {
                    var member = run.get(rand.nextInt(run.size()));
                    if (member.north != null) member.link(member.north);
                    run.clear();
                } else {
                    cell.link(cell.east);
                }
            }
        }
    }

}
