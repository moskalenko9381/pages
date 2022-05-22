package task.exception;

import java.nio.file.Path;

public class ReadFileException extends Exception {

    public ReadFileException(Exception e, Path path) {
        super("Couldn't read file " + path + ": " + e.getMessage());
    }
}
