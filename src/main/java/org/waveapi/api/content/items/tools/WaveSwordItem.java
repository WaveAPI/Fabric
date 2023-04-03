package org.waveapi.api.content.items.tools;

import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.WaveItem;
import org.waveapi.api.math.BlockPos;
import org.waveapi.api.world.entity.living.EntityLiving;
import org.waveapi.api.world.inventory.ItemStack;
import org.waveapi.api.world.world.BlockState;
import org.waveapi.api.world.world.World;
import org.waveapi.content.items.tool.CustomSwordWrap;

public class WaveSwordItem extends WaveItem {
    private final WaveToolMaterial material;
    private float speed = 3.4f;
    private int damage = 1;

    public WaveSwordItem(String id, WaveMod mod, WaveToolMaterial material) {
        super(id, mod);
        this.material = material;
    }

    @Override
    public void registerLocal() {
        baseRegister(new CustomSwordWrap(material.material, damage, speed, settings, this));
    }

    public WaveSwordItem setAttackDamage(int damage) {
        this.damage = damage;
        return this;
    }
    public WaveSwordItem setAttackSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public boolean onPostMine(ItemStack itemStack, World world, BlockState blockState, BlockPos pos, EntityLiving entity) {
        return true;
    }
}
