package io.github.joselitosn.events;

/**
 * Interface que define um ouvinte de eventos.
 *
 * @param eventType O tipo de evento.
 * @param message A mensagem do evento.
 */
public interface EventListener {
    /** Método chamado quando um evento ocorre. */
    void update(String eventType, String message);
}
