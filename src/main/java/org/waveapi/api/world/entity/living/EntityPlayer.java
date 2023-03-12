package org.waveapi.api.world.entity.living;

import net.minecraft.entity.player.PlayerEntity;

public class EntityPlayer extends EntityLiving {

    public PlayerEntity playerEntity;

    public EntityPlayer(PlayerEntity entity) {
        super(entity);
        this.playerEntity = entity;
    }
}
