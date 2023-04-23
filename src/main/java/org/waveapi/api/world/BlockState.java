package org.waveapi.api.world;

import net.minecraft.block.Block;
import org.waveapi.api.items.block.WaveBlock;
import org.waveapi.content.items.blocks.BlockHelper;
import org.waveapi.content.items.blocks.WaveBlockBased;

public class BlockState {

    public net.minecraft.block.BlockState state;

    public BlockState(net.minecraft.block.BlockState state) {
        this.state = state;
    }

    public WaveBlock getBlock() {
        Block block = state.getBlock();
        if (block instanceof WaveBlockBased) {
            return ((WaveBlockBased) block).getWaveBlock();
        } else {
            return BlockHelper.of(block);
        }
    }

}
