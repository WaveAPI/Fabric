package org.waveapi.api.entities.entity.living;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.waveapi.api.items.inventory.ItemStack;
import org.waveapi.api.math.Vector3;

public class EntityPlayer extends EntityLiving {

    public PlayerEntity playerEntity;

    public EntityPlayer(PlayerEntity entity) {
        super(entity);
        this.playerEntity = entity;
    }

    public void sendMessage(String message) {
        playerEntity.sendMessage(Text.of(message), false);
    }

    public void sendMessage(org.waveapi.api.misc.Text message) {
        playerEntity.sendMessage(message.text, false);
    }

    public void sendActionBar(org.waveapi.api.misc.Text message) {
        playerEntity.sendMessage(message.text, true);
    }
    public void sendActionBar(String message) {
        playerEntity.sendMessage(Text.of(message), true);
    }

    public void giveItem(ItemStack item) {
        playerEntity.giveItemStack(item.itemStack);
    }

    public Vector3 getLookVector() {
        return new Vector3(playerEntity.getRotationVector());
    }

}
