package org.waveapi.content.resources;

import org.waveapi.api.WaveLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagHelper {
    private static Map<String, Map<String, List<String>>> tags;

    public static void addTag(String mod, String tag, String item) {
        if (tags == null) {
            tags = new HashMap<>();
        }
        Map<String, List<String>> modMap = tags.computeIfAbsent(mod, k -> new HashMap<>());
        modMap.computeIfAbsent(tag, k -> new ArrayList<>() {}).add(item);
    }

    public static void write() {
        if (tags == null) {
            return;
        }
        File pack = ResourcePackManager.getInstance().getPackDir();
        File data = new File(pack, "data");
        if (!data.exists()) {
            data.mkdirs();
        }
        StringBuilder regex = new StringBuilder("([\"#\t ]+(");
        for (Map.Entry<String, WaveLoader.WrappedWaveMod> mod : WaveLoader.getMods().entrySet()) {
            if (mod.getValue().changed) {
                regex.append(mod.getKey()).append("|");
            }
        }
        regex.setCharAt(regex.length()-1, ')');
        regex.append(":.*?\",)");

        for (File f : data.listFiles()) {
            File tags = new File(f, "tags");
            if (tags.exists()) {
                recursivelyDeleteUpdatedMods(tags, regex.toString());
            }
        }

        for (Map.Entry<String, Map<String, List<String>>> mods : tags.entrySet()) {
            File tagFolder = new File(pack, "data/" + mods.getKey() + "/tags");
            tagFolder.mkdirs();

            for (Map.Entry<String, List<String>> tag : mods.getValue().entrySet()) {
                File tagFile = new File(tagFolder, tag.getKey() + ".json");
                if (tagFile.exists()) {
                    try {
                        StringBuilder content = new StringBuilder(Files.readString(tagFile.toPath()));
                        for (String item : tag.getValue()) {
                            content.insert(content.length() - 2, "\"" + item + "\",");
                        }
                        content.deleteCharAt(content.length()-3);
                        Files.write(tagFile.toPath(), content.toString().getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    tagFile.getParentFile().mkdirs();
                    try {
                        StringBuilder content = new StringBuilder("""
                                {
                                  "replace": false,
                                  "values": []}""");
                        for (String item : tag.getValue()) {
                            content.insert(content.length() - 2, "\"" + item + "\",");
                        }
                        content.deleteCharAt(content.length()-3);
                        Files.write(tagFile.toPath(), content.toString().getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        tags = null;
    }

    public static void recursivelyDeleteUpdatedMods(File f, String regex) {
        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                recursivelyDeleteUpdatedMods(file, regex);
            }
        } else if (f.getName().endsWith(".json")) {
            try {
                String content = Files.readString(f.toPath());
                Files.write(f.toPath(), content.replaceAll(regex, "").getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
