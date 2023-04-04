package org.waveapi.api.content.items.drop;

import org.waveapi.api.content.items.WaveItem;
import org.waveapi.api.content.items.block.WaveBlock;

public class DropHelper {
    public static Drop ore(WaveBlock block, WaveItem item) {
        return new SilkTouchDrop(block.getAsSimpleDrop(), new ItemDrop(item).apply(DropFunction.ORE_FORTUNE));
    }

    public static Drop ore(WaveBlock block, WaveItem item, int min, int max) {
        return new SilkTouchDrop(block.getAsSimpleDrop(), new ItemDrop(item, min, max).apply(DropFunction.ORE_FORTUNE));
    }

}
