package org.waveapi.content.items.tool;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.waveapi.api.content.items.tools.WaveAxeItem;
import org.waveapi.api.world.entity.living.EntityLiving;
import org.waveapi.api.world.entity.living.EntityPlayer;
import org.waveapi.api.world.inventory.ItemUseResult;
import org.waveapi.api.world.inventory.UseHand;
import org.waveapi.content.entity.EntityHelper;

public class CustomAxeWrap extends AxeItem {

    private final WaveAxeItem item;
    public CustomAxeWrap(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings, WaveAxeItem item) {
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

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (item.onPostMine(
                new org.waveapi.api.world.inventory.ItemStack(stack),
                new org.waveapi.api.world.world.World(world),
                new org.waveapi.api.world.world.BlockState(state),
                new org.waveapi.api.math.BlockPos(pos),
                (EntityLiving) EntityHelper.wrap(miner))) {
            return super.postMine(stack, world, state, pos, miner);
        }

        return true;
    }
}
