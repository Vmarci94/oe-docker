package hu.oe;

import java.io.File;
import java.io.FileInputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getClass().getName());

    public static void main(String[] args) throws InterruptedException {
        logger.info("app started!");
        logger.info(
                "usage args: (+)need files (true/false), (+)file absoulte path (linux file tree) separate with comma");
        logger.info(String.format("args: %s", Stream.of(args).collect(Collectors.joining(","))));
        if (args.length < 1) {
            logger.log(Level.SEVERE, "wrong argument list!");
            return;
        }

        try {
            if (String.valueOf(args[0]).equals("true")) {
                logger.info("Process start... (5sec)");
                Thread.sleep(5000);
                logger.info("Process end");
                logger.info("exit");
                return;
            }

            final String[] files = args[1].split(",");

            for (String filePath : files) {
                File file = new File(filePath);
                if (!file.exists()) {
                    throw new RuntimeException(String.format("File %s not exists!", filePath));
                }
                try (FileInputStream fis = new FileInputStream(file);
                        FileChannel fc = fis.getChannel();
                        FileLock fl = fc.tryLock()) {
                    logger.info("Process start... (5sec)");
                    Thread.sleep(5000);
                    logger.info("Process end");
                } catch (Exception e) {
                    throw e;
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        logger.info("exit");
    }

}