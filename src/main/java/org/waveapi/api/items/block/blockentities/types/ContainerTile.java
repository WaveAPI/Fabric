package org.waveapi.api.items.block.blockentities.types;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.waveapi.api.items.block.blockentities.WaveTileEntity;
import org.waveapi.utils.ItemUtils;

public interface ContainerTile {

    int getSize();
    org.waveapi.api.items.inventory.ItemStack getStack(int slot);
    void setStack(int slot, org.waveapi.api.items.inventory.ItemStack stack);

    default org.waveapi.api.items.inventory.ItemStack take(int slot, int amount) {
        if (slot < 0 || slot >= getSize() || getStack(slot).itemStack.isEmpty() || amount <= 0) {
            return new org.waveapi.api.items.inventory.ItemStack(ItemStack.EMPTY);
        }
        ((WaveTileEntity)this).markNeedsSaving();
        return new org.waveapi.api.items.inventory.ItemStack(getStack(slot).itemStack.split(amount));
    }

    default int giveItem(org.waveapi.api.items.inventory.ItemStack stack) {
        ItemStack s = stack.itemStack;
        int original_count = s.getCount();
        int count = s.getCount();
        for (int i = 0 ; i < getSize() ; i++) {
            if (count == 0) {
                return original_count;
            }

            ItemStack slot = getStack(i).itemStack;
            if (slot == ItemStack.EMPTY || slot.isEmpty()) {
                s.setCount(count);
                setStack(i, new org.waveapi.api.items.inventory.ItemStack(s));
                return original_count;
            }
            if (ItemUtils.canMergeItems(slot, s)) {
                int amount = Math.min(count, slot.getMaxCount() - slot.getCount());
                slot.setCount(slot.getCount() + amount);
                count -= amount;
            }
        }
        return original_count - count;
    }

    default org.waveapi.api.items.inventory.ItemStack take(int slot) {
        org.waveapi.api.items.inventory.ItemStack stack = getStack(slot);
        setStack(slot, new org.waveapi.api.items.inventory.ItemStack(ItemStack.EMPTY));
        return stack;
    }

    class impl implements Inventory {
        private WaveTileEntity tile;
        @Override
        public int size() {
            return ((ContainerTile)tile).getSize();
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public ItemStack getStack(int slot) {
            return ((ContainerTile)tile).getStack(slot).itemStack;
        }

        @Override
        public ItemStack removeStack(int slot, int amount) {
            return ((ContainerTile)tile).take(slot, amount).itemStack;
        }

        @Override
        public ItemStack removeStack(int slot) {
            return ((ContainerTile)tile).take(slot).itemStack;
        }

        @Override
        public void setStack(int slot, ItemStack stack) {
            ((ContainerTile)tile).setStack(slot, new org.waveapi.api.items.inventory.ItemStack(stack));
        }

        @Override
        public void markDirty() {
            tile.markNeedsSaving();
        }

        @Override
        public boolean canPlayerUse(PlayerEntity player) {
            return false;
        }

        @Override
        public void clear() {
            for (int i = 0 ; i < size() ; i++) {
                setStack(i, ItemStack.EMPTY);
            }
        }
    }

    static ContainerTile of(Object o) {
        if (o instanceof Inventory inventory) {
            return new ContainerTile() {

                @Override
                public org.waveapi.api.items.inventory.ItemStack take(int slot, int amount) {
                    return new org.waveapi.api.items.inventory.ItemStack(inventory.removeStack(slot, amount));
                }

                @Override
                public org.waveapi.api.items.inventory.ItemStack take(int slot) {
                    return new org.waveapi.api.items.inventory.ItemStack(inventory.removeStack(slot));
                }

                @Override
                public int getSize() {
                    return inventory.size();
                }

                @Override
                public org.waveapi.api.items.inventory.ItemStack getStack(int slot) {
                    return new org.waveapi.api.items.inventory.ItemStack(inventory.getStack(slot));
                }

                @Override
                public void setStack(int slot, org.waveapi.api.items.inventory.ItemStack stack) {
                    inventory.setStack(slot, stack.itemStack);
                }
            };
        }
        return null;
    }

}
