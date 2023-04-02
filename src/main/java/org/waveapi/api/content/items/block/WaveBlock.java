package org.waveapi.api.content.items.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.waveapi.Main;
import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.WaveItem;
import org.waveapi.api.content.items.WaveTab;
import org.waveapi.api.content.items.block.blockentities.TileEntityBlock;
import org.waveapi.api.content.items.block.blockentities.TileEntityCreation;
import org.waveapi.api.content.items.block.model.BlockModel;
import org.waveapi.api.content.items.drop.Drop;
import org.waveapi.api.content.items.drop.ItemDrop;
import org.waveapi.api.math.BlockPos;
import org.waveapi.api.misc.Side;
import org.waveapi.api.world.entity.living.EntityPlayer;
import org.waveapi.api.world.inventory.ItemUseResult;
import org.waveapi.api.world.inventory.UseHand;
import org.waveapi.api.world.world.BlockState;
import org.waveapi.api.world.world.World;
import org.waveapi.content.items.blocks.BlockHelper;
import org.waveapi.content.items.blocks.CustomBlockWrap;
import org.waveapi.content.items.blocks.TileEntityWrapper;
import org.waveapi.content.resources.LangManager;
import org.waveapi.content.resources.ResourcePackManager;
import org.waveapi.content.resources.TagHelper;
import org.waveapi.utils.ClassHelper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
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
    private WaveTab tab;
    private WaveItem item;

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
        Identifier identifier = Registries.BLOCK.getId(block);
        mod = null;  // todo: change to actual mod
        id = identifier.getPath();
    }

    @SuppressWarnings("unchecked")
    public static void register() {
        for (WaveBlock block : toRegister) {
            Block bl;
            try {
                bl = (Block) ClassHelper.LoadOrGenerateCompoundClass(block.getClass().getName() + "$mcBlock",
                        new ClassHelper.Generator() {
                            @Override
                            public String[] getBaseMethods() {
                                return new String[] {block.blockBase.getName()};
                            }

                            @Override
                            public List<String> getInterfaces() {
                                return BlockHelper.searchUp(block.getClass());
                            }
                        }
                        , Main.bake).getConstructor(AbstractBlock.Settings.class, WaveBlock.class).newInstance(block.settings, block);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }

            block.block = Registry.register(Registries.BLOCK, new Identifier(block.mod.name, block.id), bl);
            if (block.hasItem()) { // TODO: replace after creating WaveBlockItem
                Item item = Registry.register(Registries.ITEM, new Identifier(block.mod.name, block.id), new BlockItem(block.block, itemSet));
                if (block.tab != null) {
                    block.tab.items.add(item.getDefaultStack());
                }
                block.item = new WaveItem(item);
            }
            if (block instanceof TileEntityBlock) {
                try {
                    Field type = block.block.getClass().getField("tileType");
                    Field entityType = block.block.getClass().getField("entityType");
                    final Class<? extends BlockEntity> tile = (Class<? extends BlockEntity>) ClassHelper.LoadOrGenerateCompoundClass(block.getClass().getName() + "$mcTile",
                            new ClassHelper.Generator() {
                                @Override
                                public String[] getBaseMethods() {
                                    return new String[] {TileEntityWrapper.class.getName()};
                                }

                                @Override
                                public List<String> getInterfaces() {
                                    return BlockHelper.searchUpTile(((TileEntityBlock) block).getTileEntity());
                                }
                            }
                            , Main.bake);
                    entityType.set(block.block, tile);

                    BlockEntityType<BlockEntity> entity = Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(block.mod.name, block.id + "_tile"),
                            FabricBlockEntityTypeBuilder.create(
                                    (pos, state) -> {
                                        try {
                                            TileEntityCreation creation = new TileEntityCreation(tile, pos, state, (BlockEntityType) type.get(block.block));
                                            return ((TileEntityBlock) block).getTileEntity().getConstructor(TileEntityCreation.class).newInstance (creation).blockEntity;
                                        } catch (IllegalAccessException | InvocationTargetException |
                                                 InstantiationException | NoSuchMethodException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    , block.block).build()
                            );

                    type.set(block.block, entity);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        toRegister = null;
    }

    public void enableRandomTick() {
        settings.ticksRandomly();
    }
    public void onRandomTick(BlockState state, BlockPos pos, World world) {

    }

    public WaveBlock setTab(WaveTab tab) {
        this.tab = tab;
        return this;
    }

    public WaveBlock setHardness(float hardness) {
        this.settings.hardness(hardness);
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

    public BlockState getDefaultState() {
        return new BlockState(block.getDefaultState());
    }

    public WaveBlock setDrop() {
        return setDrop(new Drop[] {new ItemDrop(this.mod.name + ":" + this.id)});
    }

    public WaveItem getItem() {
        return item;
    }

    public ItemDrop getAsSimpleDrop() {
        return new ItemDrop(mod.name + ":" + id);
    }

    public WaveBlock setDrop(Drop[] drop) {
        if (!bake) {
            return this;
        }
        File file = new File(ResourcePackManager.getInstance().getPackDir(), "data/" + mod.name + "/loot_tables/blocks/" + this.id + ".json");
        file.getParentFile().mkdirs();
        StringBuilder builder = new StringBuilder("{\n" +
                "  \"type\": \"minecraft:block\",\n" +
                "  \"pools\": [\n" +
                "    {\n" +
                "      \"rolls\": 1.0,\n" +
                "      \"bonus_rolls\": 0.0,\n" +
                "      \"entries\": [\n");
        for (int i = 0 ; i < drop.length ; i++) {
            drop[i].write(builder);
            if (i < drop.length - 1) {
                builder.append(",");
            }
        }
        builder.append("""
                            ]
                        }
                    ]
                }""");


        try {
            Files.write(file.toPath(), builder.toString().getBytes(), StandardOpenOption.CREATE_NEW);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return this;
    }

    public WaveBlock setMiningLevelRequired(int level) {
        if (level > 0) {
            settings.requiresTool();
        }
        if (!bake) return this;
        TagHelper.addTag("fabric", "blocks/needs_tool_level_" + level, this.mod.name + ":" + this.id);
        return this;
    }

    public WaveBlock makePickaxeEffective() {
        if (!bake) return this;
        TagHelper.addTag("minecraft", "blocks/mineable/pickaxe", this.mod.name + ":" + this.id);
        return this;
    }

    public WaveBlock makeAxeEffective() {
        if (!bake) return this;
        TagHelper.addTag("minecraft", "blocks/mineable/axe", this.mod.name + ":" + this.id);
        return this;
    }

    public WaveBlock makeShovelEffective() {
        if (!bake) return this;
        TagHelper.addTag("minecraft", "blocks/mineable/shovel", this.mod.name + ":" + this.id);
        return this;
    }

    public WaveBlock makeHoeEffective() {
        if (!bake) return this;
        TagHelper.addTag("minecraft", "blocks/mineable/hoe", this.mod.name + ":" + this.id);
        return this;
    }

    public ItemUseResult onUse(BlockState blockState, BlockPos pos, World world, EntityPlayer entityPlayer, UseHand useHand) {
        return null;
    }
}