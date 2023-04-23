package org.waveapi.api.entities.entity.interfaces;

import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.item.ItemStack;
import org.waveapi.api.entities.entity.EntityBase;

public interface FlyingItemBasedEntity {

    org.waveapi.api.items.inventory.ItemStack getItem();

    class impl implements FlyingItemEntity {
        private EntityBase entity;
        @Override
        public ItemStack getStack() {
            return ((FlyingItemBasedEntity)entity).getItem().itemStack;
        }
    }

}
