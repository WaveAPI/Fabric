package org.waveapi.api.world.entity.living;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.waveapi.api.content.entities.EntityCreation;
import org.waveapi.api.world.entity.DamageSource;
import org.waveapi.api.world.entity.EntityBase;

import java.lang.reflect.InvocationTargetException;

public class EntityLiving extends EntityBase {
    

    public EntityLiving(LivingEntity livingEntity) {
        super(livingEntity);
        entity = livingEntity;
    }

    public EntityLiving(EntityCreation e) {
        try {
           entity = (LivingEntity) e.eClass.getConstructor(EntityType.class, World.class, EntityBase.class).newInstance(e.type, e.world, this);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
    }

    public float getHeadYaw() {
        return ((LivingEntity)entity).headYaw;
    }

    public float getHealth() {
        return ((LivingEntity)entity).getHealth();
    }

    public void setHealth(float health) {
        ((LivingEntity)entity).setHealth(health);
    }

    public float getMaxHealth() {
        return ((LivingEntity)entity).getMaxHealth();
    }
}
