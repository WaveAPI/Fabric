package org.waveapi.api.items.inventory;

import net.minecraft.server.network.ServerPlayerEntity;
import org.waveapi.api.entities.entity.living.EntityPlayer;
import org.waveapi.api.items.WaveItem;
import org.waveapi.api.items.tags.Tag;
import org.waveapi.content.items.ItemHelper;
import org.waveapi.content.items.WaveItemBased;

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

    public WaveItem getItem() {
        return itemStack.getItem() instanceof WaveItemBased wave ? wave.getWave() : ItemHelper.of(itemStack.getItem());
    }

    public int getMaxAmount() {return itemStack.getMaxCount();}

    public boolean is(WaveItem item) {
        return itemStack.isOf(item._getItem());
    }

    public boolean isOfTag(Tag tag) {
        return tag.check(this);
    }

    public void damage(int amount, EntityPlayer player) {
        if (player.playerEntity instanceof ServerPlayerEntity) {
            boolean broken = itemStack.damage(amount, player.playerEntity.getRandom(), (ServerPlayerEntity) player.playerEntity);
            if (broken)
                itemStack.setCount(itemStack.getCount() - 1);
        }
    }

}
