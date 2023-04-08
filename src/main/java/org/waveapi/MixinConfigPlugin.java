package org.waveapi;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.waveapi.api.WaveLoader;
import org.waveapi.api.events.Events;
import org.waveapi.api.misc.Side;
import org.waveapi.ticker.DeltaTickManager;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MixinConfigPlugin implements IMixinConfigPlugin {

    public static Set<String> allowedMixins;

    @Override
    public void onLoad(String mixinPackage) {
        if (mixinPackage.equals("org.waveapi.mixin")) {
            long initialTime = System.currentTimeMillis();
            Main.LOGGER.info("Preloading");

            new File("./waveAPI/classes").mkdirs();

            allowedMixins = new HashSet<>();

            DeltaTickManager.initialize();

            allowedMixins.add("org.waveapi.mixin.MixinResourcePackManager");

            new File("./waveAPI/classes").mkdirs();

            try {
                WaveLoader.init();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }

            Main.LOGGER.info("Registering events.");

            Events events = new Events();

            for (Map.Entry<String, WaveLoader.WrappedWaveMod> mod : WaveLoader.getMods().entrySet()) {
                try {
                    mod.getValue().mod.registerEvents(events);
                    if (Side.isClient()) {
                        mod.getValue().mod.registerClientEvents(events);
                    }
                } catch (Exception e) {
                    new RuntimeException("Failed while registering events of mod of waveAPI mod [" + mod.getValue().mod.name + "]", e).printStackTrace();
                    System.exit(-1);
                }
            }

            Main.LOGGER.info("Preload took " + (System.currentTimeMillis() - initialTime) + "ms");
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return allowedMixins.contains(mixinClassName);
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
