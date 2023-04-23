package org.waveapi.api.items.block.blockentities;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import org.waveapi.api.math.BlockPos;
import org.waveapi.api.world.BlockState;

public class TileEntityCreation<T extends BlockEntity> {


    protected final net.minecraft.block.BlockState state;
    protected final net.minecraft.util.math.BlockPos pos;
    protected final Class<T> tClass;

    protected final BlockEntityType<T> entityType;

    public TileEntityCreation (Class<T> tClass, net.minecraft.util.math.BlockPos pos, net.minecraft.block.BlockState state, BlockEntityType<T> entityType) {
        this.pos = pos;
        this.state = state;
        this.tClass = tClass;
        this.entityType = entityType;
    }

    public BlockPos getPosition() {
        return new BlockPos(pos);
    }
    public BlockState getState() {
        return new BlockState(state);
    }
}
