package org.waveapi.api.items.block.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import org.waveapi.api.entities.entity.EntityBase;
import org.waveapi.api.entities.entity._mc.WaveEntityBased;
import org.waveapi.api.misc.NBT;
import org.waveapi.api.world.World;

import java.lang.reflect.InvocationTargetException;

public class WaveTileEntity {

    public final BlockEntity blockEntity;

    public WaveTileEntity(TileEntityCreation creation) {
        try {
            blockEntity = (BlockEntity) creation.tClass.getConstructor(BlockEntityType.class, BlockPos.class, BlockState.class, WaveTileEntity.class)
                    .newInstance(creation.entityType, creation.pos, creation.state, this);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(NBT nbt) {

    }

    public void load(NBT nbt) {

    }

    public void markNeedsSaving() {
         blockEntity.markDirty();
    }

    public World getWorld () {
        return new World(blockEntity.getWorld());
    }

    public org.waveapi.api.math.BlockPos getPosition() {
        return new org.waveapi.api.math.BlockPos(blockEntity.getPos());
    }

    public <T> T to(BlockEntityCastingType<T> type) {
        if (blockEntity instanceof WaveEntityBased) {
            EntityBase e = ((WaveEntityBased) blockEntity).getWaveEntity();
            if (type.getClazz().isInstance(e)) {
                return (T)e;
            } else {
                return null;
            }
        } else {
            return (T)type.cast(blockEntity);
        }
    }

}
