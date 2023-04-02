package org.waveapi.api.content.items.tools;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.WaveItem;
import org.waveapi.content.items.CustomItemWrap;
import org.waveapi.content.items.CustomSwordWrap;

public class WaveSwordItem extends WaveItem {
    private final WaveToolMaterial material;
    private float speed;
    private int damage;

    public WaveSwordItem(String id, WaveMod mod, WaveToolMaterial material) {
        super(id, mod);
        this.material = material;
    }

    @Override
    public void registerLocal() {
        baseRegister(new CustomSwordWrap(material.material, damage, speed, settings, this));
    }

    public WaveSwordItem setAttackDamage(int damage) {
        this.damage = damage;
        return this;
    }
    public WaveSwordItem setAttackSpeed(float speed) {
        this.speed = speed;
        return this;
    }

}
