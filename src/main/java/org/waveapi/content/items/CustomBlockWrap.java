package org.waveapi.content.items;

import net.minecraft.block.Block;
import org.waveapi.api.content.items.block.WaveBlock;

public class CustomBlockWrap extends Block {
    private final WaveBlock block;

    public CustomBlockWrap(Settings settings, WaveBlock block) {
        super(settings);
        this.block = block;
    }
}