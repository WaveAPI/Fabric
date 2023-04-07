package org.waveapi.content.items.etc;

import net.minecraft.item.ArmorItem;
import org.waveapi.api.content.items.WaveItem;
import org.waveapi.api.content.items.armour.WaveArmourItem;


public class CustomArmourWrap extends ArmorItem {

    private final WaveItem item;
    public CustomArmourWrap(WaveItem item) {
        super(((WaveArmourItem)item).material.getMaterial(item.getMod()), ((WaveArmourItem)item).slot.vanilla, item.settings);
        this.item = item;
    }
}
