package org.waveapi.api.content.items.armour;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.waveapi.api.WaveMod;
import org.waveapi.api.content.items.recipes.ingredients.Ingredient;
import org.waveapi.api.file.texture.Texture;
import org.waveapi.content.resources.ResourcePackManager;

public class WaveArmourMaterial {

    private final String name;
    private int baseDurability = 1;
    private int[] durability = new int[] {200, 300, 200, 100};
    private int[] protection = new int[] {1, 1, 1, 1};
    private int enchatability = 10;
    private Ingredient repair;
    private float toughness;
    private float knockback;
    private Texture layer2;
    private Texture layer1;

    public WaveArmourMaterial(String name) {
        this.name = name;
    }

    private ArmorMaterial mat;

    public ArmorMaterial getMaterial(WaveMod mod) {
        if (mat == null) {
            layer1.getWithMinecraftAsMod(ResourcePackManager.getInstance().getPackDir(), mod,
                    "models/armor/" + mod.name + "_" + name + "_wave_layer_1");
            layer2.getWithMinecraftAsMod(ResourcePackManager.getInstance().getPackDir(), mod,
                    "models/armor/" + mod.name + "_" + name + "_wave_layer_2");
            mat = new ArmorMaterial() {
                @Override
                public int getDurability(EquipmentSlot slot) {
                    return baseDurability * durability[slot.getEntitySlotId()];
                }

                @Override
                public int getProtectionAmount(EquipmentSlot slot) {
                    return protection[slot.getEntitySlotId()];
                }

                @Override
                public int getEnchantability() {
                    return enchatability;
                }

                @Override
                public SoundEvent getEquipSound() {
                    return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
                }

                @Override
                public net.minecraft.recipe.Ingredient getRepairIngredient() {
                    return repair.getVanilla();
                }

                @Override
                public String getName() {
                    return (mod.name + "_" + name + "_wave");
                }

                @Override
                public float getToughness() {
                    return toughness;
                }

                @Override
                public float getKnockbackResistance() {
                    return knockback;
                }
            };
        }
        return mat;
    }



    public WaveArmourMaterial setEnchantability(int enchantability) {
        this.enchatability = enchantability;
        return this;
    }

    public WaveArmourMaterial setDurability(int[] durability) {
        this.durability = durability;
        return this;
    }
    public WaveArmourMaterial setBaseDurability(int durability) {
        baseDurability = durability;
        return this;
    }
    public WaveArmourMaterial setProtection(int[] protection) {
        this.protection = protection;
        return this;
    }
    public WaveArmourMaterial setRepairIngredient(Ingredient ingredient) {
        repair = ingredient;
        return this;
    }

    public WaveArmourMaterial setToughness(float toughness) {
        this.toughness = toughness;
        return this;
    }

    public WaveArmourMaterial setKnockbackResistance(float resistance) {
        this.knockback = resistance;
        return this;
    }

    public WaveArmourMaterial setHelmetChestplateBootsTexture(Texture texture) {
        this.layer1 = texture;
        return this;
    }
    public WaveArmourMaterial setLeggingsTexture(Texture texture) {
        this.layer2 = texture;
        return this;
    }

}
