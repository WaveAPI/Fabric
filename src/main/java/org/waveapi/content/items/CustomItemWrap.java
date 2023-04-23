package org.waveapi.content.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.waveapi.api.entities.entity.living.EntityPlayer;
import org.waveapi.api.items.ItemUseResult;
import org.waveapi.api.items.UseHand;
import org.waveapi.api.items.WaveItem;

import java.util.List;

public class CustomItemWrap extends Item implements WaveItemBased {

    private final WaveItem item;
    public CustomItemWrap(WaveItem item) {
        super(item.settings);
        this.item = item;
    }

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

        ItemUseResult result = this.item.onUse(new org.waveapi.api.items.inventory.ItemStack(item), useHand, new EntityPlayer(user), new org.waveapi.api.world.World(world));

        if (result != null) {
            return ItemUseResult.to(item, result);
        } else {
            return super.use(world, user, hand);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        for (org.waveapi.api.misc.Text t : item.addToolTip(new org.waveapi.api.items.inventory.ItemStack(stack))) {
            tooltip.add(t.text);
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public WaveItem getWave() {
        return item;
    }
}
