package org.waveapi.api.items;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import org.waveapi.content.items.ItemHelper;

public interface ItemAccepter {
    default boolean isValidItem(WaveItem item) {
        return false;
    }

    default boolean _isValidItem(Item item) {
        return isValidItem(ItemHelper.of(item));
    }

    class simple implements ItemAccepter {
        private final EnchantmentTarget target;

        public simple(EnchantmentTarget target) {
            this.target = target;
        }

        @Override
        public boolean _isValidItem(Item item) {
            return target.isAcceptableItem(item);
        }
    }

    ItemAccepter ARMOUR = new simple(EnchantmentTarget.ARMOR);
    ItemAccepter MElEE_WEAPON = new simple(EnchantmentTarget.WEAPON);

    ItemAccepter TOOL = new ItemAccepter() {
        @Override
        public boolean _isValidItem(Item item) {
            return item instanceof ToolItem;
        }
    };
    ItemAccepter MINING_TOOL = new simple(EnchantmentTarget.DIGGER);

}
