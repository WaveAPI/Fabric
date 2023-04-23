package org.waveapi.api.items.recipes.ingredients;

import org.waveapi.api.items.WaveItem;

public class SimpleItemIngredient extends Ingredient {
    private final WaveItem item;

    public SimpleItemIngredient (WaveItem item) {
        this.item = item;
    }

    @Override
    public net.minecraft.recipe.Ingredient getVanilla() {
        return net.minecraft.recipe.Ingredient.ofItems(item._getItem());
    }
}
