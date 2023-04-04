package chrisna.sandbox.mazes.api.http;

import chrisna.sandbox.mazes.api.MazesService;
import chrisna.sandbox.mazes.api.RenderingService;
import chrisna.sandbox.mazes.domain.Cell;
import chrisna.sandbox.mazes.domain.Grid;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/mazes")
public class MazesHttpApi {
    @Autowired
    MazesService mazesService;
    @Autowired
    RenderingService renderingService;

    @GetMapping("/maze")
    @RegisterReflectionForBinding({Grid.class, Cell.class})  // register hints for Grid and Cell
    public Mono<Grid> generateGrid(
            @RequestParam int rows,
            @RequestParam int columns,
            @RequestParam(defaultValue = "bt") String algo) {
        return switch (algo) {
            case "bt" -> Mono.just(mazesService.binaryTree(rows, columns));
            default -> Mono.just(mazesService.sidewinder(rows, columns));
        };
    }

    @GetMapping(path = "/mazeImg", produces = MediaType.IMAGE_PNG_VALUE)
    public Mono<DataBuffer> generateImg(
            ServerHttpResponse response,
            @RequestParam int rows,
            @RequestParam int columns,
            @RequestParam(defaultValue = "bt") String algo,
            @RequestParam(defaultValue = "16") int scale) throws IOException {
        RenderedImage img = switch (algo) {
            case "bt" -> renderingService.getBufferedImage(
                    mazesService.binaryTree(rows, columns), scale);
            default -> renderingService.getBufferedImage(
                    mazesService.sidewinder(rows, columns), scale);
        };
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", baos);
        baos.close();
        DataBuffer imageData = response.bufferFactory().wrap(baos.toByteArray());
        return Mono.just(imageData);
    }

}
