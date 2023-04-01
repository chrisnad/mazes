package chrisna.sandbox.mazes;

import chrisna.sandbox.mazes.domain.Grid;
import chrisna.sandbox.mazes.domain.algorithms.Sidewinder;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@SpringBootApplication
public class MazesApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplicationBuilder appBuilder = new SpringApplicationBuilder(MazesApplication.class);
        if (args.length > 0 && "cli".equals(args[0]))
            appBuilder.web(WebApplicationType.NONE)
                    .bannerMode(Banner.Mode.OFF)
                    .logStartupInfo(false);
        appBuilder.build().run(args);
    }

    @Override
    public void run(String... args) {
        AtomicInteger i = new AtomicInteger();
        Map<Integer, String> argsMap =
                Arrays.stream(args).collect(Collectors.toMap(a -> i.getAndIncrement(), a -> a));
        if (argsMap.size() > 0 && "cli".equals(argsMap.get(0))) {
            int row = Integer.parseInt(argsMap.getOrDefault(1, "4"));
            int col = Integer.parseInt(argsMap.getOrDefault(2, "4"));
            Grid grid = new Grid(row, col);
            Sidewinder.on(grid);
            System.out.println(grid);
            grid.toPng("maze.png");
        }
    }
}
