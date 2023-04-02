package org.waveapi.content.items.blocks;

import net.minecraft.block.Block;
import org.waveapi.api.content.items.block.WaveBlock;

public class CustomBlockWrap extends Block implements WaveBlockBased {
    private final WaveBlock block;

    public CustomBlockWrap(Settings settings, WaveBlock block) {
        super(settings);
        this.block = block;
    }

    @Override
    public WaveBlock getWaveBlock() {
        return block;
    }
}