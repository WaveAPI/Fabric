package org.waveapi.content.entity.wraps.living;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.world.World;
import org.waveapi.api.entities.entity.EntityBase;

import java.util.ArrayList;

public class EntityLivingWrap extends LivingEntity {
    private final EntityBase entity;

    public EntityLivingWrap(EntityType<?> type, World world, EntityBase base) {
        super((EntityType<? extends LivingEntity>) type, world);
        this.entity = base;
    }


    @Override
    public Iterable<ItemStack> getArmorItems() {
        return new ArrayList<>(0);
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return Items.AIR.getDefaultStack();
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public Arm getMainArm() {
        return Arm.LEFT;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (entity.superWrap) {
            entity.superWrap = false;
            return super.damage(source, amount);
        } else {
            return entity.damage(new org.waveapi.api.entities.DamageSource(source), amount);
        }
    }

    @Override
    public void tick() {
        if (entity.superWrap) {
            entity.superWrap = false;
            super.tick();
        } else {
            entity.tick();
        }
    }

    @Override
    public boolean handleAttack(Entity attacker) {
        if (entity.superWrap) {
            entity.superWrap = false;
            return super.handleAttack(attacker);
        } else {
            return entity.handleAttack();
        }
    }
}
