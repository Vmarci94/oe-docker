package hu.oe;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws InterruptedException {
        logger.info("app started!");
        logger.info(
                "usage args: <<file absoulte path>> <<openning mode, separate with comma e.g: READ,WRITE,APPEND,CREATE>>, etc..");
        logger.info(Arrays.toString(args));
        int timeout = Integer.parseInt(
                Optional.ofNullable(System.getenv("TIMEOUT")).orElseGet(() -> {
                    logger.warning("Not found TIMEOUT env, the default value is 5000");
                    return "5000";
                })
        );
        logger.info(String.format("timeout: %s", timeout));

        if (args.length == 0) {
            processDummy(timeout);
            return;
        }

        if (args.length % 2 == 0) {
            for (int i = 0; i < args.length - 1; i+=2) {
                var filePath = Paths.get(args[i]);
                var openOption =
                        Stream.of(args[i + 1].split(",")).map(StandardOpenOption::valueOf).collect(Collectors.toList());
                try (var fileChannel = FileChannel.open(filePath, openOption.toArray(StandardOpenOption[]::new))) {
                    if (openOption.contains(StandardOpenOption.WRITE)) {
                        var buff = ByteBuffer.wrap(String.format("\nHello world#%s#Hello world\n",
                                LocalDateTime.now()).getBytes(StandardCharsets.UTF_8));
                        fileChannel.write(buff);
                    }
                    processDummy(timeout, filePath);
                } catch (Exception e) {
                    logger.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        } else throw new IllegalArgumentException();
        logger.info("exit");
    }

    public static void processDummy(int sleepTimeOut, Path path) throws InterruptedException {
        logger.info(String.format("Process start... %s (%s sec)", path, sleepTimeOut/1000));
        Thread.sleep(sleepTimeOut);
        logger.info("Process end");
    }

    public static void processDummy(int sleepTimeOut) throws InterruptedException {
        logger.info(String.format("Process start... %s (%s sec)", "#no files, just sleep#" ,sleepTimeOut/1000));
        Thread.sleep(sleepTimeOut);
        logger.info("Process end");
    }

}