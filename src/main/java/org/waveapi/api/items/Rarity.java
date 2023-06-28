package org.waveapi.api.items;

import net.minecraft.enchantment.Enchantment;

public enum Rarity {
    COMMON(net.minecraft.util.Rarity.COMMON, Enchantment.Rarity.COMMON),
    UNCOMMON(net.minecraft.util.Rarity.UNCOMMON, Enchantment.Rarity.UNCOMMON),
    RARE(net.minecraft.util.Rarity.RARE, Enchantment.Rarity.RARE),
    EPIC(net.minecraft.util.Rarity.EPIC,Enchantment.Rarity.VERY_RARE);


    public final net.minecraft.util.Rarity rar;
    public final Enchantment.Rarity enchRar;

    Rarity(net.minecraft.util.Rarity rarity, Enchantment.Rarity enchRar) {
        rar = rarity;
        this.enchRar = enchRar;
    }
}
