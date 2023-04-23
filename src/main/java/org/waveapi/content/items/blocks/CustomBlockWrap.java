package org.waveapi.content.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.waveapi.api.entities.entity.living.EntityPlayer;
import org.waveapi.api.items.ItemUseResult;
import org.waveapi.api.items.UseHand;
import org.waveapi.api.items.block.WaveBlock;
import org.waveapi.api.world.World;

public class CustomBlockWrap extends Block implements WaveBlockBased {
    private final WaveBlock block;

    public CustomBlockWrap(Settings settings, WaveBlock block) {
        super(settings);
        this.block = block;
    }

    @Override
    public WaveBlock getWaveBlock() {
        return block;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        block.onRandomTick(new org.waveapi.api.world.BlockState(state),
                new org.waveapi.api.math.BlockPos(pos),
                new World(world));
    }

    @Override
    public ActionResult onUse(BlockState state, net.minecraft.world.World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        UseHand useHand;
        if (hand == Hand.MAIN_HAND) {
            useHand = UseHand.MAIN_HAND;
        } else {
            useHand = UseHand.OFF_HAND;
        }

        ItemUseResult result = this.block.onUse(new org.waveapi.api.world.BlockState(state), new org.waveapi.api.math.BlockPos(pos), new org.waveapi.api.world.World(world), new EntityPlayer(player), useHand);

        if (result != null) {
            return ItemUseResult.to(result);
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }
}