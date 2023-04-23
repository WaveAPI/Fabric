package org.waveapi.api.entities;

import net.minecraft.entity.SpawnGroup;

public enum EntityGroup {
    AMBIENT,
    MISC,
    AXOLOTLS,
    CREATURE,
    MONSTER,
    UNDERGROUND_WATER_CREATURE,
    WATER_AMBIENT,
    WATER_CREATURE;

    public SpawnGroup to() {
        switch (this) {
            case AMBIENT -> {
                return SpawnGroup.AMBIENT;
            }
            case MISC -> {
                return SpawnGroup.MISC;
            }
            case AXOLOTLS -> {
                return SpawnGroup.AXOLOTLS;
            }
            case CREATURE -> {
                return SpawnGroup.CREATURE;
            }
            case MONSTER -> {
                return SpawnGroup.MONSTER;
            }
            case UNDERGROUND_WATER_CREATURE -> {
                return SpawnGroup.UNDERGROUND_WATER_CREATURE;
            }
            case WATER_AMBIENT -> {
                return SpawnGroup.WATER_AMBIENT;
            }
            case WATER_CREATURE -> {
                return SpawnGroup.WATER_CREATURE;
            }
        }
        return SpawnGroup.MISC;
    }

}
