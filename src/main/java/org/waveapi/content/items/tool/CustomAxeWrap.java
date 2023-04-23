package org.waveapi.content.items.tool;

import net.minecraft.item.AxeItem;
import org.waveapi.api.items.WaveItem;
import org.waveapi.api.items.tools.WaveCommonToolItem;


public class CustomAxeWrap extends AxeItem {

    private final WaveItem item;
    public CustomAxeWrap(WaveItem item) {
        super(((WaveCommonToolItem)item).material.material, ((WaveCommonToolItem)item).damage, ((WaveCommonToolItem)item).speed, item.settings);
        this.item = item;
    }
}
