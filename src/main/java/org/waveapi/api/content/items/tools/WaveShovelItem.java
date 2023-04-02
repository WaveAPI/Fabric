package org.waveapi.api.content.items.tools;

import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.WaveItem;
import org.waveapi.content.items.CustomHoeWrap;
import org.waveapi.content.items.CustomShovelWrap;
import org.waveapi.content.resources.TagHelper;

public class WaveShovelItem extends WaveItem {
    private final WaveToolMaterial material;
    private float speed = -3.0f;
    private int damage = 0;

    public WaveShovelItem(String id, WaveMod mod, WaveToolMaterial material) {
        super(id, mod);
        this.material = material;
    }

    @Override
    public void registerLocal() {
        TagHelper.addTag("fabric", "items/shovels", mod.name + ":" + id);
        baseRegister(new CustomShovelWrap(material.material, damage, speed, settings, this));
    }

    public WaveShovelItem setAttackDamage(int damage) {
        this.damage = damage;
        return this;
    }
    public WaveShovelItem setAttackSpeed(float speed) {
        this.speed = speed;
        return this;
    }

}
