package org.waveapi.api.items;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.waveapi.Main;
import org.waveapi.api.WaveMod;
import org.waveapi.api.entities.entity.living.EntityPlayer;
import org.waveapi.api.items.inventory.ItemStack;
import org.waveapi.api.items.models.ItemModel;
import org.waveapi.api.items.tags.Tag;
import org.waveapi.api.misc.Side;
import org.waveapi.api.misc.Text;
import org.waveapi.api.world.World;
import org.waveapi.content.items.CustomItemWrap;
import org.waveapi.content.resources.LangManager;
import org.waveapi.content.resources.ResourcePackManager;
import org.waveapi.utils.ClassHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.waveapi.Main.bake;

public class WaveItem {
    protected final String id;
    protected final WaveMod mod;

    protected Item item;

    public Item.Settings settings;
    protected WaveTab tab;

    protected String[] base;

    public WaveItem tag(Tag tag) {
        tag.tag(this);
        return this;
    }

    //<editor-fold desc="Item register code and constructors.">
    protected static LinkedList<WaveItem> toRegister = new LinkedList<>();

    public static void register() {
        Set<String> registered = new HashSet<>(toRegister.size());
        for (WaveItem item : toRegister) {
            if (registered.contains(item.id)) {
                throw new RuntimeException("Mod [" + item.mod.name + "] tried to register [" + item.id + "] twice.");
            }
            try {
                item._registerLocal();
                registered.add(item.id);
            } catch (Exception e) {
                throw new RuntimeException("Caused by " + item.mod.name, e);
            }
        }
        toRegister = null;
    }

    public void baseRegister() {
        Item item;
        try {
            item = (Item) ClassHelper.LoadOrGenerateCompoundClass(new ClassHelper.Generator() {

                @Override
                public String getName() {
                    return base[0] + "$mc_class";
                }

                @Override
                public String[] getBaseMethods() {
                    return base;
                }

                @Override
                public List<String> getInterfaces() {
                    return new ArrayList<>();
                }
            }).getConstructor(WaveItem.class).newInstance(this);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        this.item = Registry.register(Registry.ITEM, new Identifier(mod.name, id), item);
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

    public WaveItem(Item item) {
        Identifier identifier = Registry.ITEM.getId(item);
        this.id = identifier.getPath();
        this.mod = null; // todo: change to actual mod
        this.item = item;
    }
    //</editor-fold>

    public void setDurability(int durability) {
        settings.maxDamage(durability);
    }

    //<editor-fold desc="Code needed for food to work">
    public WaveItem makeFood(int hunger, float saturation) {
        return this.makeFood(hunger, saturation, false, false, false);
    }
    public WaveItem makeFood(int hunger, float saturation, boolean isAlwaysEdible) {
        return this.makeFood(hunger, saturation, isAlwaysEdible, false, false);
    }


        public WaveItem makeFood(int hunger, float saturation, boolean isAlwaysEdible, boolean isMeat, boolean isASnack) {
        FoodComponent.Builder builder = new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation);

        if (isAlwaysEdible) {
            builder.alwaysEdible();
        }
        if (isMeat) {
            builder.meat();
        }
        if (isASnack) {
            builder.snack();
        }


        settings.food(builder.build());
        return this;
    }
    //</editor-fold>


    public WaveItem setRarity(Rarity rarity) {
        settings.rarity(rarity.rar);
        return this;
    }

    public ItemUseResult onUse(org.waveapi.api.items.inventory.ItemStack item, UseHand hand, EntityPlayer player, World world) {
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

    public int getMaxAmount() {return item.getMaxCount();}

}
