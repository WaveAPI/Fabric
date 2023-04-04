package org.waveapi.api.content.items.tools;

import org.waveapi.api.WaveMod;
import org.waveapi.content.items.CustomItemWrap;
import org.waveapi.content.items.tool.CommonTool;
import org.waveapi.content.items.tool.CustomAxeWrap;
import org.waveapi.content.resources.TagHelper;

public class WaveAxeItem extends WaveCommonToolItem {

    public WaveAxeItem(String id, WaveMod mod, WaveToolMaterial material) {
        super(id, mod, material);
        speed = -3.2f;
        damage = 3;
    }

    public WaveAxeItem setAttackDamage(int damage) {
        this.damage = damage;
        return this;
    }
    public WaveAxeItem setAttackSpeed(float speed) {
        this.speed = speed;
        return this;
    }
    @Override
    public void registerLocal() {
        TagHelper.addTag("fabric", "items/axes", mod.name + ":" + id);
        this.base = new String[] {
                CustomAxeWrap.class.getName(),
                CommonTool.class.getName(),
                CustomItemWrap.class.getName()
        };
        baseRegister();
    }
}
