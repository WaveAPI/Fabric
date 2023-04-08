package org.waveapi.content.items.blocks;

import net.minecraft.item.BlockItem;
import org.waveapi.api.content.items.WaveItem;
import org.waveapi.api.content.items.block.WaveBlock;


public class BlockItemWrap extends BlockItem {
    private final WaveItem item;
    public BlockItemWrap(WaveItem item) {
        super(((WaveBlock)item).getBlock(), item.settings);
        this.item = item;
    }
}
