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
import org.waveapi.api.world.inventory.ItemUseResult;
import org.waveapi.content.resources.LangManager;
import org.waveapi.content.resources.ResourcePackManager;
import org.waveapi.api.world.inventory.UseHand;

import static org.waveapi.Main.bake;

public class WaveItem {
    private final String id;
    private final WaveMod mod;

    private final Item item;

    public WaveItem(String id, WaveMod mod) {
        this.id = id;
        this.mod = mod;

        item = Registry.register(Registry.ITEM, new Identifier(mod.name, id),
                new Item(new Item.Settings().maxCount(getMaxStackSize())) {
                    @Override
                    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
                        ItemStack item;
                        UseHand useHand;
                        if (hand == Hand.MAIN_HAND) {
                            item = user.getMainHandStack();
                            useHand = UseHand.MAIN_HAND;
                        } else {
                            item = user.getOffHandStack();
                            useHand = UseHand.OFF_HAND;
                        }

                        ItemUseResult result = onUse(new org.waveapi.api.world.inventory.ItemStack(item), useHand);

                        if (result != null) {
                            return ItemUseResult.to(item, result);
                        } else {
                            return super.use(world, user, hand);
                        }
                    }

                });

    }

    public ItemUseResult onUse(org.waveapi.api.world.inventory.ItemStack item, UseHand hand) {
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
    public int getMaxStackSize() {return 64;}


}
