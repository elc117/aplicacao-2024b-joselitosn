package io.github.joselitosn.notifications;

public class NotificationHandler {
    private NotificationHandler nextHandler;

    public void setNextHandler(NotificationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void handle(Notification notification) {
        if (nextHandler != null) {
            nextHandler.handle(notification);
        }
    }
}
