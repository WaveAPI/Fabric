package org.waveapi.api.items.tags;

import org.waveapi.api.WaveMod;

public class Tags {
    //<editor-fold desc="Common tags">
    public static final Tag BUTTON = new SimpleCommonTag("minecraft", "buttons");
    public static final Tag LOG = new SimpleCommonTag("minecraft", "logs");
    public static final Tag PLANKS = new SimpleCommonTag("minecraft", "planks");
    public static final Tag SAND = new SimpleCommonTag("minecraft", "sand");

    public static final Tag SLAB = new SimpleCommonTag("minecraft", "slabs");

    public static final Tag STAIR = new SimpleCommonTag("minecraft", "stairs");

    public static final Tag WOOL = new SimpleCommonTag("minecraft", "wool");

    public static final Tag DIRT = new SimpleCommonTag("minecraft", "dirt");

    public static final Tag FISH = new SimpleCommonTag("minecraft", "fishes");

    public static final Tag LEAVES = new SimpleCommonTag("minecraft", "leaves");

    public static final Tag SMALL_FLOWER = new SimpleCommonTag("minecraft", "small_flowers");

    public static final Tag SAPLING = new SimpleCommonTag("minecraft", "saplings");

    public static final Tag INGOT = new SimpleCommonTag("c", "ingots");

    public static final Tag COBBLESTONE = new SimpleCommonTag("minecraft", "stone_crafting_materials");

    public static final Tag GEM = new SimpleCommonTag("c", "gems");

    //</editor-fold>


    //<editor-fold desc="Advanced tags">
    public Tag ingotCommon(String name) {
        return new SimpleCommonTag("c", name + "_ingots");
    }

    public Tag ingotModSpecific(String name, WaveMod mod) {
        return new SimpleCommonTag(mod.name, name + "_ingots");
    }

    public Tag otherCommon(String name) {
        return new SimpleCommonTag("c", name);
    }

    public Tag otherModSpecific(String name, WaveMod mod) {
        return new SimpleCommonTag(mod.name, name);
    }
    //</editor-fold>


}
