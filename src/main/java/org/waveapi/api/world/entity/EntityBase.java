package org.waveapi.api.world.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.world.World;
import org.waveapi.api.math.Vector3;

public class EntityBase {
    public Entity entity;

    public EntityBase(Entity entity) {
        this.entity = entity;
    }

    public EntityBase(EntityType<?> type, World world) {
        entity = new Entity(type, world) {
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
            public Packet<?> createSpawnPacket() {
                return null;
            }
        };
    }

    public void setVelocity(Vector3 velocity) {
        entity.setVelocity(velocity.v);
    }

    public boolean isAlive() {
        return entity.isAlive();
    }

    public float getYaw() {
        return entity.getYaw();
    }
    public float getPitch() {
        return entity.getPitch();
    }

}
