package org.waveapi.api.content.items.armour;

import net.minecraft.entity.EquipmentSlot;

public enum ArmourSlot {
    HELMET(EquipmentSlot.HEAD),
    CHESTPLATE(EquipmentSlot.CHEST),
    LEGGINGS(EquipmentSlot.LEGS),
    BOOTS(EquipmentSlot.FEET);

    ArmourSlot(EquipmentSlot vanilla) {
        this.vanilla = vanilla;
    }

    public EquipmentSlot vanilla;
}
