package org.waveapi.api.world.entity.interfaces;

import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.item.ItemStack;
import org.waveapi.api.world.entity.EntityBase;

public interface FlyingItemBasedEntity {

    org.waveapi.api.world.inventory.ItemStack getItem();

    class impl implements FlyingItemEntity {
        private EntityBase entity;
        @Override
        public ItemStack getStack() {
            return ((FlyingItemBasedEntity)entity).getItem().itemStack;
        }
    }

}
