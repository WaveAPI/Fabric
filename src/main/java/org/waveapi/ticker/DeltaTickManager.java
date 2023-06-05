package org.waveapi.ticker;

import org.waveapi.Main;
import org.waveapi.MixinConfigPlugin;
import org.waveapi.ticker.tickers.LimitedPerTick;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Map;

public class DeltaTickManager {

    public static Class<? extends DeltaTicker> ticker;

    public static Map<String, Object> deltaTickingSettings;
    public static void initialize() {
        File cfg = new File(Main.mainFolder, "deltaTickingSettings.yml");
        if (!cfg.exists()) {
            try {
                cfg.getParentFile().mkdirs();
                Files.write(cfg.toPath(), ("ticker: LimitedPerTick\n" +
                                            "tickersPerTick: 100").getBytes(), StandardOpenOption.CREATE_NEW);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Yaml yaml = new Yaml();
        try {
            deltaTickingSettings = yaml.load(new FileInputStream(cfg));
            String ticker = (String) deltaTickingSettings.get("ticker");
            if (ticker.equals("none")) {
                return;
            }
            switch (ticker) {
                case "LimitedPerTick" -> DeltaTickManager.ticker = LimitedPerTick.class;
            }

            MixinConfigPlugin.allowedMixins.add("org.waveapi.mixin.MixinWorldTileTick");

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
