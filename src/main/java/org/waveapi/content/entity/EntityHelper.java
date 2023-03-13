package org.waveapi.content.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.waveapi.api.world.entity.EntityBase;
import org.waveapi.api.world.entity.living.EntityLiving;
import org.waveapi.api.world.entity.living.EntityPlayer;

public class EntityFactory {

    public static EntityBase wrap(Entity entity) {
        if (entity instanceof LivingEntity) {
            if (entity instanceof PlayerEntity) {
                return  new EntityPlayer((PlayerEntity) entity);
            }
            return new EntityLiving((LivingEntity) entity);
        }
        return new EntityBase(entity);
    }

}
