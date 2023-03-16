package org.waveapi.api.content.entities;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.waveapi.Main;
import org.waveapi.api.WaveMod;
import org.waveapi.api.content.entities.renderer.WaveEntityRenderer;
import org.waveapi.api.world.entity.EntityBase;
import org.waveapi.api.world.entity.EntityGroup;
import org.waveapi.content.entity.EntityHelper;
import org.waveapi.content.entity.EntityWrap;
import org.waveapi.utils.ClassHelper;

import java.util.LinkedList;
import java.util.List;

public class WaveEntityType<T extends EntityBase> {

    private final WaveMod mod;
    private final WaveEntityRenderer renderer;
    public final Class<T> entity;
    public final Class<Entity> entityClass;

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
                    FabricEntityTypeBuilder.create(t.type.to()).entityFactory((type, world) -> EntityCreation.create(t, world).entity).dimensions(t.box.getDimensions()).build()
            );

            t.renderer.register(t);
            EntityRendererRegistry.register(t.entityType, t.renderer::getRenderer);





        }
        toRegister = null;
    }



    @SuppressWarnings("unchecked")
    public WaveEntityType (String id, Class<T> entity, WaveEntityRenderer renderer, EntityGroup group, EntityBox box, WaveMod mod) {
        this.id = id;
        this.entity = entity;
        this.renderer = renderer;
        this.mod = mod;
        this.type = group;
        this.box = box;

        entityClass = (Class<Entity>) ClassHelper.LoadOrGenerateCompoundClass(entity.getTypeName() + "BaseEntityClass", new ClassHelper.Generator<Entity>() {
            @Override
            public Class<Entity> getBaseClass() {
                return Entity.class;
            }

            @Override
            public Class<?> getBaseMethods() {
                return EntityWrap.class;
            }

            @Override
            public List<ClassHelper.InterfaceImpl> getInterfaces() {
                return EntityHelper.searchUp(entity);
            }
        }, Main.bake);

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
