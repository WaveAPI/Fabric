package org.waveapi.content.items;

import net.minecraft.item.Item;
import org.waveapi.api.content.items.WaveItem;

import java.util.HashMap;
import java.util.Map;

public class ItemHelper {
    private static final Map<Item, WaveItem> items = new HashMap<>();
    public static WaveItem of(Item item) {
        return items.computeIfAbsent(item, WaveItem::new);
    }
}
