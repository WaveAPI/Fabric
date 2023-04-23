package org.waveapi.api.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.waveapi.api.WaveMod;
import org.waveapi.api.items.models.ItemModel;
import org.waveapi.api.items.models.SimpleItemModel;
import org.waveapi.api.misc.Side;
import org.waveapi.content.resources.LangManager;

import java.util.LinkedList;
import java.util.List;

import static org.waveapi.Main.bake;

public class WaveTab {

    private final String id;
    private final WaveMod mod;
    public final ItemGroup group;

    public List<ItemStack> items = new LinkedList<>();


    public WaveTab(String id, WaveItem item, WaveMod mod) {
        this.mod = mod;
        this.id = id;
        group = FabricItemGroup.builder(new Identifier(mod.name, id)).icon(() -> new ItemStack(item._getItem())).entries(new ItemGroup.EntryCollector() {
            @Override
            public void accept(ItemGroup.DisplayContext displayContext, ItemGroup.Entries entries) {
                entries.addAll(items);
            }
        }).build();
    }

    public WaveTab(String id, ItemModel model, WaveMod mod) {
        this(id, new WaveItem("tab_" + id + "_logo_item", mod).setModel(model), mod);
    }

    public WaveTab(String id, String modelPath, WaveMod mod) {
        this(id, new SimpleItemModel(modelPath), mod);
    }

    public WaveTab addTranslation(String language, String name) {
        if (Side.isClient() && bake) {
            LangManager.addTranslation(mod.name, language, "itemGroup." + mod.name + "." + id, name);
        }
        return this;
    }

}
