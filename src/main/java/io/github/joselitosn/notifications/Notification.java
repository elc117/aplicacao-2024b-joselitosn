package io.github.joselitosn.notifications;

/**
 * Representa uma notificação.
 */
public class Notification {
    private String title;
    private String message;

    /**
     * Construtor da classe Notification.
     * @param title O título da notificação.
     * @param message A mensagem da notificação.
     */
    public Notification(String title, String message) {
        this.title = title;
        this.message = message;
    }

    /**
     * Retorna o título da notificação.
     * @return O título da notificação.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retorna a mensagem da notificação.
     * @return A mensagem da notificação.
     */
    public String getMessage() {
        return message;
    }
}
