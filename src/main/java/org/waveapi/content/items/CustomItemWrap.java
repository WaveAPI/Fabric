package org.waveapi.content.items;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.waveapi.api.content.items.WaveItem;
import org.waveapi.api.world.entity.living.EntityPlayer;
import org.waveapi.api.world.inventory.ItemUseResult;
import org.waveapi.api.world.inventory.UseHand;

public class CustomItemWrap extends Item {

    private final WaveItem item;
    public CustomItemWrap(Settings settings, WaveItem item) {
        super(settings);
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

        ItemUseResult result = this.item.onUse(new org.waveapi.api.world.inventory.ItemStack(item), useHand, new EntityPlayer(user));

        if (result != null) {
            return ItemUseResult.to(item, result);
        } else {
            return super.use(world, user, hand);
        }
    }


}
