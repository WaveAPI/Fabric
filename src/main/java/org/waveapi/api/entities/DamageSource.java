package org.waveapi.api.entities;

public class DamageSource {

    public net.minecraft.entity.damage.DamageSource source;

    public DamageSource(net.minecraft.entity.damage.DamageSource source) {
        this.source = source;
    }

    public String getName() {
        return source.getName();
    }

    public boolean doesIgnoreArmour() {
        return source.bypassesArmor();
    }

    public static final DamageSource GENERIC = new DamageSource(net.minecraft.entity.damage.DamageSource.GENERIC);
    public static final DamageSource LAVA = new DamageSource(net.minecraft.entity.damage.DamageSource.LAVA);
    public static final DamageSource IN_FIRE = new DamageSource(net.minecraft.entity.damage.DamageSource.IN_FIRE);

}
