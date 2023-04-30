package org.waveapi.api.items.enchantments;

public enum EnchantmentTarget {
    BREAKABLE(net.minecraft.enchantment.EnchantmentTarget.BREAKABLE),
    SWORD(net.minecraft.enchantment.EnchantmentTarget.WEAPON),
    ARMOUR(net.minecraft.enchantment.EnchantmentTarget.ARMOR);
    public final net.minecraft.enchantment.EnchantmentTarget _mc;

    EnchantmentTarget(net.minecraft.enchantment.EnchantmentTarget _mc) {
        this._mc = _mc;
    }
}
