package chrisna.sandbox.mazes;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MazesApplication {

    public static void main(String[] args) {
        if ("yes".equals(System.getenv("MAZES_CLI"))) {
            SpringApplicationBuilder appBuilder = new SpringApplicationBuilder(MazesApplicationCli.class);
            appBuilder.web(WebApplicationType.NONE)
                    .bannerMode(Banner.Mode.OFF)
                    .logStartupInfo(false);
            appBuilder.build().run(args);
        } else {
            SpringApplication.run(MazesApplication.class, args);
        }
    }

}
