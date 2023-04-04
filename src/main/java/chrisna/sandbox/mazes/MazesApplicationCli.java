package chrisna.sandbox.mazes;

import chrisna.sandbox.mazes.domain.Grid;
import chrisna.sandbox.mazes.domain.algorithms.BinaryTree;
import chrisna.sandbox.mazes.domain.algorithms.Sidewinder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
@ConditionalOnNotWebApplication
public class MazesApplicationCli implements CommandLineRunner {

    @Override
    public void run(String... args) {
        AtomicInteger i = new AtomicInteger();
        Map<Integer, String> argsMap =
                Arrays.stream(args).collect(Collectors.toMap(a -> i.getAndIncrement(), a -> a));
        String algo = argsMap.getOrDefault(0, "bt");
        int rows = Integer.parseInt(argsMap.getOrDefault(1, "4"));
        int columns = Integer.parseInt(argsMap.getOrDefault(2, "4"));

        Grid maze = new Grid(rows, columns);
        switch (algo) {
            case "bt" -> BinaryTree.on(maze);
            default -> Sidewinder.on(maze);
        }
        System.out.println(maze);
    }
}
