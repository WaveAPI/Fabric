package org.waveapi.api.items.enchantments;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.waveapi.Main;
import org.waveapi.api.WaveMod;
import org.waveapi.api.entities.entity.EntityBase;
import org.waveapi.api.entities.entity.living.EntityLiving;
import org.waveapi.api.items.ItemAccepter;
import org.waveapi.api.items.Rarity;
import org.waveapi.api.items.enchantments._wrap.EnchantmentWrapper;

import java.util.ArrayList;
import java.util.List;

public class WaveEnchantment {

    private final String name;
    private final WaveMod mod;
    public Rarity rarity = Rarity.COMMON;
    public List<net.minecraft.entity.EquipmentSlot> slots = new ArrayList<>();

    public boolean isTreasure = false;
    public boolean isCursed = false;

    public List<ItemAccepter> accepters = new ArrayList<>();
    private int maxLevel = 0;

    public void _register () {
        Registry.register(Registries.ENCHANTMENT, new Identifier(mod.name, name), new EnchantmentWrapper(this));
    }
    public WaveEnchantment(String name, WaveMod mod) {
        this.name = name;
        this.mod = mod;

        Main.postInit.add(this::_register);

    }

    public WaveEnchantment setRarity(Rarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public WaveEnchantment addEquipmentSlot(org.waveapi.api.items.EquipmentSlot slot) {
        slots.add(slot.slot);
        return this;
    }

    public WaveEnchantment addAllowedItemsAccepter(ItemAccepter accepter) {
        accepters.add(accepter);
        return this;
    }

    public void onUsedToAttack(EntityLiving attacker, EntityBase attacked, int level) {

    }

    public WaveEnchantment makeTreasure() {
        isTreasure = true;
        return this;
    }
    public WaveEnchantment makeCursed() {
        isCursed = true;
        return this;
    }

    public WaveEnchantment makeAvailableInEnchantingTable() {return this;}
    public WaveEnchantment makeAvailableInVillagerTrades() {return this;}

    public WaveEnchantment setMaxLevel(int level) {
        maxLevel = level;
        return this;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

}
