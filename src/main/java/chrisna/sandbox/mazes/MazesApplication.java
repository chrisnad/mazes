package chrisna.sandbox.mazes;

import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

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
        if (args.length > 0 && "cli".equals(args[0])) {
            System.out.println("TODO generate maze...");
        }
    }
}
