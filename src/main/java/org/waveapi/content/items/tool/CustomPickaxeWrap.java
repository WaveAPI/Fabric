package org.waveapi.content.items.tool;

import net.minecraft.item.PickaxeItem;
import org.waveapi.api.items.WaveItem;
import org.waveapi.api.items.tools.WaveCommonToolItem;

public class CustomPickaxeWrap extends PickaxeItem {

    private final WaveItem item;
    public CustomPickaxeWrap(WaveItem item) {
        super(((WaveCommonToolItem)item).material.material, ((WaveCommonToolItem)item).damage, ((WaveCommonToolItem)item).speed, item.settings);
        this.item = item;
    }
}
