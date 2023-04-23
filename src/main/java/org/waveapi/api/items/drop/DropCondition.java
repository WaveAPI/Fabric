package org.waveapi.api.items.drop;

public interface DropCondition {
    DropCondition SILK_TOUCH = () -> """
                                {
                                  "condition": "minecraft:match_tool",
                                  "predicate": {
                                    "enchantments": [
                                      {
                                        "enchantment": "minecraft:silk_touch",
                                        "levels": {
                                          "min": 1
                                        }
                                      }
                                    ]
                                  }
                                }""";

    String getFunction();

}
