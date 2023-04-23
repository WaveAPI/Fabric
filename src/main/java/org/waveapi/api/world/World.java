package org.waveapi.api.world;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import org.waveapi.api.entities.entity.EntityBase;
import org.waveapi.api.items.block.blockentities.WaveTileEntity;
import org.waveapi.api.items.inventory.ItemStack;
import org.waveapi.api.math.BlockPos;
import org.waveapi.api.math.Vector3;
import org.waveapi.content.items.blocks.WaveTileEntityBased;

import java.lang.reflect.InvocationTargetException;

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

    public <T> T getTileEntity(BlockPos pos, Class<T> tClass) {
        BlockEntity entity = world.getBlockEntity(pos.pos);
        if (entity instanceof WaveTileEntityBased) {
            WaveTileEntity tile = ((WaveTileEntityBased) entity).getWaveTileEntity();
            if (tClass.isInstance(tile)) {
                return (T)tile;
            } else {
                return null;
            }
        } else {
            try {
                return (T)tClass.getMethod("of", Object.class).invoke(null, entity);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        }
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