package org.waveapi.api.items;

public enum Rarity {
    COMMON(net.minecraft.util.Rarity.COMMON),
    UNCOMMON(net.minecraft.util.Rarity.UNCOMMON),
    RARE(net.minecraft.util.Rarity.RARE),
    EPIC(net.minecraft.util.Rarity.EPIC);


    public final net.minecraft.util.Rarity rar;

    Rarity(net.minecraft.util.Rarity rarity) {
        rar = rarity;
    }
}
