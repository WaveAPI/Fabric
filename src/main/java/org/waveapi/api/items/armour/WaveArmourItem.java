package org.waveapi.api.items.armour;

import org.waveapi.api.WaveMod;
import org.waveapi.api.items.WaveItem;
import org.waveapi.content.items.CustomItemWrap;
import org.waveapi.content.items.etc.CustomArmourWrap;

public class WaveArmourItem extends WaveItem {
    public final WaveArmourMaterial material;
    public final ArmourSlot slot;

    public WaveArmourItem(String id, WaveArmourMaterial material, ArmourSlot slot, WaveMod mod) {
        super(id, mod);
        this.material = material;
        this.slot = slot;
    }

    @Override
    public void _registerLocal() {
        this.base = new String[] {
                CustomArmourWrap.class.getName(),
                CustomItemWrap.class.getName()
        };
        baseRegister();
    }

}
