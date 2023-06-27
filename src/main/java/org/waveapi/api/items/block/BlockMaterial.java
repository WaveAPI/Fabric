package org.waveapi.api.items.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum BlockMaterial {
    GLASS(Blocks.GLASS),
    STONE(Blocks.STONE),
    SOIL(Blocks.DIRT);

    public final Block mat;
    BlockMaterial(Block glass) {
        mat = glass;
    }
}
