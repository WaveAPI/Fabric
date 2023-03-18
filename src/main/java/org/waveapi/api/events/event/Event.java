package org.waveapi.api.events.event;

import org.waveapi.api.events.Events;

public interface Event {
    static void register(Events.EventListener listener) {
        throw new RuntimeException("Someone tried to listen to Event event, which you should never do.");
    }
}
