package org.waveapi.api.items.tools;

import net.minecraft.item.ToolMaterial;
import org.waveapi.api.items.recipes.ingredients.Ingredient;

public class WaveToolMaterial {

    public ToolMaterial material;
    private int durability = 100;
    private float miningSpeed = 1;
    private float damage = 1;
    private int level = 1;
    private int enchantability = 12;
    private net.minecraft.recipe.Ingredient ingredient = net.minecraft.recipe.Ingredient.empty();

    public WaveToolMaterial () {
        material = new ToolMaterial() {
            @Override
            public int getDurability() {
                return durability;
            }

            @Override
            public float getMiningSpeedMultiplier() {
                return miningSpeed;
            }

            @Override
            public float getAttackDamage() {
                return damage;
            }

            @Override
            public int getMiningLevel() {
                return level;
            }

            @Override
            public int getEnchantability() {
                return enchantability;
            }

            @Override
            public net.minecraft.recipe.Ingredient getRepairIngredient() {
                return ingredient;
            }
        };
    }

    public WaveToolMaterial setBaseDurability(int durability) {
        this.durability = durability;
        return this;
    }

    public WaveToolMaterial setMiningSpeedMultiplier(float speed) {
        this.miningSpeed = speed;
        return this;
    }

    public WaveToolMaterial setAttackDamage(float damage) {
        this.damage = damage;
        return this;
    }

    public WaveToolMaterial setMiningLevel(int level) {
        this.level = level;
        return this;
    }

    public WaveToolMaterial setEnchantability(int enchantability) {
        this.enchantability = enchantability;
        return this;
    }

    public WaveToolMaterial setRepairIngredient(Ingredient ingredient) {
        this.ingredient = ingredient.getVanilla();
        return this;
    }

}
