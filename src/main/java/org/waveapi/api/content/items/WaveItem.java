package org.waveapi.api.content.items;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.waveapi.Main;
import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.models.ItemModel;
import org.waveapi.api.misc.Side;
import org.waveapi.api.misc.Text;
import org.waveapi.api.world.entity.living.EntityPlayer;
import org.waveapi.api.world.inventory.ItemStack;
import org.waveapi.api.world.inventory.ItemUseResult;
import org.waveapi.api.world.inventory.UseHand;
import org.waveapi.api.world.world.World;
import org.waveapi.content.items.CustomItemWrap;
import org.waveapi.content.resources.LangManager;
import org.waveapi.content.resources.ResourcePackManager;
import org.waveapi.utils.ClassHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.waveapi.Main.bake;

public class WaveItem {
    protected final String id;
    protected final WaveMod mod;

    protected Item item;

    public Item.Settings settings;

    protected static LinkedList<WaveItem> toRegister = new LinkedList<>();
    protected WaveTab tab;
    public boolean superWrap;

    protected String[] base;

    public static void register() {
        for (WaveItem item : toRegister) {
            try {
                item._registerLocal();
            } catch (Exception e) {
                throw new RuntimeException("Caused by " + item.mod.name, e);
            }
        }
        toRegister = null;
    }

    public void baseRegister() {
        Item item;
        try {
            item = (Item) ClassHelper.LoadOrGenerateCompoundClass(this.mod.getClass().getPackageName() + "." + id + "$mcItem", new ClassHelper.Generator() {
                @Override
                public String[] getBaseMethods() {
                    return base;
                }

                @Override
                public List<String> getInterfaces() {
                    return new ArrayList<>();
                }
            }, Main.bake).getConstructor(WaveItem.class).newInstance(this);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        this.item = Registry.register(Registries.ITEM, new Identifier(mod.name, id), item);
        if (tab != null) {
            tab.items.add(item.getDefaultStack());
        }
        settings = null;
    }
    
    public void _registerLocal() {
        this.base = new String[] {
                CustomItemWrap.class.getName()
        };
        baseRegister();
    }

    public WaveItem(String id, WaveMod mod) {
        this.id = id;
        this.mod = mod;
        this.settings = new Item.Settings();

        toRegister.add(this);
    }

    public void setDurability(int durability) {
        settings.maxDamage(durability);
    }

    public WaveItem(Item item) {
        Identifier identifier = Registries.ITEM.getId(item);
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
        this.tab = tab;
        return this;
    }

    public WaveMod getMod() {
        return mod;
    }

    public Item _getItem() {
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

    public List<Text> addToolTip(ItemStack stack) {
        return new ArrayList<>();
    }

}
