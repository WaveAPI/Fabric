package org.waveapi.utils;

import net.minecraft.item.ItemStack;

public class ItemUtils {
    public static boolean canMergeItems(ItemStack first, ItemStack second) {
        return first.isOf(second.getItem())
                && ItemStack.areNbtEqual(first, second)
                && first.getDamage() == second.getDamage();
    }
}
