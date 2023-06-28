package org.waveapi.api.items.enchantments._wrap;

import com.google.common.collect.Maps;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.waveapi.api.entities.entity.EntityBase;
import org.waveapi.api.entities.entity.living.EntityLiving;
import org.waveapi.api.items.enchantments.WaveEnchantment;

import java.util.EnumMap;
import java.util.Map;

public class EnchantmentWrapper extends Enchantment {


    private final WaveEnchantment wave;

    public EnchantmentWrapper(WaveEnchantment enchantment) {
        super(enchantment.rarity.enchRar,
                enchantment.target._mc,
                null);
        this.wave = enchantment;
    }

    @Override
    public boolean isTreasure() {
        return wave.isTreasure;
    }

    @Override
    public boolean isCursed() {
        return wave.isCursed;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return wave.villagerTrade;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return wave.enchTable;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        wave.onAttack(new EntityLiving(user), new EntityBase(target), level);
    }

    public Map<EquipmentSlot, ItemStack> getEquipment(LivingEntity entity) {
        EnumMap<EquipmentSlot, ItemStack> map = Maps.newEnumMap(EquipmentSlot.class);
        for (EquipmentSlot equipmentSlot : wave.slots) {
            ItemStack itemStack = entity.getEquippedStack(equipmentSlot);
            if (itemStack.isEmpty()) continue;
            map.put(equipmentSlot, itemStack);
        }
        return map;
    }

    @Override
    public int getMaxLevel() {
        return wave.getMaxLevel();
    }
}
