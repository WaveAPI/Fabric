package org.waveapi.api.items.tools;

import org.waveapi.api.WaveMod;
import org.waveapi.content.items.CustomItemWrap;
import org.waveapi.content.items.tool.CommonTool;
import org.waveapi.content.items.tool.CustomSwordWrap;

public class WaveSwordItem extends WaveCommonToolItem {
    public WaveSwordItem(String id, WaveToolMaterial material, WaveMod mod) {
        super(id, material, mod);
        speed = -2.4f;
        damage = 2;
    }

    @Override
    public void _registerLocal() {
        this.base = new String[] {
                CustomSwordWrap.class.getName(),
                CommonTool.class.getName(),
                CustomItemWrap.class.getName()
        };
        baseRegister();
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
