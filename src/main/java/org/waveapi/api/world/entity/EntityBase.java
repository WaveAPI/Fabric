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
}
