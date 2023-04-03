package org.waveapi.content.items.tool;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.waveapi.api.content.items.tools.WaveSwordItem;
import org.waveapi.api.world.entity.living.EntityPlayer;
import org.waveapi.api.world.inventory.ItemUseResult;
import org.waveapi.api.world.inventory.UseHand;

public class CustomSwordWrap extends SwordItem {

    private final WaveSwordItem item;
    public CustomSwordWrap(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, WaveSwordItem item) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
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

        ItemUseResult result = this.item.onUse(new org.waveapi.api.world.inventory.ItemStack(item), useHand, new EntityPlayer(user), new org.waveapi.api.world.world.World(world));

        if (result != null) {
            return ItemUseResult.to(item, result);
        } else {
            return super.use(world, user, hand);
        }
    }

    //AUTO:COMMON_TOOL
}
