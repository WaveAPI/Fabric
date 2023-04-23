package org.waveapi.api.items.drop;

public interface DropFunction {
    DropFunction ORE_FORTUNE = () -> """
                            {
                              "function": "minecraft:apply_bonus",
                              "enchantment": "minecraft:fortune",
                              "formula": "minecraft:ore_drops"
                            }""";

    String getFunction();

}
