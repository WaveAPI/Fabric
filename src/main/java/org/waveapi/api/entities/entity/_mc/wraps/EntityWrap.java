package org.waveapi.api.entities.entity._mc.wraps;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.waveapi.api.entities.entity.EntityBase;

public class EntityWrap extends Entity {

    public final EntityBase entity;
    public EntityWrap(EntityType<?> type, World world, EntityBase base) {
        super(type, world);
        this.entity = base;
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }


    @Override
    public boolean damage(DamageSource source, float amount) {
        if (entity.superWrap) {
            entity.superWrap = false;
            return super.damage(source, amount);
        } else {
            return entity.damage(new org.waveapi.api.entities.DamageSource(source), amount);
        }
    }

    @Override
    public void tick() {
        if (entity.superWrap) {
            entity.superWrap = false;
            super.tick();
        } else {
            entity.tick();
        }
    }

    @Override
    public boolean handleAttack(Entity attacker) {
        if (entity.superWrap) {
            entity.superWrap = false;
            return super.handleAttack(attacker);
        } else {
            return entity.handleAttack();
        }
    }
}
