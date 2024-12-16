package io.github.joselitosn.log;

import io.github.joselitosn.events.EventListener;

import java.util.logging.Logger;

/**
 * Listener de eventos que loga as mensagens em um {@link Logger}.
 */
public class LogEventListener implements EventListener {
    private Logger logger;

    /**
     * Construtor.
     * @param logger Logger para log de mensagens.
     */
    public LogEventListener(Logger logger) {
        this.logger = logger;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void update(String eventType, String message) {
        logger.info(message);
    }
}
