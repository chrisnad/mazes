package chrisnad.sandbox.mazes.domain.algorithms;

import chrisnad.sandbox.mazes.domain.Cell;
import chrisnad.sandbox.mazes.domain.Grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BinaryTree {
    private static final Random rand = new Random();
    private BinaryTree() {}

    public static void on(Grid grid) {
        Arrays.stream(grid.getCells())
                .flatMap(Arrays::stream)
                .forEach(cell -> {
                            List<Cell> neighbors = new ArrayList<>();
                            if (cell.north != null) neighbors.add(cell.north);
                            if (cell.east != null) neighbors.add(cell.east);
                            if (!neighbors.isEmpty())
                                cell.link(neighbors.get(rand.nextInt(neighbors.size())));
                        }
                );
    }

}
