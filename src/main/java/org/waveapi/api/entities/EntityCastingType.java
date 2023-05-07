package org.waveapi.api.entities;

public interface EntityCastingType<T> {
    Object cast(Object origin);

    Class<T> getClazz();

    interface helper {
        Object cast(Object origin);
    }

    static <T> EntityCastingType<T> create(Class<T> clazz, helper helper) {
        return new EntityCastingType<T>() {
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
