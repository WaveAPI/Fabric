package org.waveapi.api.mics;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

public class Side {
    public static boolean isServer() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
    }

    public static boolean isClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }
}
