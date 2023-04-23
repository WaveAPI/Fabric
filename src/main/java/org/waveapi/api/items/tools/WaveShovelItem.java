package org.waveapi.api.items.tools;

import org.waveapi.api.WaveMod;
import org.waveapi.content.items.CustomItemWrap;
import org.waveapi.content.items.tool.CommonTool;
import org.waveapi.content.items.tool.CustomShovelWrap;
import org.waveapi.content.resources.TagHelper;

public class WaveShovelItem extends WaveCommonToolItem {

    public WaveShovelItem(String id, WaveToolMaterial material, WaveMod mod) {
        super(id, material, mod);
        speed = -3.0f;
        damage = 0;
    }

    @Override
    public void _registerLocal() {
        TagHelper.addTag("fabric", "items/shovels", mod.name + ":" + id);
        this.base = new String[] {
                CustomShovelWrap.class.getName(),
                CommonTool.class.getName(),
                CustomItemWrap.class.getName()
        };
        baseRegister();
    }
}
