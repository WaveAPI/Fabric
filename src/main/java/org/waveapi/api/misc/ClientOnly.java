package org.waveapi.api.misc;

import net.minecraft.client.MinecraftClient;
import org.waveapi.api.entities.entity.living.EntityPlayer;

public class ClientOnly {

    public static EntityPlayer getPlayer() {
        return new EntityPlayer(MinecraftClient.getInstance().player);
    }

}
