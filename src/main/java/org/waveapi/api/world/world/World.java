package org.waveapi.api.world.world;

import org.waveapi.api.math.BlockPos;
import org.waveapi.api.world.entity.EntityBase;

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
}