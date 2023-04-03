package org.waveapi.api.content.items.tools;

import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.WaveItem;
import org.waveapi.api.math.BlockPos;
import org.waveapi.api.world.entity.living.EntityLiving;
import org.waveapi.api.world.inventory.ItemStack;
import org.waveapi.api.world.world.BlockState;
import org.waveapi.api.world.world.World;
import org.waveapi.content.items.tool.CustomShovelWrap;
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

    public boolean onPostMine(ItemStack itemStack, World world, BlockState blockState, BlockPos pos, EntityLiving entity) {
        return true;
    }

}
