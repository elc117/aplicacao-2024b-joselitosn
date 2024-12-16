package io.github.joselitosn.log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Classe responsável por gerenciar os logs da aplicação.
 */
public final class LogManager {
    /**
     * Instância única do gerenciador de logs.
     */
    private static volatile LogManager instance;

    /**
     * Objeto Logger utilizado para registrar os logs.
     */
    private final Logger logger;

    /**
     * Construtor privado para evitar instanciação direta.
     * @param logger Objeto Logger a ser utilizado.
     */
    private LogManager(Logger logger) {
        this.logger = logger;
    }

    /**
     * Obtém a instância única do gerenciador de logs.
     * @param logger Objeto Logger a ser utilizado.
     * @return A instância única do gerenciador de logs.
     */
    public static LogManager getInstance(Logger logger) {
        LogManager result = instance;
        if (result != null) {
            return result;
        }
        synchronized(LogManager.class) {
            if (instance == null) {
                instance = new LogManager(logger);
            }
            return instance;
        }
    }
    /**
     * Obtém a instância única do gerenciador de logs, criando um novo Logger se necessário.
     * @return A instância única do gerenciador de logs.
     */
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

    /**
     * Obtém o objeto Logger utilizado pelo gerenciador de logs.
     * @return O objeto Logger utilizado.
     */
    public Logger getLogger() {
        return this.logger;
    }

    /**
     * Registra uma mensagem de log.
     * @param message A mensagem a ser registrada.
     */
    public void log(String message) {
        logger.log(new LogRecord(Level.INFO, message));
    }

    /**
     * Registra uma mensagem de log com um nível de severidade específico.
     * @param level O nível de severidade da mensagem.
     * @param message A mensagem a ser registrada.
     */
    public void log(Level level, String message) {
        logger.log(new LogRecord(level, message));
    }

    /**
     * Registra uma exceção como uma mensagem de log.
     * @param exception A exceção a ser registrada.
     */
    public void log(Exception exception) {
        logger.log(new LogRecord(Level.SEVERE, exception.getMessage()));
    }
}
