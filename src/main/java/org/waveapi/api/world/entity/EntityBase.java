package org.waveapi.api.world.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import org.waveapi.api.math.BlockPos;
import org.waveapi.api.math.Vector3;

public class EntityBase {
    public Entity entity;

    public EntityBase(Entity entity) {
        this.entity = entity;
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
        return entity.getName().asString();
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
}
