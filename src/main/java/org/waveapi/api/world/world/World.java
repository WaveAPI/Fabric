package org.waveapi.api.world.world;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import org.waveapi.api.content.items.block.blockentities.WaveTileEntity;
import org.waveapi.api.math.BlockPos;
import org.waveapi.api.math.Vector3;
import org.waveapi.api.world.entity.EntityBase;
import org.waveapi.api.world.inventory.ItemStack;
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

    public void dropItem(Vector3 position, ItemStack stack) {
        ItemEntity entity = new ItemEntity(world, position.getX(), position.getY(), position.getZ(), stack.itemStack);
        world.spawnEntity(entity);
    }

    public void breakBlock(BlockPos pos, boolean shouldDrop) {
        world.breakBlock(pos.pos, shouldDrop);
    }

    public void breakBlock(BlockPos pos, boolean shouldDrop, EntityBase entity) {
        world.breakBlock(pos.pos, shouldDrop, entity.entity);
    }

}