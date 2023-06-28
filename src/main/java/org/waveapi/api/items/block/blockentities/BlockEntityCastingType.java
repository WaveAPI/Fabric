package org.waveapi.api.items.block.blockentities;

import org.waveapi.api.entities.EntityCastingType;

public interface BlockEntityCastingType<T> {
    Object cast(Object origin);

    Class<T> getClazz();

    interface helper {
        Object cast(Object origin);
    }

    static <T> BlockEntityCastingType<T> create(Class<T> clazz, EntityCastingType.helper helper) {
        return new BlockEntityCastingType<T>() {
            @Override
            public Object cast(Object origin) {
                return helper.cast(origin);
            }

            @Override
            public Class<T> getClazz() {
                return clazz;
            }
        };
    }
}
