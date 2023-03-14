package org.waveapi.api.content.entities;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.waveapi.api.WaveMod;
import org.waveapi.api.content.entities.renderer.WaveEntityRenderer;
import org.waveapi.api.world.entity.EntityBase;
import org.waveapi.api.world.entity.EntityGroup;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

public class WaveEntityType<T extends Entity> {

    private final WaveMod mod;
    private final Class<WaveEntityRenderer> renderer;
    private final Class<EntityBase> entity;
    private final String id;
    public EntityType<Entity> entityType;
    public EntityGroup type;
    public EntityBox box;


    private static LinkedList<WaveEntityType<?>> toRegister = new LinkedList<>();

    public static void register() {
        for (WaveEntityType<?> t : toRegister) {
            t.entityType = Registry.register(
                    Registry.ENTITY_TYPE,
                    new Identifier(t.mod.name, t.id),
                    FabricEntityTypeBuilder.create(t.type.to()).entityFactory((type, world) -> {
                        try {
                            return t.entity.getDeclaredConstructor(EntityType.class, World.class).newInstance(type, world).entity;
                        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                                 IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }).dimensions(t.box.getDimensions()).build()
            );



        }
        toRegister = null;
    }



    public WaveEntityType (String id, Class<EntityBase> entity, Class<WaveEntityRenderer> renderer, EntityGroup group, EntityBox box, WaveMod mod) {
        this.id = id;
        this.entity = entity;
        this.renderer = renderer;
        this.mod = mod;
        this.type = group;


        toRegister.add(this);
    }

    public WaveEntityType (String id, Class<EntityBase> entity, Class<WaveEntityRenderer> renderer, EntityBox box, WaveMod mod) {
        this(id, entity, renderer, EntityGroup.CREATURE, box, mod);
    }

    public WaveEntityType (String id, Class<EntityBase> entity, Class<WaveEntityRenderer> renderer,  WaveMod mod) {
        this(id, entity, renderer, new EntityBox.fixed(0.5f, 0.5f), mod);
    }



}
