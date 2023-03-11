package org.waveapi;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.waveapi.api.WaveLoader;
import org.waveapi.api.WaveMod;

import java.util.Map;

public class Main implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("waveapi");

	@Override
	public void onInitialize() {

		LOGGER.info("Initializing");

		WaveLoader.init();

		for (Map.Entry<String, WaveMod> mod : WaveLoader.getMods().entrySet()) {
			mod.getValue().init();
		}

	}
}