package org.waveapi;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waveapi.api.WaveLoader;
import org.waveapi.api.mics.Side;
import org.waveapi.content.resources.LangManager;
import org.waveapi.content.resources.ResourcePackManager;

import java.io.File;
import java.util.Map;

public class Main implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("waveapi");

	public static final File mainFolder = new File("./waveAPI");

	public static boolean bake;

	@Override
	public void onInitialize() {

		LOGGER.info("Initializing");

		WaveLoader.init();

		if (Side.isClient()) {
			new ResourcePackManager();
		}

		for (Map.Entry<String, WaveLoader.WrappedWaveMod> mod : WaveLoader.getMods().entrySet()) {
			bake = mod.getValue().changed;
			mod.getValue().mod.init();
		}

		if (Side.isClient()) {
			LangManager.write();
		}

	}
}