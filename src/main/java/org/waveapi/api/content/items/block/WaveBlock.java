package org.waveapi.api.content.items.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.waveapi.Main;
import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.WaveTab;
import org.waveapi.api.content.items.block.model.BlockModel;
import org.waveapi.api.misc.Side;
import org.waveapi.content.items.BlockHelper;
import org.waveapi.content.items.CustomBlockWrap;
import org.waveapi.content.resources.LangManager;
import org.waveapi.content.resources.ResourcePackManager;
import org.waveapi.utils.ClassHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import static org.waveapi.Main.bake;

public class WaveBlock {
    private static Item.Settings itemSet;
    private final String id;
    private final WaveMod mod;
    private Block block;
    private AbstractBlock.Settings settings;

    private static LinkedList<WaveBlock> toRegister = new LinkedList<>();

    private Class<CustomBlockWrap> blockBase;
    private boolean hasItem = true;

    public WaveBlock(String id, WaveMod mod, BlockMaterial material) {
        this.id = id;
        this.mod = mod;
        this.settings = FabricBlockSettings.of(material.mat);
        blockBase = CustomBlockWrap.class;
        itemSet = new Item.Settings();

        toRegister.add(this);
    }

    public WaveBlock(String id, WaveMod mod) {
        this(id, mod, BlockMaterial.STONE);
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
            Block bl;
            try {
                bl = (Block) ClassHelper.LoadOrGenerateCompoundClass(block.getClass().getName() + "$mcBlock",
                        new ClassHelper.Generator<Block>() {
                            @Override
                            public Class<Block> getBaseClass() {
                                return Block.class;
                            }

                            @Override
                            public Class<?> getBaseMethods() {
                                return block.blockBase;
                            }

                            @Override
                            public List<ClassHelper.InterfaceImpl> getInterfaces() {
                                return BlockHelper.searchUp(block.getClass());
                            }
                        }
                        , Main.bake).getConstructor(AbstractBlock.Settings.class, WaveBlock.class).newInstance(block.settings, block);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            block.block = Registry.register(Registry.BLOCK, new Identifier(block.mod.name, block.id), bl);
            if (block.hasItem()) {
                Registry.register(Registry.ITEM, new Identifier(block.mod.name, block.id), new BlockItem(block.block, itemSet));
            }
        }
        toRegister = null;
    }

    public WaveBlock setTab(WaveTab tab) {
        itemSet.group(tab.group);
        return this;
    }

    public WaveBlock addTranslation(String language, String name) {
        if (Side.isClient() && bake) {
            LangManager.addTranslation(mod.name, language, "block." + mod.name + "." + id, name);
        }
        return this;
    }

    public WaveBlock setModels(BlockModel model) {
        if (Side.isClient() && bake) {
            model.buildBlock(ResourcePackManager.getInstance().getPackDir(), this, true, true, "");
        }
        return this;
    }

    public boolean hasItem() {
        return hasItem;
    }

    public WaveBlock setHasItem(boolean hasItem) {
        this.hasItem = hasItem;
        return this;
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