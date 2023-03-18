package org.waveapi.api.events.event.message;

import org.waveapi.MixinConfigPlugin;
import org.waveapi.api.events.Events;
import org.waveapi.api.events.event.Cancellable;
import org.waveapi.api.events.event.Event;
import org.waveapi.api.misc.Text;

import java.util.LinkedList;
import java.util.List;

public class ClientChatMessageEvent implements Event, Cancellable {
    private final Text text;
    private boolean cancelled;

    public ClientChatMessageEvent(Text text) {
        this.text = text;
    }

    public Text getText() {
        return text;
    }

    public static List<Events.EventListener> listeners;
    public static void register(Events.EventListener listener) {
        if (listeners == null) {
            listeners = new LinkedList<>();
            MixinConfigPlugin.allowedMixins.add("org.waveapi.mixin.MixinGameMessageS2CPacket");
        }
        listeners.add(listener);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public boolean setCancelled(boolean shouldCancel) {
        return cancelled;
    }
}
