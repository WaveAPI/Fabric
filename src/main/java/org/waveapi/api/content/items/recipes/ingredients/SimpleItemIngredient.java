package org.waveapi.api.content.items.recipes.ingredients;

import org.waveapi.api.content.items.WaveItem;

public class SimpleItemIngredient extends Ingredient {
    private final WaveItem item;

    public SimpleItemIngredient (WaveItem item) {
        this.item = item;
    }

    @Override
    public net.minecraft.recipe.Ingredient getVanilla() {
        return net.minecraft.recipe.Ingredient.ofItems(item.getItem());
    }
}
