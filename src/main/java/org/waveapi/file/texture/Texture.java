package org.waveapi.file.texture;

import org.waveapi.api.WaveMod;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Texture {

    private final String path;
    private String resourceID;

    private static final String MISSING_TEXTURE = "waveapi:item/missing_texture";
    public Texture(String path) {
        if (path == null) {
            resourceID = MISSING_TEXTURE;
        }
        this.path = path;
    }

    /**
     * @return path of texture as would be used in a resourcePack
     */
    public String get(File pack, WaveMod mod, String recommended_path) {
        resourceIdCheck: if (resourceID == null) {
            InputStream stream = mod.getResource(path);
            if (stream == null) {
                resourceID = MISSING_TEXTURE;
                break resourceIdCheck;
            }
            String resourcePath = (recommended_path == null ? "item/" + path.replace(".png", "") : recommended_path);
            resourceID = mod.name + ":" + recommended_path;
            File file = new File(new File(pack,"assets/" + mod.name + "/textures"),
                    resourcePath + ".png");
            try {
                file.getParentFile().mkdirs();
                Files.copy(stream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return resourceID;
    }

}
