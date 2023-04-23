package org.waveapi.api.items.tools;

import org.waveapi.api.WaveMod;
import org.waveapi.content.items.CustomItemWrap;
import org.waveapi.content.items.tool.CommonTool;
import org.waveapi.content.items.tool.CustomPickaxeWrap;
import org.waveapi.content.resources.TagHelper;

public class WavePickaxeItem extends WaveCommonToolItem {

    public WavePickaxeItem(String id, WaveToolMaterial material, WaveMod mod) {
        super(id, material, mod);
        speed = -2.8f;
        damage = 1;
    }

    @Override
    public void _registerLocal() {
        TagHelper.addTag("fabric", "items/pickaxes", mod.name + ":" + id);
        this.base = new String[] {
                CustomPickaxeWrap.class.getName(),
                CommonTool.class.getName(),
                CustomItemWrap.class.getName()
        };
        baseRegister();
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
