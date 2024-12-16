package io.github.joselitosn.events;

import io.github.joselitosn.notifications.Notification;

public interface EventListener {
    void update(String eventType, String message);
}
