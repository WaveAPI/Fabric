package org.waveapi.api.items.tags;

import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.waveapi.api.items.WaveItem;
import org.waveapi.api.items.inventory.ItemStack;
import org.waveapi.content.resources.TagHelper;

public class SimpleCommonTag implements Tag {

    private final String id;
    private final String mod;

    private final Ingredient ingredient;

    public SimpleCommonTag(String mod, String location) {
        this.mod = mod;
        this.id = location;
        this.ingredient = Ingredient.fromTag(TagKey.of(Registry.ITEM.getKey(),  new Identifier(mod, id)));
    }

    @Override
    public String getTagIngredient() {
        return "{\"tag\":\"" + mod + ":" + id + "\"}";
    }

    @Override
    public void tag(WaveItem item) {
        TagHelper.addTag(mod, "items/" + id, item.getMod().name + ":" + item.getId());
    }

    @Override
    public boolean check(ItemStack itemStack) {
        return ingredient.test(itemStack.itemStack);
    }
}
