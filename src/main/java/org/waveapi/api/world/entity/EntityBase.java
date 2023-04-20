package org.waveapi.api.world.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.waveapi.api.content.entities.EntityCreation;
import org.waveapi.api.math.BlockPos;
import org.waveapi.api.math.Vector3;

import java.lang.reflect.InvocationTargetException;

public class EntityBase {
    public Entity entity;
    public boolean superWrap = false;

    public EntityBase(Entity entity) {
        this.entity = entity;
    }

    public EntityBase() {

    }

    public EntityBase(EntityCreation e) {
        try {
            entity = (Entity)e.eClass.getConstructor(EntityType.class, World.class, EntityBase.class).newInstance(e.type, e.world, this);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Vector3 getVelocity() {
        return new Vector3(entity.getVelocity());
    }
    public void setVelocity(Vector3 velocity) {
        entity.setVelocity(velocity.v);
    }

    public float getYaw() {
        return entity.getYaw();
    }
    public float getPitch() {
        return entity.getPitch();
    }

    public Vector3 getPosition() {
        return new Vector3(entity.getPos());
    }

    public void setPosition(Vector3 position) {
        entity.setPosition(position.v);
    }

    public int getID() {
        return entity.getId();
    }

    public String getName() {
        return entity.getName().getString();
    }

    public boolean isAlive() {
        return entity.isAlive();
    }

    public BlockPos getBlockLookingAt(double maxDistance, boolean includeFluids) {
        HitResult hit = entity.raycast(maxDistance, 1.0f, includeFluids);
        if (hit.getType() == HitResult.Type.BLOCK)
            return new BlockPos(((BlockHitResult) hit).getBlockPos());
        return null;
    }

    public BlockPos getBlockLookingAt(double maxDistance) {
        return getBlockLookingAt(maxDistance, false);
    }

    public void destroy() {
        entity.remove(Entity.RemovalReason.DISCARDED);
    }

    public boolean handleAttack() {
        superWrap = true;
        return entity.handleAttack(entity);
    }
    public boolean damage(DamageSource source, float amount) {
        superWrap = true;
        return entity.damage(source.source, amount);
    }

    public org.waveapi.api.world.world.World getWorld() {
        return new org.waveapi.api.world.world.World(this.entity.world);
    }

    public void tick() {
        superWrap = true;
        entity.tick();
    }
}
