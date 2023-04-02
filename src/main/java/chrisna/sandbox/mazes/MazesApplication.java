package chrisna.sandbox.mazes;

import chrisna.sandbox.mazes.domain.Grid;
import chrisna.sandbox.mazes.domain.algorithms.BinaryTree;
import chrisna.sandbox.mazes.domain.algorithms.Sidewinder;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@SpringBootApplication
public class MazesApplication implements CommandLineRunner {

    @Bean
    public HttpMessageConverter<BufferedImage> bufferedImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

    public static void main(String[] args) {
        SpringApplicationBuilder appBuilder = new SpringApplicationBuilder(MazesApplication.class);
        if (args.length > 0 && "cli".equals(args[0]))
            appBuilder.web(WebApplicationType.NONE)
                    .bannerMode(Banner.Mode.OFF)
                    .logStartupInfo(false);
        if (args.length > 0 && "mvc".equals(args[0]))
            appBuilder.web(WebApplicationType.SERVLET);
        appBuilder.build().run(args);
    }

    @Override
    public void run(String... args) {
        AtomicInteger i = new AtomicInteger();
        Map<Integer, String> argsMap =
                Arrays.stream(args).collect(Collectors.toMap(a -> i.getAndIncrement(), a -> a));
        if (argsMap.size() > 0 && "cli".equals(argsMap.get(0))) {
            String algo = argsMap.getOrDefault(1, "bt");
            int rows = Integer.parseInt(argsMap.getOrDefault(2, "4"));
            int columns = Integer.parseInt(argsMap.getOrDefault(3, "4"));

            Grid grid = new Grid(rows, columns);
            switch (algo) {
                case "bt" -> BinaryTree.on(grid);
                default -> Sidewinder.on(grid);
            }
            System.out.println(grid);
        }
    }
}
