package org.waveapi.api.items;

public enum EquipmentSlot {
    MAIN_HAND(net.minecraft.entity.EquipmentSlot.MAINHAND),
    OFF_HAND(net.minecraft.entity.EquipmentSlot.OFFHAND),
    HELMET(net.minecraft.entity.EquipmentSlot.HEAD),
    CHESTPLATE(net.minecraft.entity.EquipmentSlot.CHEST),
    LEGGINGS(net.minecraft.entity.EquipmentSlot.LEGS),
    BOOTS(net.minecraft.entity.EquipmentSlot.FEET);

    EquipmentSlot(net.minecraft.entity.EquipmentSlot slot) {
        this.slot = slot;
    }

    public net.minecraft.entity.EquipmentSlot slot;
}
