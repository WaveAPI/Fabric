package org.waveapi.content.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.waveapi.api.world.entity.EntityBase;
import org.waveapi.api.world.entity.interfaces.FlyingItemBasedEntity;
import org.waveapi.api.world.entity.living.EntityLiving;
import org.waveapi.api.world.entity.living.EntityPlayer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EntityHelper {

    public static EntityBase wrap(Entity entity) {
        if (entity instanceof LivingEntity) {
            if (entity instanceof PlayerEntity) {
                return  new EntityPlayer((PlayerEntity) entity);
            }
            return new EntityLiving((LivingEntity) entity);
        }
        return new EntityBase(entity);
    }

    public static Map<String, String> entityPossibleInterfaces;

    public static void populateEntityPossibleInterfaces() {
        entityPossibleInterfaces = new HashMap<>();
        entityPossibleInterfaces.put(FlyingItemBasedEntity.class.getName(), FlyingItemBasedEntity.impl.class.getName());
    }

    public static <T extends EntityBase> List<String> searchUp(Class<T> entity) {
        List<String> list = new LinkedList<>();

        if (entityPossibleInterfaces == null) {
            populateEntityPossibleInterfaces();
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
