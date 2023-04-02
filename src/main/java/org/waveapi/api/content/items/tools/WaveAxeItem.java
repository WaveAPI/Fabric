package org.waveapi.api.content.items.tools;

import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.WaveItem;
import org.waveapi.content.items.CustomAxeWrap;
import org.waveapi.content.items.CustomShovelWrap;
import org.waveapi.content.resources.TagHelper;

public class WaveAxeItem extends WaveItem {
    private final WaveToolMaterial material;
    private float speed = -3.2f;
    private int damage = 3;

    public WaveAxeItem(String id, WaveMod mod, WaveToolMaterial material) {
        super(id, mod);
        this.material = material;
    }

    @Override
    public void registerLocal() {
        TagHelper.addTag("fabric", "items/axes", mod.name + ":" + id);
        baseRegister(new CustomAxeWrap(material.material, damage, speed, settings, this));
    }

    public WaveAxeItem setAttackDamage(int damage) {
        this.damage = damage;
        return this;
    }
    public WaveAxeItem setAttackSpeed(float speed) {
        this.speed = speed;
        return this;
    }

}
