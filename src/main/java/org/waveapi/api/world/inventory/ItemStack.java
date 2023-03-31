package org.waveapi.api.world.inventory;

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

}
