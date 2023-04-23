package org.waveapi.content.items.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.waveapi.api.items.block.blockentities.DeltaTicking;
import org.waveapi.api.items.block.blockentities.WaveTileEntity;
import org.waveapi.api.misc.NBT;
import org.waveapi.ticker.DeltaTicker;

import java.lang.reflect.Field;

public class TileEntityWrapper extends BlockEntity implements WaveTileEntityBased {
    private final WaveTileEntity wave;

    public TileEntityWrapper(BlockEntityType<?> type, BlockPos pos, BlockState state, WaveTileEntity tile) {
        super(type, pos, state);
        this.wave = tile;
    }

    @Override
    public WaveTileEntity getWaveTileEntity() {
        return wave;
    }

    @Override
    public void setWorld(World world) {
        if (wave instanceof DeltaTicking) {
            try {
                Field field = world.getClass().getField("ticker");

                ((DeltaTicker)field.get(world)).addTicking((DeltaTicking) wave);

                if (this.world != null) {
                    ((DeltaTicker)field.get(world)).removeTicking((DeltaTicking) wave);
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
        super.setWorld(world);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        wave.load(new NBT(nbt));
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        wave.save(new NBT(nbt));
    }
}
