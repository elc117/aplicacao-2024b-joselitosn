package io.github.joselitosn.log;

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

    public Logger getLogger() {
        return this.logger;
    }

    public void log(String message) {
        logger.log(new LogRecord(Level.INFO,message));
    }

}
