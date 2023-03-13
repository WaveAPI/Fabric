package org.waveapi.api.world.world;

public class World {
    public final net.minecraft.world.World world;

    public World(net.minecraft.world.World world) {
        this.world = world;
    }

    public boolean isClientSide() {
        return world.isClient();
    }
}