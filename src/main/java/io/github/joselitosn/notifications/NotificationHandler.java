package io.github.joselitosn.notifications;

/**
 * Classe que representa um manipulador de notificações.
 */
public class NotificationHandler {
    private NotificationHandler nextHandler;

    /**
     * Define o próximo manipulador na cadeia de responsabilidade.
     * @param nextHandler O próximo manipulador.
     */
    public void setNextHandler(NotificationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    /**
     * Método que trata uma notificação.
     * @param notification A notificação a ser tratada.
     */
    public void handle(Notification notification) {
        if (nextHandler != null) {
            nextHandler.handle(notification);
        }
    }
}
