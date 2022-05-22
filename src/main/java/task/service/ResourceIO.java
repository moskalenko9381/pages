package task.service;

import org.apache.log4j.Logger;
import task.exception.ReadFileException;
import task.exception.WriteException;

import java.io.*;
import java.util.Scanner;

public class ResourceIO {
    private static final Logger logger = Logger.getLogger(ResourceIO.class);

    public static String readFileToString(String name) throws ReadFileException {
        File file = new File(name);
        StringBuilder res = new StringBuilder();
        try (FileReader fr = new FileReader(file);
             BufferedReader reader = new BufferedReader(fr)) {
            String line = reader.readLine();
            while (line != null) {
                line = reader.readLine();
                res.append(line);
            }
            return res.toString();
        } catch (IOException e) {
            throw new ReadFileException(e, file.toPath());
        }
    }

    public static String readUrl(String name) throws ReadFileException {
        File file = new File(name);
        try (FileReader fr = new FileReader(file);
             BufferedReader reader = new BufferedReader(fr)) {
            return reader.readLine();
        } catch (IOException e) {
            throw new ReadFileException(e, file.toPath());
        }
    }

    public static void writeToFile(String lostPages, String newPages, String changedPages) {
        logger.info("Введите имя секретаря: ");
        String secretary = new Scanner(System.in).nextLine();
        File log = new File(Constant.LETTER_NAME);
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
            throw new WriteException(e, log.toPath());
        }
    }
}
