package org.waveapi.api.items.recipes;

import org.waveapi.api.WaveMod;
import org.waveapi.api.items.WaveItem;
import org.waveapi.api.items.tags.Tag;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.waveapi.Main.bake;

public class WaveShapedRecipe { //TODO: REWRITE and use ingredients, maybe do something which is similar to drops
    private final WaveMod mod;

    private static LinkedList<WaveShapedRecipe> toRegister = new LinkedList<>();
    private final String[] pattern;
    private String result;

    private final String name;

    private final Map<Character, String> ingredients;

    public static void build(File dataDir) {
        int id = 0;
        for (WaveShapedRecipe recipe : toRegister) {
            File file = new File(dataDir, recipe.mod.name + "/recipes/" + ("shaped" + id + "_" + recipe.name + ".json"));
            file.getParentFile().mkdirs();

            StringBuilder text = new StringBuilder("""
                    {
                      "type": "minecraft:crafting_shaped",
                      "pattern": [""");

            for (String line : recipe.pattern) {
                text.append("\"").append(line).append("\",");
            }
            text.setCharAt(text.length() - 1, ']');
            text.append(",\n\"key\": {");

            for (Map.Entry<Character, String> ingredient : recipe.ingredients.entrySet()) {
                text.append("\"").append(ingredient.getKey()).append("\": ").append(ingredient.getValue()).append(",");
            }

            text.setCharAt(text.length() - 1, '}');
            text.append(", \"result\":").append(recipe.result).append("}");

            try {
                Files.write(file.toPath(), text.toString().getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            id++;
        }

        toRegister = null;
    }

    public WaveShapedRecipe setResultCount(int count) {
        if (!bake) return this;
        if (result.contains("\"count\":")) {
            result = result.substring(0, result.indexOf("\"count\":") - 1) + ", \"count\":" + count + "}";
        } else {
            result = result.substring(0, result.length() - 1) + ", \"count\":" + count + "}";
        }
        return this;
    }

    public WaveShapedRecipe(String result, String[] pattern, WaveMod mod) {
        this.mod = mod;
        this.pattern = pattern;
        ingredients = new HashMap<>();

        if (bake) {
            this.name = result.replace(":", "_");
            this.result = "{ \"item\": \"" + result + "\"}";
            toRegister.add(this);
        } else {
            this.result = "";
            this.name = "";
        }
    }
    public WaveShapedRecipe(WaveItem result, String[] pattern, WaveMod mod) {
        this(result.getMod().name + ":" + result.getId(), pattern, mod);
    }

    public WaveShapedRecipe addIngredient(char symbol, String id) {
        if (!bake) return this;
        ingredients.put(symbol, "{\"item\": \"" + id + "\"}");
        return this;
    }

    public WaveShapedRecipe addIngredient(char symbol, Tag tag) {
        if (!bake) return this;
        ingredients.put(symbol, tag.getTagIngredient());
        return this;
    }

}
