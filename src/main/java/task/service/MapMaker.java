package task.service;

import org.apache.log4j.Logger;
import task.exception.ReadFileException;

import java.nio.file.Paths;
import java.util.Map;

public class MapMaker {
    private static final Logger logger = Logger.getLogger(MapMaker.class);

    public static Map<String, String> makeMapFromPath(String directoryName) {
        return FileUtil.walkToMap(Paths.get(directoryName),
                path -> {
                    try {
                        return ResourceIO.readUrl(path.toString());
                    } catch (ReadFileException e) {
                        logger.error("Error reading URL in file " + path + " :" + e);
                    }
                    return null;
                },
                path -> {
                    try {
                        return ResourceIO.readFileToString(path.toString());
                    } catch (ReadFileException e) {
                        logger.error("Error reading content in file " + path + " :" + e);
                    }
                    return null;
                }
        );
    }
}
