package task.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FileUtil {

    public static Stream<Path> walk(Path directory) {
        if (!directory.toFile().isDirectory()) {
            throw new RuntimeException("Current path is not a directory");
        }
        try {
            return Files.walk(directory);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't walk directory " + directory + ": " + e.getMessage());
        }
    }

    public static <K, V> Map<K, V> walkToMap(Path directory,
                                             Function<Path, K> keyProducer,
                                             Function<Path, V> valueProducer) {
        return walk(directory).filter(Files::isRegularFile)
                .collect(Collectors.toMap(keyProducer, valueProducer));
    }
}
