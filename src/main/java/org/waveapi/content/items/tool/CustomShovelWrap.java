package org.waveapi.content.items.tool;

import net.minecraft.item.ShovelItem;
import org.waveapi.api.items.WaveItem;
import org.waveapi.api.items.tools.WaveCommonToolItem;

public class CustomShovelWrap extends ShovelItem {

    private final WaveItem item;
    public CustomShovelWrap(WaveItem item) {
        super(((WaveCommonToolItem)item).material.material, ((WaveCommonToolItem)item).damage, ((WaveCommonToolItem)item).speed, item.settings);
        this.item = item;
    }

}
