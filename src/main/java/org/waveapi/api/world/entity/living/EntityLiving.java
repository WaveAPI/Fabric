package org.waveapi.api.world.entity.living;

import net.minecraft.entity.LivingEntity;
import org.waveapi.api.world.entity.DamageSource;
import org.waveapi.api.world.entity.EntityBase;

public class EntityLiving extends EntityBase {

    public LivingEntity livingEntity;

    public EntityLiving(LivingEntity livingEntity) {
        super(livingEntity);
        this.livingEntity = livingEntity;
    }

    public float getHeadYaw() {
        return livingEntity.headYaw;
    }

    public float getHealth() {
        return livingEntity.getHealth();
    }

    public void setHealth(float health) {
        livingEntity.setHealth(health);
    }

    public float getMaxHealth() {
        return livingEntity.getMaxHealth();
    }

    public void damage(DamageSource source, float amount) {
        livingEntity.damage(source.source, amount);
    }

    public boolean isAlive() {
        return entity.isAlive();
    }
}
