package org.waveapi.api.items.block.model;

import org.waveapi.api.items.block.WaveBlock;
import org.waveapi.api.items.models.ItemModel;

import java.io.File;

public abstract class BlockModel extends ItemModel {
    public abstract void buildBlock(File pack, WaveBlock block, boolean buildItem, boolean buildBlockState, String additional);
}
