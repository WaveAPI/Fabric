package org.waveapi.content.items.tool;

import net.minecraft.item.HoeItem;
import org.waveapi.api.items.WaveItem;
import org.waveapi.api.items.tools.WaveCommonToolItem;

public class CustomHoeWrap extends HoeItem {

    private final WaveItem item;
    public CustomHoeWrap(WaveItem item) {
        super(((WaveCommonToolItem)item).material.material, ((WaveCommonToolItem)item).damage, ((WaveCommonToolItem)item).speed, item.settings);
        this.item = item;
    }

}
