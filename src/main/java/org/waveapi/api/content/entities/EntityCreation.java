package org.waveapi.api.content.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import org.waveapi.api.world.entity.EntityBase;

import java.lang.reflect.InvocationTargetException;

public class EntityCreation {

    public static <T extends EntityBase> T create(WaveEntityType<T> type, org.waveapi.api.world.world.World world) {
        return create(type, world.world);
    }

    public static <T extends EntityBase> T create(WaveEntityType<T> type, World world) {
        try {
            return type.entity.getDeclaredConstructor(EntityCreation.class).newInstance(new EntityCreation(type.entityType, world));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public EntityCreation(EntityType<?> type, World world) {
        this.type = type;
        this.world = world;
    }

    public EntityType<?> type;
    public World world;
}
