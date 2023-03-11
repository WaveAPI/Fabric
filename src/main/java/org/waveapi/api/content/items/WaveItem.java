package org.waveapi.api.content.items;

import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import org.waveapi.api.WaveMod;

public class WaveItem {
    private final String id;

    private Item item;

    public WaveItem(String id, WaveMod mod) {
        this.id = id;

        item = Registry.register(Registry.ITEM, new Identifier(mod.name, id), new Item(new Item.Settings()));

    }

    public String getId() {
        return id;
    }
}
