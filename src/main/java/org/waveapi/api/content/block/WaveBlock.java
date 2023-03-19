package org.waveapi.api.content.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.waveapi.api.WaveMod;
import org.waveapi.content.items.CustomBlockWrap;

import java.util.LinkedList;

public class WaveBlock {
    private final String id;
    private final WaveMod mod;
    private Block block;
    private AbstractBlock.Settings settings;

    private static LinkedList<WaveBlock> toRegister = new LinkedList<>();

    public WaveBlock(String id, WaveMod mod) {
        this.id = id;
        this.mod = mod;
        this.settings = FabricBlockSettings.of(Material.METAL);

        toRegister.add(this);
    }

    public WaveBlock(Block block) {
        this.block = block;
        Identifier identifier = Registry.BLOCK.getId(block);
        toRegister.add(this);
        mod = null;  // todo: change to actual mod
        id = identifier.getPath();
    }

    public static void register() {
        for (WaveBlock block : toRegister) {
            block.block = Registry.register(Registry.BLOCK, new Identifier(block.mod.name, block.id), new CustomBlockWrap(block.settings, block));
        }
        toRegister = null;
    }

    public String getId() {
        return id;
    }

    public WaveMod getMod() {
        return mod;
    }

    public Block getBlock() {
        return block;
    }
}