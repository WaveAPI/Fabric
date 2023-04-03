package org.waveapi.api.content.items.tools;

import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.WaveItem;
import org.waveapi.api.math.BlockPos;
import org.waveapi.api.world.entity.living.EntityLiving;
import org.waveapi.api.world.inventory.ItemStack;
import org.waveapi.api.world.world.BlockState;
import org.waveapi.api.world.world.World;
import org.waveapi.content.items.tool.CustomHoeWrap;
import org.waveapi.content.resources.TagHelper;

public class WaveHoeItem extends WaveItem {
    private final WaveToolMaterial material;
    private float speed = -3f;
    private int damage = 0;

    public WaveHoeItem(String id, WaveMod mod, WaveToolMaterial material) {
        super(id, mod);
        this.material = material;
    }

    @Override
    public void registerLocal() {
        TagHelper.addTag("fabric", "items/hoes", mod.name + ":" + id);
        baseRegister(new CustomHoeWrap(material.material, damage, speed, settings, this));
    }

    public WaveHoeItem setAttackDamage(int damage) {
        this.damage = damage;
        return this;
    }
    public WaveHoeItem setAttackSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public boolean onPostMine(ItemStack itemStack, World world, BlockState blockState, BlockPos pos, EntityLiving entity) {
        return true;
    }

}
