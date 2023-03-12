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
}
