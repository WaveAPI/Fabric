package org.waveapi.api.world.entity.living;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.waveapi.api.world.inventory.ItemStack;

public class EntityPlayer extends EntityLiving {

    public PlayerEntity playerEntity;

    public EntityPlayer(PlayerEntity entity) {
        super(entity);
        this.playerEntity = entity;
    }

    public void sendMessage(String message) {
        playerEntity.sendMessage(Text.of(message), false);
    }

    public void sendActionBar(String message) {
        playerEntity.sendMessage(Text.of(message), true);
    }

    public void giveItem(ItemStack item) {
        playerEntity.giveItemStack(item.itemStack);
    }
}
