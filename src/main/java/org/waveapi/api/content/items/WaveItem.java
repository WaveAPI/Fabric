package org.waveapi.api.content.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.models.ItemModel;
import org.waveapi.api.misc.Side;
import org.waveapi.api.world.entity.living.EntityPlayer;
import org.waveapi.api.world.inventory.ItemUseResult;
import org.waveapi.content.items.CustomItemWrap;
import org.waveapi.content.resources.LangManager;
import org.waveapi.content.resources.ResourcePackManager;
import org.waveapi.api.world.inventory.UseHand;

import java.util.LinkedList;

import static org.waveapi.Main.bake;

public class WaveItem {
    private final String id;
    private final WaveMod mod;

    private Item item;

    private final Item.Settings settings;

    private static LinkedList<WaveItem> toRegister = new LinkedList<>();
    public static void register() {
        for (WaveItem item : toRegister) {
            item.item = Registry.register(Registry.ITEM, new Identifier(item.mod.name, item.id), new CustomItemWrap(item.settings, item));
        }
        toRegister = null;
    }

    public WaveItem(String id, WaveMod mod) {
        this.id = id;
        this.mod = mod;
        this.settings = new Item.Settings();

        toRegister.add(this);
    }

    public ItemUseResult onUse(org.waveapi.api.world.inventory.ItemStack item, UseHand hand, EntityPlayer player) {
        return null;
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
    public void setMaxStackSize(int size) {
        settings.maxCount(size);
    }


}
