package io.github.joselitosn.events;

/**
 * Interface que define um ouvinte de eventos.
 *
 */
public interface EventListener {
    /**
     * Método chamado quando um evento ocorre.
     * @param eventType O tipo de evento.
     * @param message A mensagem do evento.
     */
    void update(String eventType, String message);
}
