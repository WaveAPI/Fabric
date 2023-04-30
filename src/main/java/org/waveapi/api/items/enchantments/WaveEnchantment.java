package org.waveapi.api.items.enchantments;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.waveapi.Main;
import org.waveapi.api.WaveMod;
import org.waveapi.api.entities.entity.EntityBase;
import org.waveapi.api.entities.entity.living.EntityLiving;
import org.waveapi.api.items.Rarity;
import org.waveapi.api.items.enchantments._wrap.EnchantmentWrapper;
import org.waveapi.content.resources.LangManager;

import java.util.ArrayList;
import java.util.List;

public class WaveEnchantment {

    private final String name;
    private final WaveMod mod;
    public final EnchantmentTarget target;
    public Rarity rarity = Rarity.COMMON;
    public List<net.minecraft.entity.EquipmentSlot> slots = new ArrayList<>();

    public boolean isTreasure = false;
    public boolean isCursed = false;

    private int maxLevel = 0;
    public boolean enchTable = false;
    public boolean villagerTrade = false;
    public EnchantmentWrapper _mc;

    public void _register () {
        this._mc = new EnchantmentWrapper(this);
        Registry.register(Registries.ENCHANTMENT, new Identifier(mod.name, name), _mc);
    }
    public WaveEnchantment(String name, EnchantmentTarget target, WaveMod mod) {
        this.name = name;
        this.mod = mod;
        this.target = target;

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

    public void onAttack(EntityLiving attacker, EntityBase attacked, int level) {

    }

    public WaveEnchantment addTranslation(String language, String name) {
        LangManager.addTranslation(mod.name, language,  "enchantment." + mod.name + "." + this.name, name);
        return this;
    }

    public WaveEnchantment makeTreasure() {
        isTreasure = true;
        return this;
    }
    public WaveEnchantment makeCursed() {
        isCursed = true;
        return this;
    }

    public WaveEnchantment makeAvailableInEnchantingTable() {
        this.enchTable = true;
        return this;
    }
    public WaveEnchantment makeAvailableInVillagerTrades() {
        this.villagerTrade = true;
        return this;
    }

    public WaveEnchantment setMaxLevel(int level) {
        maxLevel = level;
        return this;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

}
