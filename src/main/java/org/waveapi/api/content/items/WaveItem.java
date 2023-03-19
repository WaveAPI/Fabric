package org.waveapi.api.content.items;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.models.ItemModel;
import org.waveapi.api.misc.Side;
import org.waveapi.api.world.entity.living.EntityPlayer;
import org.waveapi.api.world.inventory.ItemStack;
import org.waveapi.api.world.inventory.ItemUseResult;
import org.waveapi.api.world.inventory.UseHand;
import org.waveapi.api.world.world.World;
import org.waveapi.content.items.CustomItemWrap;
import org.waveapi.content.resources.LangManager;
import org.waveapi.content.resources.ResourcePackManager;

import java.util.LinkedList;

import static org.waveapi.Main.bake;

public class WaveItem {
    private final String id;
    private final WaveMod mod;

    private Item item;

    private Item.Settings settings;

    private static LinkedList<WaveItem> toRegister = new LinkedList<>();
    public static void register() {
        for (WaveItem item : toRegister) {
            item.item = Registry.register(Registry.ITEM, new Identifier(item.mod.name, item.id), new CustomItemWrap(item.settings, item));
            item.settings = null;
        }
        toRegister = null;
    }

    public WaveItem(String id, WaveMod mod) {
        this.id = id;
        this.mod = mod;
        this.settings = new Item.Settings();

        toRegister.add(this);
    }

    public WaveItem(Item item) {
        Identifier identifier = Registry.ITEM.getId(item);
        this.id = identifier.getPath();
        this.mod = null; // todo: change to actual mod
    }

    public ItemUseResult onUse(org.waveapi.api.world.inventory.ItemStack item, UseHand hand, EntityPlayer player, World world) {
        return null;
    }

    public String getId() {
        return id;
    }

    public WaveItem setModel(ItemModel model) {
        if (Side.isClient() && bake) {
            model.build(ResourcePackManager.getInstance().getPackDir(), this);
        }
        return this;
    }

    public WaveItem setTab(WaveTab tab) {
        settings.group(tab.group);
        return this;
    }

    public WaveMod getMod() {
        return mod;
    }

    public Item getItem() {
        return item;
    }

    public WaveItem addTranslation(String language, String name) {
        if (Side.isClient() && bake) {
            LangManager.addTranslation(mod.name, language, "item." + mod.name + "." + id, name);
        }
        return this;
    }
    public WaveItem setMaxStackSize(int size) {
        settings.maxCount(size);
        return this;
    }

    public ItemStack getDefaultStack() {
        return new ItemStack(item.getDefaultStack());
    }

}
