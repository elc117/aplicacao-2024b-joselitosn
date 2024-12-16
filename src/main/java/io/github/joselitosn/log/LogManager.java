package io.github.joselitosn.log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public final class LogManager {
    private static volatile LogManager instance;

    private final Logger logger;

    private LogManager(Logger logger) {
        this.logger = logger;
    }

    public static LogManager getInstance(Logger logger) {
        LogManager result = instance;
        if (result != null) {
            return result;
        }
        synchronized (LogManager.class) {
            if (instance == null) {
                instance = new LogManager(logger);
            }
            return instance;
        }
    }

    public static LogManager getInstance() {
        Logger newlogger = Logger.getLogger("justsched");
        try {
            java.util.logging.LogManager.getLogManager().readConfiguration(Files.newInputStream(Paths.get("logger.properties")));
        } catch (SecurityException | IOException e1) {
            e1.printStackTrace();
        }
        newlogger.setLevel(Level.INFO);

        try {
            newlogger.addHandler(new FileHandler("justsched.log"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return LogManager.getInstance(newlogger);
    }

    public Logger getLogger() {
        return this.logger;
    }

    public void log(String message) {
        logger.log(new LogRecord(Level.INFO,message));
    }

}
