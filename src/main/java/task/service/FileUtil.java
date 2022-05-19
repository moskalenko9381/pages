package task.service;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FileUtil {
    private static final Logger logger = Logger.getLogger(FileUtil.class);

    public Map<String, String> makeMapFromPath(String directoryName) {
        try (Stream<Path> pathStream = Files.walk(Paths.get(directoryName))){
            return  pathStream
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toMap(this::getUrl, this::readFile));
        } catch (IOException e) {
            logger.error("Error finding files in directory");
        }
        return Collections.emptyMap();
    }

    private String readFile(String name) {
        File file = new File(name);
        try (FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr)) {
            String line = reader.readLine();
            StringBuilder res = new StringBuilder();
            while (line != null) {
                line = reader.readLine();
                res.append(line);
            }
            return res.toString();
        } catch (IOException e) {
            return "";
        }
    }

    private String getUrl(String name) {
        File file = new File(name);
        try (FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr)) {
            return reader.readLine();
        } catch (IOException e) {
            return "";
        }
    }

    public void writeToFile(String lostPages, String newPages, String changedPages) {
        logger.info("Введите имя секретаря: ");
        String secretary = new Scanner(System.in).nextLine();
        File log = new File("letter.txt");
        try (FileWriter fileWriter = new FileWriter(log, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write("Здравствуйте, дорогая " + secretary + "\n" +
                    "За последние сутки во вверенных Вам сайтах произошли следующие изменения:\n\n" +
                    "Исчезли следующие страницы:\n{" + lostPages + "}\n\n" +
                    "Появились следующие новые страницы:\n{" + newPages + "}\n\n" +
                    "Изменились следующие страницы:\n{" + changedPages + "}\n\n" +
                    "С уважением,\n" +
                    "автоматизированная система\n" +
                    "мониторинга.");
            logger.info("Writing in file completed");
        } catch (IOException e) {
            logger.error("Error while writing in file");
        }
    }
}
