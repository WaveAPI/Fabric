package org.waveapi.utils;

import net.minecraft.item.ItemStack;

public class ItemUtils {
    public static boolean canMergeItems(ItemStack first, ItemStack second) {
        return ItemStack.canCombine(first, second);
    }
}
