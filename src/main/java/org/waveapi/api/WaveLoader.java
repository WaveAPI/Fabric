package org.waveapi.api;

import org.waveapi.Main;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class WaveLoader {

    public static Map<String, WaveMod> getMods() {
        return mods;
    }

    public static Map<String, WaveMod> mods = new HashMap<>();

    public static void init() {

        File modFolder = new File("./waves");
        File[] mods = modFolder.listFiles();

        for (File mod : mods) {
            try {
                URL[] urls = new URL[] {mod.toURI().toURL()};
                URLClassLoader classLoader = new URLClassLoader(urls, WaveLoader.class.getClassLoader());

                URL yml = classLoader.getResource("mod.yml");
                if (yml == null) {
                    continue;
                }
                Main.LOGGER.info("Found mod " + mod.getName());

                Yaml yaml = new Yaml();
                Map<String, Object> params = yaml.load(yml.openStream());

                Object mainObject = params.get("main");
                if (mainObject instanceof String) {
                    String main = (String) params.get("main");
                    Class<?> c = classLoader.loadClass(main);

                    c.getConstructor().newInstance();

                } else {
                    Main.LOGGER.info("Mod " + mod.getName() + " has bad main path");
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void register(WaveMod mod) {
        mods.put(mod.name, mod);
    }

}
