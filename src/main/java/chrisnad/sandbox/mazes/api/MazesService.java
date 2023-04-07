package chrisnad.sandbox.mazes.api;

import chrisnad.sandbox.mazes.domain.Grid;
import chrisnad.sandbox.mazes.domain.algorithms.BinaryTree;
import chrisnad.sandbox.mazes.domain.algorithms.Sidewinder;
import org.springframework.stereotype.Service;

@Service
public class MazesService {

    public Grid sidewinder(int rows, int columns) {
        Grid grid = new Grid(rows, columns);
        Sidewinder.on(grid);
        return grid;
    }

    public Grid binaryTree(int rows, int columns) {
        Grid grid = new Grid(rows, columns);
        BinaryTree.on(grid);
        return grid;
    }
}
