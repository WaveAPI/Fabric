package org.waveapi.content.items.blocks;

import net.minecraft.block.Block;
import org.waveapi.api.items.block.WaveBlock;
import org.waveapi.api.items.block.blockentities.TileEntityBlock;
import org.waveapi.api.items.block.blockentities.WaveTileEntity;
import org.waveapi.api.items.block.blockentities.types.ContainerTile;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BlockHelper {

    private static final Map<Block, WaveBlock> blocks = new HashMap<>();
    public static WaveBlock of(Block block) {
        return blocks.computeIfAbsent(block, WaveBlock::new);
    }
    public static Map<String, String> blockPossibleInterfaces;
    public static Map<String, String> tilePossibleInterfaces;

    public static void populateBlockPossibleInterfaces() {
        blockPossibleInterfaces = new HashMap<>();
        blockPossibleInterfaces.put(TileEntityBlock.class.getName(), TileEntityBlock.impl.class.getName());
    }

    public static <T extends WaveBlock> List<String> searchUp(Class<T> block) {
        List<String> list = new LinkedList<>();

        if (blockPossibleInterfaces == null) {
            populateBlockPossibleInterfaces();
        }

        for (Type type : block.getGenericInterfaces()) {
            String impl = blockPossibleInterfaces.get(type.getTypeName());
            if (impl != null) {
                list.add(impl);
            }
        }

        return list;
    }

    public static <T extends WaveTileEntity> List<String> searchUpTile(Class<T> block) {
        List<String> list = new LinkedList<>();

        if (tilePossibleInterfaces == null) {
            populateTilePossibleInterfaces();
        }

        for (Type type : block.getGenericInterfaces()) {
            String impl = tilePossibleInterfaces.get(type.getTypeName());
            if (impl != null) {
                list.add(impl);
            }
        }

        return list;
    }

    private static void populateTilePossibleInterfaces() {
        tilePossibleInterfaces = new HashMap<>();
        tilePossibleInterfaces.put(ContainerTile.class.getName(), ContainerTile.impl.class.getName());
    }
}
