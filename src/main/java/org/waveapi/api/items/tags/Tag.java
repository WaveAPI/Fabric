package org.waveapi.api.items.tags;

import org.waveapi.api.items.WaveItem;
import org.waveapi.api.items.inventory.ItemStack;

public interface Tag {
    String getTagIngredient();

    void tag(WaveItem item);

    boolean check(ItemStack itemStack);
}
