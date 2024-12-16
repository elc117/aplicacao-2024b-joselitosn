package io.github.joselitosn.log;

import io.github.joselitosn.events.EventListener;
import io.github.joselitosn.notifications.Notification;

import java.util.logging.Logger;

public class LogEventListener implements EventListener {
    private Logger logger;

    public LogEventListener(Logger logger) {
        this.logger = logger;
    }
    @Override
    public void update(String eventType, String message) {
        logger.info(message);
    }
}
