package task.exception;

import java.nio.file.Path;

public class WriteException extends RuntimeException {

    public WriteException(Exception e, Path path) {
        super("Couldn't write to file " + path + ": " + e.getMessage());
    }

}