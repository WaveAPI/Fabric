package org.waveapi.api.world.world;

import net.minecraft.block.entity.BlockEntity;
import org.waveapi.api.content.items.block.blockentities.WaveTileEntity;
import org.waveapi.api.math.BlockPos;
import org.waveapi.api.world.entity.EntityBase;
import org.waveapi.content.items.blocks.WaveTileEntityBased;

public class World {
    public final net.minecraft.world.World world;

    public World(net.minecraft.world.World world) {
        this.world = world;
    }

    public boolean isClientSide() {
        return world.isClient();
    }

    public void addEntity(EntityBase entity) {
        world.spawnEntity(entity.entity);
    }

    public BlockState getBlockState(BlockPos pos) {
        return new BlockState(world.getBlockState(pos.pos));
    }

    public boolean setBlockState(BlockPos pos, BlockState state) {
        return world.setBlockState(pos.pos, state.state);
    }

    public <T extends WaveTileEntity> T getTileEntity(BlockPos pos, Class<T> tClass) {
        BlockEntity entity = world.getBlockEntity(pos.pos);
        if (entity instanceof WaveTileEntityBased) {
            WaveTileEntity tile = ((WaveTileEntityBased) entity).getWaveTileEntity();
            if (tClass.isInstance(tile)) {
                return (T)tile;
            } else {
                return null;
            }
        }

        return null;
    }

}