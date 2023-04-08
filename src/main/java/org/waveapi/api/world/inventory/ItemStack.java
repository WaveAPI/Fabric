package org.waveapi.api.world.inventory;

import net.minecraft.server.network.ServerPlayerEntity;
import org.waveapi.api.world.entity.living.EntityPlayer;

public class ItemStack {

    public final net.minecraft.item.ItemStack itemStack;
    public ItemStack (net.minecraft.item.ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public String getName() {
        return this.itemStack.getName().getString();
    }
    public int getAmount() {
        return this.itemStack.getCount();
    }
    public void setAmount(int amount) {
        this.itemStack.setCount(amount);
    }

    public void damage(int amount, EntityPlayer player) {
        if (player.playerEntity instanceof ServerPlayerEntity) {
            boolean broken = itemStack.damage(amount, player.playerEntity.getRandom(), (ServerPlayerEntity) player.playerEntity);
            if (broken)
                itemStack.setCount(itemStack.getCount() - 1);
        }
    }

}
