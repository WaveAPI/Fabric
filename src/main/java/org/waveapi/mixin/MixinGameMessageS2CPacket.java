package org.waveapi.mixin;

import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.waveapi.api.events.Events;
import org.waveapi.api.events.event.message.ClientChatMessageEvent;
import org.waveapi.api.misc.Text;

import java.lang.reflect.InvocationTargetException;

@Mixin(net.minecraft.client.network.ClientPlayNetworkHandler.class)
public class MixinGameMessageS2CPacket {
    @Inject(method = "onGameMessage", at=@At("HEAD"), cancellable = true)
    public void onMessage(GameMessageS2CPacket packet, CallbackInfo ci) {
        Text text = new Text(packet.content());
        ClientChatMessageEvent e = new ClientChatMessageEvent(text);
        for (Events.EventListener listener : ClientChatMessageEvent.listeners) {
            try {
                listener.method.invoke(listener.object, e);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.isCancelled()) {
            ci.cancel();
        }
    }
}
