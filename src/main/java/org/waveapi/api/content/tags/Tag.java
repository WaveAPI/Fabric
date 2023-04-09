package org.waveapi.api.content.tags;

import org.waveapi.api.content.items.WaveItem;
import org.waveapi.api.world.inventory.ItemStack;

public interface Tag {
    String getTagIngredient();

    void tag(WaveItem item);

    boolean check(ItemStack itemStack);
}
