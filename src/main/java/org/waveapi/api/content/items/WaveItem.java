package org.waveapi.api.content.items;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.network.NetworkSide;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.models.ItemModel;
import org.waveapi.api.mics.Side;
import org.waveapi.content.resources.LangManager;
import org.waveapi.content.resources.ResourcePackManager;

import static org.waveapi.Main.bake;

public class WaveItem {
    private final String id;
    private final WaveMod mod;

    private final Item item;

    public WaveItem(String id, WaveMod mod) {
        this.id = id;
        this.mod = mod;

        item = Registry.register(Registry.ITEM, new Identifier(mod.name, id),
                new Item(new Item.Settings().maxCount(getMaxStackSize())
                ));

    }

    public String getId() {
        return id;
    }

    public void setModel(ItemModel model) {
        if (Side.isClient() && bake) {
            model.build(ResourcePackManager.getInstance().getPackDir(), this);
        }
    }

    public WaveMod getMod() {
        return mod;
    }

    public Item getItem() {
        return item;
    }

    public void addTranslation(String language, String name) {
        if (Side.isClient() && bake) {
            LangManager.addTranslation(mod.name, language, "item." + mod.name + "." + id, name);
        }
    }
    public int getMaxStackSize() {return 64;}


}
