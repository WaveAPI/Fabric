package org.waveapi.api.content.items.tools;

import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.WaveItem;
import org.waveapi.api.math.BlockPos;
import org.waveapi.api.world.entity.living.EntityLiving;
import org.waveapi.api.world.inventory.ItemStack;
import org.waveapi.api.world.world.BlockState;
import org.waveapi.api.world.world.World;
import org.waveapi.content.items.tool.CustomPickaxeWrap;
import org.waveapi.content.resources.TagHelper;

public class WavePickaxeItem extends WaveItem {
    private final WaveToolMaterial material;
    private float speed = -2.8f;
    private int damage = 0;

    public WavePickaxeItem(String id, WaveMod mod, WaveToolMaterial material) {
        super(id, mod);
        this.material = material;
    }

    @Override
    public void registerLocal() {
        TagHelper.addTag("fabric", "items/pickaxes", mod.name + ":" + id);
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

    public boolean onPostMine(ItemStack itemStack, World world, BlockState blockState, BlockPos pos, EntityLiving entity) {
        return true;
    }
}
