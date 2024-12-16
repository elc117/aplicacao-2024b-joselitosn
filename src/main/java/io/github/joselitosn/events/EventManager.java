package io.github.joselitosn.events;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Gerenciador de eventos.
 * Permite a subscrição e notificação de ouvintes de eventos.
 */
public class EventManager {
    private Map<String, List<EventListener>> listeners = new HashMap<>();

    /**
     * Construtor do EventManager.
     * @param operations Operações suportadas pelo EventManager.
     */
    public EventManager(String... operations) {
        for (String operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }

    /**
     * Subscrição de um ouvinte de eventos.
     * @param eventType Tipo de evento.
     * @param listener Ouvinte de eventos.
     */
    public void subscribe(String eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        users.add(listener);
    }

    /**
     * Cancelar a subscrição de um ouvinte de eventos.
     * @param eventType Tipo de evento.
     * @param listener Ouvinte de eventos.
     */
    public void unsubscribe(String eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        users.remove(listener);
    }

    /**
     * Notificar os ouvintes de eventos de um determinado tipo.
     * @param eventType Tipo de evento.
     * @param message Mensagem a ser enviada aos ouvintes.
     */
    public void notify(String eventType, String message) {
        List<EventListener> users = listeners.get(eventType);
        for (EventListener listener : users) {
            listener.update(eventType, message);
        }
    }

    /**
     * Método para obter o mapa de ouvintes.
     * @return O mapa de ouvintes.
     */
    public Map<String, List<EventListener>> getListeners() { return listeners; }
}
