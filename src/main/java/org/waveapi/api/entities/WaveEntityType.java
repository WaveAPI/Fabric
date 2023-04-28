package org.waveapi.api.entities;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.waveapi.api.WaveMod;
import org.waveapi.api.entities.entity.EntityBase;
import org.waveapi.api.entities.entity.living.EntityLiving;
import org.waveapi.api.entities.renderer.WaveEntityRenderer;
import org.waveapi.api.misc.Side;
import org.waveapi.content.entity.EntityHelper;
import org.waveapi.utils.ClassHelper;

import java.util.LinkedList;
import java.util.List;

public class WaveEntityType<T extends EntityBase> {

    private final WaveMod mod;
    public final Class<T> entity;
    public final Class<Entity> entityClass;

    private final String id;
    private final FabricEntityTypeBuilder<Entity> preregister;
    public EntityType<? extends Entity> entityType;
    public EntityGroup type;
    public EntityBox box;


    private static LinkedList<WaveEntityType<?>> toRegister = new LinkedList<>();

    public static void register() {
        for (WaveEntityType<?> t : toRegister) {
            t.entityType = Registry.register(
                    Registries.ENTITY_TYPE,
                    new Identifier(t.mod.name, t.id),
                    t.preregister.build()
            );

            if (EntityLiving.class.isAssignableFrom(t.entity)) {
                FabricDefaultAttributeRegistry.register((EntityType<? extends LivingEntity>) t.entityType,
                        LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10)
                        );
            }

            if (Side.isClient()) {
                t.getEntityRenderer().register(t);
                EntityRendererRegistry.register(t.entityType, t.getEntityRenderer()::getRenderer);
            }

        }
        toRegister = null;
    }

    public WaveEntityRenderer getEntityRenderer() {
        return new WaveEntityRenderer();
    }


    @SuppressWarnings("unchecked")
    public WaveEntityType (String id, Class<T> entity, EntityGroup group, EntityBox box, WaveMod mod) {
        this.id = id;
        this.entity = entity;
        this.mod = mod;
        this.type = group;
        this.box = box;

        this.preregister = FabricEntityTypeBuilder.create(type.to()).entityFactory((type, world) -> EntityCreation.create(this, world).entity).dimensions(box.getDimensions());

        entityClass = (Class<Entity>) ClassHelper.LoadOrGenerateCompoundClass(new ClassHelper.Generator() {
            @Override
            public String[] getBaseMethods() {
                return EntityHelper.searchUpBase(entity);
            }

            @Override
            public List<String> getInterfaces() {
                return EntityHelper.searchUp(entity);
            }
        });

        toRegister.add(this);
    }

    public void setMaxTrackingRange(int range) {
        this.preregister.trackRangeBlocks(range);
    }

    public WaveEntityType (String id, Class<T> entity, EntityBox box, WaveMod mod) {
        this(id, entity, EntityGroup.CREATURE, box, mod);
    }

    public WaveEntityType (String id, Class<T> entity,  WaveMod mod) {
        this(id, entity, new EntityBox.fixed(0.5f, 0.5f), mod);
    }

    public String getId() {
        return id;
    }

    public WaveMod getMod() {
        return mod;
    }


}
