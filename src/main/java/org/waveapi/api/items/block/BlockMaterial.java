package org.waveapi.api.items.block;

import net.minecraft.block.Material;

public enum BlockMaterial {
    GLASS(Material.GLASS),
    STONE(Material.STONE),
    SOIL(Material.SOIL);

    public final Material mat;
    BlockMaterial(Material glass) {
        mat = glass;
    }
}
