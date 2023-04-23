package org.waveapi.api.entities;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public class DamageSource {

    public final RegistryKey<DamageType> type;
    private net.minecraft.entity.damage.DamageSource source;

    public DamageSource(net.minecraft.entity.damage.DamageSource source) {
        this.source = source;
        this.type = null;
    }

    public DamageSource(RegistryKey<DamageType> type) {
        this.type = type;
    }

    public net.minecraft.entity.damage.DamageSource getSource(World world) {
        if (source == null) {
            source = new net.minecraft.entity.damage.DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).getEntry(type).orElse(null));
        }
        return source;
    }


    public String getName() {
        return source.getName();
    }

    public static final DamageSource GENERIC = new DamageSource(DamageTypes.GENERIC);
    public static final DamageSource LAVA = new DamageSource(DamageTypes.LAVA);
    public static final DamageSource IN_FIRE = new DamageSource(DamageTypes.LAVA);

}
