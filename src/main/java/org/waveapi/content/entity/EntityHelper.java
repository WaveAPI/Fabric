package org.waveapi.content.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.waveapi.api.entities.entity.EntityBase;
import org.waveapi.api.entities.entity.interfaces.FlyingItemBasedEntity;
import org.waveapi.api.entities.entity.living.EntityLiving;
import org.waveapi.api.entities.entity.living.EntityPlayer;
import org.waveapi.content.entity.wraps.EntityWrap;
import org.waveapi.content.entity.wraps.living.EntityLivingWrap;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EntityHelper {

    public static EntityBase wrap(Entity entity) {
        if (entity instanceof LivingEntity) {
            if (entity instanceof PlayerEntity) {
                return new EntityPlayer((PlayerEntity) entity);
            }
            return new EntityLiving((LivingEntity) entity);
        }
        return new EntityBase(entity);
    }

    public static Map<String, String> entityPossibleInterfaces;

    public static Map<String, String[]> entityPossibleBases;

    public static void populatePossibilities() {
        entityPossibleInterfaces = new HashMap<>();
        entityPossibleBases = new HashMap<>();
        entityPossibleInterfaces.put(FlyingItemBasedEntity.class.getName(), FlyingItemBasedEntity.impl.class.getName());

        entityPossibleBases.put(EntityBase.class.getName(),
                new String[]{EntityWrap.class.getName()});
        entityPossibleBases.put(EntityLiving.class.getName(),
                new String[]{EntityLivingWrap.class.getName()});
    }

    public static <T extends EntityBase> String[] searchUpBase(Class<T> entity) {

        if (entityPossibleInterfaces == null) {
            populatePossibilities();
        }

        String base = null;
        Class<?> parent = entity.getSuperclass();
        while (parent != null && base == null) {
            base = parent.getName();
            parent = parent.getSuperclass();
        }

        return entityPossibleBases.get(base);
    }

    public static <T extends EntityBase> List<String> searchUp(Class<T> entity) {
        List<String> list = new LinkedList<>();

        if (entityPossibleInterfaces == null) {
            populatePossibilities();
        }

        for (Type type : entity.getGenericInterfaces()) {
            String impl = entityPossibleInterfaces.get(type.getTypeName());
            if (impl != null) {
                list.add(impl);
            }
        }

        return list;
    }
}
