package org.waveapi.api.content.entities;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.waveapi.api.WaveMod;
import org.waveapi.api.content.entities.renderer.WaveEntityRenderer;
import org.waveapi.api.world.entity.EntityBase;
import org.waveapi.api.world.entity.EntityGroup;

import java.util.LinkedList;

public class WaveEntityType<T extends EntityBase> {

    private final WaveMod mod;
    private final WaveEntityRenderer renderer;
    public final Class<T> entity;

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
                        return EntityCreation.create(t, world).entity;
                    }).dimensions(t.box.getDimensions()).build()
            );

            EntityRendererRegistry.register(t.entityType, t.renderer::getRenderer);





        }
        toRegister = null;
    }



    public WaveEntityType (String id, Class<T> entity, WaveEntityRenderer renderer, EntityGroup group, EntityBox box, WaveMod mod) {
        this.id = id;
        this.entity = entity;
        this.renderer = renderer;
        this.mod = mod;
        this.type = group;
        this.box = box;


        toRegister.add(this);
    }

    public WaveEntityType (String id, Class<T> entity, WaveEntityRenderer renderer, EntityBox box, WaveMod mod) {
        this(id, entity, renderer, EntityGroup.CREATURE, box, mod);
    }

    public WaveEntityType (String id, Class<T> entity, WaveEntityRenderer renderer,  WaveMod mod) {
        this(id, entity, renderer, new EntityBox.fixed(0.5f, 0.5f), mod);
    }

    public String getId() {
        return id;
    }

    public WaveMod getMod() {
        return mod;
    }


}
