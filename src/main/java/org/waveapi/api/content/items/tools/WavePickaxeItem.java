package org.waveapi.api.content.items.tools;

import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.WaveItem;
import org.waveapi.content.items.CustomPickaxeWrap;
import org.waveapi.content.items.CustomSwordWrap;
import org.waveapi.content.resources.TagHelper;

public class WavePickaxeItem extends WaveItem {
    private final WaveToolMaterial material;
    private float speed = -3.6f;
    private int damage = 0;

    public WavePickaxeItem(String id, WaveMod mod, WaveToolMaterial material) {
        super(id, mod);
        this.material = material;
    }

    @Override
    public void registerLocal() {
        TagHelper.addTag("fabric", "items/pickaxe", mod.name + ":" + id);
        baseRegister(new CustomPickaxeWrap(material.material, damage, speed, settings, this));
    }

    public WavePickaxeItem setAttackDamage(int damage) {
        this.damage = damage;
        return this;
    }
    public WavePickaxeItem setAttackSpeed(float speed) {
        this.speed = speed;
        return this;
    }

}
