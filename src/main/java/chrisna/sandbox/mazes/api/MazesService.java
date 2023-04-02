package chrisna.sandbox.mazes.api;

import chrisna.sandbox.mazes.domain.Grid;
import chrisna.sandbox.mazes.domain.algorithms.BinaryTree;
import chrisna.sandbox.mazes.domain.algorithms.Sidewinder;
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
