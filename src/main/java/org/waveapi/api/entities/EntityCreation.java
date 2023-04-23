package org.waveapi.api.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import org.waveapi.api.entities.entity.EntityBase;

import java.lang.reflect.InvocationTargetException;

public class EntityCreation<E extends Entity> {

    public static <T extends EntityBase> T create(WaveEntityType<T> type, org.waveapi.api.world.World world) {
        return create(type, world.world);
    }

    public static <T extends EntityBase> T create(WaveEntityType<T> type, World world) {
        try {
            return type.entity.getDeclaredConstructor(EntityCreation.class).newInstance(new EntityCreation<>(type.entityType, world, type.entityClass));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public EntityCreation(EntityType<?> type, World world, Class<E> entity) {
        this.type = type;
        this.world = world;
        this.eClass = entity;
    }
    public Class<E> eClass;
    public EntityType<?> type;
    public World world;
}
