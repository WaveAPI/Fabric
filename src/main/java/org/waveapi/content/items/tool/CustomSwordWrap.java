package org.waveapi.content.items.tool;

import net.minecraft.item.SwordItem;
import org.waveapi.api.items.WaveItem;
import org.waveapi.api.items.tools.WaveCommonToolItem;

public class CustomSwordWrap extends SwordItem {

    private final WaveItem item;
    public CustomSwordWrap(WaveItem item) {
        super(((WaveCommonToolItem)item).material.material, ((WaveCommonToolItem)item).damage, ((WaveCommonToolItem)item).speed, item.settings);
        this.item = item;
    }
}
