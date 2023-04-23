package org.waveapi.api.items.block.blockentities;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.waveapi.api.items.block.WaveBlock;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public interface TileEntityBlock {
    Class<? extends WaveTileEntity> getTileEntity();

    class impl implements BlockEntityProvider {
        private final WaveBlock block = Objects.requireNonNull(null);

        public BlockEntityType<?> tileType;
        public Class<?> entityType;

        @Nullable
        @Override
        public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            TileEntityBlock bl = ((TileEntityBlock)block);
            TileEntityCreation<?> creation = new TileEntityCreation(entityType, pos, state, tileType);
            try {
                return bl.getTileEntity().getConstructor(TileEntityCreation.class).newInstance(creation).blockEntity;
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
