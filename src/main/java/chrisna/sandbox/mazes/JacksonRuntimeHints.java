package chrisna.sandbox.mazes;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.aot.hint.ExecutableMode;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@ImportRuntimeHints(JacksonRuntimeHints.ObjectIdGeneratorsRegistrar.class)
public class JacksonRuntimeHints {

    static class ObjectIdGeneratorsRegistrar implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            try {
                hints
                        .reflection()
                        .registerConstructor(
                                ObjectIdGenerators.IntSequenceGenerator.class.getConstructor(), ExecutableMode.INVOKE);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
