package org.waveapi.api;

import net.fabricmc.loader.api.FabricLoader;
import org.waveapi.Dependency;
import org.waveapi.Main;
import org.waveapi.utils.*;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class WaveLoader {

    public static Map<String, WrappedWaveMod> getMods() {
        return mods;
    }

    public static class WrappedWaveMod {
        public WaveMod mod;
        public File file;
        public boolean changed;
        public String id;
        public Map<String, Object> params;
    }

    public static Map<String, WrappedWaveMod> mods = new LinkedHashMap<>();

    public static Map<String, Dependency> extraDeps = new HashMap<>() {
        {
            put("kotlin!", new Dependency() {
                @Override
                public String getMissingMessage() {
                    return "Missing kotlin.\n" +
                            "Consider downloading a kotlin library for your version from https://modrinth.com/mods?q=kotlin";
                }

                @Override
                public boolean check() {
                    try {
                        return KotlinCheck.isKotlinAvailable();
                    } catch (Exception e) {
                        return false;
                    }
                }

                @Override
                public String getLoadBefore() {
                    return null;
                }
            });
        }
    };
    public static Set<String> externalDeps = new HashSet<>();
    public static void init() {

        List<WrappedWaveMod> modList = new ArrayList<>();

        {
            File modFolder = new File("./mods");
            File modFolder2 = new File("./waveapi/mods");
            List<File> mods = new java.util.ArrayList<>(List.of(modFolder.listFiles()));
            File[] modFolder2Contents = modFolder2.listFiles();
            if (modFolder2Contents != null) {
                mods.addAll(List.of(modFolder2Contents));
            }

            File modified = new File(Main.mainFolder, "modifCache.txt");

            Map<String, Long> lastModified = new HashMap<>();

            String version = FabricLoader.getInstance().getModContainer("waveapi").get().getMetadata().getVersion().toString();

            modifiedCheck:
            if (!FabricLoader.getInstance().isDevelopmentEnvironment()) {
                try {
                    if (modified.isFile()) {
                        FileInputStream in = new FileInputStream(modified);

                        if (!version.equals(ByteUtils.readString(in))) {
                            ClassHelper.rebuild = true;
                            break modifiedCheck;
                        }

                        while (in.available() > 0) {
                            lastModified.put(ByteUtils.readString(in), ByteUtils.readLong(in));
                        }
                        in.close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            Yaml yaml = new Yaml();

            for (File mod : mods) {
                try {
                    if (!mod.getName().endsWith(".jar")) {
                        continue;
                    }
                    ZipFile file = new ZipFile(mod);
                    ZipEntry waveYml = file.getEntry("wave.yml");
                    if (waveYml == null) {
                        continue;
                    }

                    WrappedWaveMod wrap = new WrappedWaveMod();
                    wrap.file = mod;

                    if (mod.lastModified() > lastModified.getOrDefault(mod.getName(), 0L)) {
                        lastModified.put(mod.getName(), mod.lastModified());
                        Main.LOGGER.info("Found modified mod " + mod.getName());
                        wrap.changed = true;
                    } else {
                        Main.LOGGER.info("Found mod " + mod.getName());
                    }

                    Map<String, Object> params = yaml.load(file.getInputStream(waveYml));
                    Object name = params.get("id");
                    if (name instanceof String str) {
                        wrap.id = str;
                    } else {
                        wrap.id = mod.getName();
                    }
                    wrap.params = params;
                    modList.add(wrap);
                } catch (Exception e) {
                    throw new RuntimeException("Failed in pre-init of waveAPI mod [" + mod.getName() + "]", e);
                }
            }
            try {
                if (modified.exists()) {
                    modified.delete();
                }
                modified.getParentFile().mkdirs();
                modified.createNewFile();
                FileOutputStream out = new FileOutputStream(modified);
                out.write(ByteUtils.encodeString(version));
                for (Map.Entry<String, Long> entry : lastModified.entrySet()) {
                    out.write(ByteUtils.encodeString(entry.getKey()));
                    out.write(ByteUtils.encodeLong(entry.getValue()));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Map<String, List<Dependency>> deps = new HashMap<>();
        Map<String, WrappedWaveMod> mods = new HashMap<>();

        for (WrappedWaveMod mod : modList) {
            mods.put(mod.id, mod);
            Object req = mod.params.get("requires");
            List<Object> needs;
            if (req instanceof String) {
                needs = List.of(req);
            } else if (req instanceof List) {
                needs = (List<Object>) req;
            } else {
                deps.put(mod.id, new ArrayList<>());
                continue;
            }

            List<Dependency> depList = new ArrayList<>(needs.size());
            for (Object dependency : needs) {
                if (dependency instanceof String dep) {
                    if (dep.endsWith("!")) {
                        depList.add(extraDeps.get(dep));
                    } else {
                        depList.add(new Dependency() {
                            @Override
                            public String getMissingMessage() {
                                return "Missing dependency [" + dep + "]\n" +
                                        "Potentially download it from: https://modrinth.com/mods?q=" + dep;
                            }

                            @Override
                            public boolean check() {
                                return deps.containsKey(dep) || externalDeps.contains(dep);
                            }

                            @Override
                            public String getLoadBefore() {
                                return dep;
                            }
                        });
                    }
                }
            }
            deps.put(mod.id, depList);
        }

        {
            Map<Dependency, List<String>> missingDeps = new HashMap<>();
            for (Map.Entry<String, List<Dependency>> entry : deps.entrySet()) {
                for (Dependency dep : entry.getValue()) {
                    if (!dep.check()) {
                        missingDeps.computeIfAbsent(dep, e -> new ArrayList<>()).add(entry.getKey());
                    }
                }
            }

            if (missingDeps.size() > 0) {
                List<String> strs = new ArrayList<>();
                for (Map.Entry<Dependency, List<String>> entry : missingDeps.entrySet()) {
                    StringBuilder str = new StringBuilder(entry.getKey().getMissingMessage());
                    str.append("\nRequired by [");
                    for (String mod : entry.getValue()) {
                        str.append(mod + ", ");
                    }
                    str.setCharAt(str.length() - 2, ']');

                    strs.add(str.toString());
                }
                ErrorMessageBuilder.create("Missing dependency", strs);
            }
        }

        List<WrappedWaveMod> modsToLoad = new ArrayList<>();

        for (String str : DependencyResolver.resolveDependencies(deps)) {
            modsToLoad.add(mods.get(str));
        }

        URL[] urls = new URL[modsToLoad.size()];
        int i = 0;
        try {
            for (WrappedWaveMod wrap : modsToLoad) {
                urls[i] = wrap.file.toURI().toURL();
                i++;
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        URLClassLoader classLoader = new URLClassLoader(urls, WaveLoader.class.getClassLoader());

        for (WrappedWaveMod wrap : modsToLoad) {
            try {
                Object mainObject = wrap.params.get("main");
                if (mainObject instanceof String) {
                    String main = (String) wrap.params.get("main");
                    Class<?> c = classLoader.loadClass(main);

                    Object reg = c.getConstructor().newInstance();
                    if (reg instanceof WaveMod) {
                        wrap.mod = (WaveMod) reg;
                        register(wrap);
                    } else {
                        Main.LOGGER.error("Mod " + wrap.file.getName() + " has main not extending WaveMod");
                    }
                } else {
                    Main.LOGGER.error("Mod " + wrap.file.getName() + " has bad main path");
                }
            } catch (Exception e) {
                    throw new RuntimeException("Failed in pre-init of waveAPI mod [" + wrap.file.getName() + "]", e);
            }
        }

    }

    public static void register(WrappedWaveMod wrap) {
        WaveMod mod = wrap.mod;

        Object version = wrap.params.get("version");
        if (version != null) {
            mod.version = version.toString();
        }

        Object id = wrap.params.get("id");
        if (id != null) {
            mod.name = id.toString();
        }

        if (mod.name == null) {
            throw new RuntimeException("Mod [" + wrap.file.getName() + "] is missing an ID");
        }

        if (!mod.name.matches("[a-z0-9_]+")) {
            throw new RuntimeException("Bad mod ID [" + mod.name + "] It contains a character which doesn't match [a-z0-9_]");
        }

        mods.put(mod.name, wrap);
    }

}
