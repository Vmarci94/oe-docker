package hu.oe;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getClass().getName());

    public static void main(String[] args) {
        logger.info("app started!");
        logger.info(String.format("args: %s", Stream.of(args).collect(Collectors.joining(","))));
    }

}