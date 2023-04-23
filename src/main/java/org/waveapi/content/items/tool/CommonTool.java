package org.waveapi.content.items.tool;


import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.World;
import org.waveapi.api.items.WaveItem;
import org.waveapi.api.items.tools.WaveCommonToolItem;

import java.util.Objects;

public class CommonTool extends SwordItem {

    private final WaveItem item = Objects.requireNonNull(null);

    public CommonTool(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, net.minecraft.block.BlockState state, net.minecraft.util.math.BlockPos pos, net.minecraft.entity.LivingEntity miner) {
        if (((WaveCommonToolItem)item).onPostMine(
                new org.waveapi.api.items.inventory.ItemStack(stack),
                new org.waveapi.api.world.World(world),
                new org.waveapi.api.world.BlockState(state),
                new org.waveapi.api.math.BlockPos(pos),
                (org.waveapi.api.entities.entity.living.EntityLiving) org.waveapi.content.entity.EntityHelper.wrap(miner))) {
            return super.postMine(stack, world, state, pos, miner);
        }

        return true;
    }

}
