package org.waveapi.api.kotlin.items.block.blockentities

import org.waveapi.api.items.block.blockentities.BlockEntityCastingType
import org.waveapi.api.items.block.blockentities.WaveTileEntity

fun <T> WaveTileEntity?.toType(type: BlockEntityCastingType<T>?): T? {
    return this?.to(type)
}
