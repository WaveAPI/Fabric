package org.waveapi.api.items.armour;

import net.minecraft.item.ArmorItem;

public enum ArmourSlot {
    HELMET(ArmorItem.Type.HELMET),
    CHESTPLATE(ArmorItem.Type.CHESTPLATE),
    LEGGINGS(ArmorItem.Type.LEGGINGS),
    BOOTS(ArmorItem.Type.BOOTS);

    ArmourSlot(ArmorItem.Type vanilla) {
        this.vanilla = vanilla;
    }

    public ArmorItem.Type vanilla;
}
